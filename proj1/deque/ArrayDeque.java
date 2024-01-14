package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private class ArrayIterator implements Iterator<T> {
        private int iterated = 0;
        private int wizPos = getFirstIndex();
        public boolean hasNext() {
            return iterated < size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items");
            }
            T item = items[wizPos];
            if (wizPos == items.length - 1) {
                wizPos = 0;
            } else {
                wizPos++;
            }
            iterated++;
            return item;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
        private T[] items;
        private int size;
        private int nextFirst;
        private int nextLast;

        public ArrayDeque() {
            items = (T[]) new Object[8];
            size = 0;
            nextFirst = 4;
            nextLast = 5;
        }

        /**
         * Resizes the underlying array to the target capacity.
         */
        private void resize(int capacity) {
            T[] a = (T[]) new Object[capacity];
            if (size == 0) {
                System.arraycopy(items, 0, a, 0, size);
            } else if (getLastIndex() >= getFirstIndex()) {
                System.arraycopy(items, getFirstIndex(), a, 0, size);
            } else {
                System.arraycopy(items, getFirstIndex(), a, 0, items.length - getFirstIndex());
                System.arraycopy(items, 0, a, items.length - getFirstIndex(), getLastIndex() + 1);
            }
            int sizeTemp = size;
            items = a;
            size = sizeTemp;
            nextFirst = items.length - 1;
            nextLast = size;
        }

        /**
         * Adds x to the front of the list.
         */
        public void addFirst(T x) {
            if (size == items.length) {
                resize(size * 2);
            }
            items[nextFirst] = x;
            if (nextFirst == 0) {
                nextFirst = items.length - 1;
            } else {
                nextFirst--;
            }
            size++;
        }

        /**
         * Adds x to the end of the list.
         */
        public void addLast(T x) {
            if (size == items.length) {
                resize(size * 2);
            }
            items[nextLast] = x;
            if (nextLast == items.length - 1) {
                nextLast = 0;
            } else {
                nextLast++;
            }
            size++;
        }


        /**
         * Returns the size of the list.
         */
        public int size() {
            return size;
        }

        public void printDeque() {
            if (size == 0) {
                System.out.println();
            }
            for (T item : items) {
                System.out.print(items + " ");
            }
            System.out.println();
        }

        public T removeFirst() {
            if (size == 0) {
                return null;
            }
            nextFirst = getFirstIndex();
            T firstItem = items[nextFirst];
            items[nextFirst] = null;
            size--;
            if ((size < (double) items.length / 4) && (items.length >= 16)) {
                resize(items.length / 2);
            }
            return firstItem;
        }

        private int getFirstIndex() {
            if (size == 0) {
                throw new NoSuchElementException("No more items");
            }
            int firstIndex;
            if (nextFirst == items.length - 1) {
                firstIndex = 0;
            } else {
                firstIndex = nextFirst + 1;
            }
            return firstIndex;
        }

        public T removeLast() {
            if (size == 0) {
                return null;
            }
            nextLast = getLastIndex();
            T lastItem = items[nextLast];
            items[nextLast] = null;  // We've already adjusted nextLast, so this is safe.
            size--;
            if ((size < (double) items.length / 4) && (items.length >= 16)) {
                resize(items.length / 2);
            }
            return lastItem;
        }

        private int getLastIndex() {
            if (size == 0) {
                throw new NoSuchElementException("No more items");
            }
            int lastIndex;
            if (nextLast == 0) {
                lastIndex = items.length - 1;
            } else {
                lastIndex = nextLast - 1;
            }
            return lastIndex;
        }

        public T get(int index) {
            if (index < 0 || index >= size) {
                return null;
            }
            return items[(getFirstIndex() + index) % items.length];
        }


        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Deque)) {
                return false;
            }
            Deque<T> otherDeque = (Deque<T>) o;
            if (this.size() != otherDeque.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (!this.get(i).equals(otherDeque.get(i))) {
                    return false;
                }
            }
            return true;
        }


    }
