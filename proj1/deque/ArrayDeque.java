package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
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

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if ((nextLast > nextFirst + 1) || size == 0) {
            System.arraycopy(items, nextFirst + 1, a, 0, size);
        } else {
            if (nextFirst == items.length - 1) {
                System.arraycopy(items, 0, a, 0, size);
            } else {
                System.arraycopy(items, nextFirst + 1, a, 0, items.length - 1 - nextFirst);
                System.arraycopy(items, 0, a, items.length - 1 - nextFirst, nextLast);
            }
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        if (size == items.length) {
            resize((int) Math.round(size * 2));
        }
        items[nextFirst] = x;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
        size++;
    }

    /** Adds x to the end of the list. */
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

    /** Returns the first item in the list. */
    private T getFirst() {
        if (nextFirst == items.length - 1) {
            return items[0];
        } else {
            return items[nextFirst + 1];
        }
    }

    /** returns last item in the list */
    private T getLast() {
        if (nextLast == 0) {
            return items[items.length - 1];
        } else {
            return items[nextLast - 1];
        }
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    public void printDeque() {
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        } else if (nextFirst > nextLast) {
            for (int i = nextFirst + 1; i < items.length; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if ((size < items.length / 4) && (items.length >= 16)) {
            resize(items.length / 2);
        }
        T firstItem = getFirst();
        items[nextFirst] = null;
        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst++;
        }
        size--;
        return firstItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        // Adjust nextLast before accessing the array to avoid going out of bounds.
        nextLast = (nextLast - 1 + items.length) % items.length;
        T lastItem = items[nextLast];
        items[nextLast] = null;  // We've already adjusted nextLast, so this is safe.
        size--;
        if ((size < items.length / 4) && (items.length >= 16)) {
            resize(items.length / 2);
        }
        return lastItem;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int start = nextFirst + 1;
        if (start + index >= items.length) {
            index -= (items.length - start);
            return items[index];
        } else {
            return items[start + index];
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<?> other = (ArrayDeque<?>) o;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int wizPos;
        private int iterated;

        private ArrayIterator() {
            iterated = 0;
            if (nextFirst == items.length - 1) {
                wizPos = 0;
            } else {
                wizPos = nextFirst + 1;
            }
        }

        public boolean hasNext() {
            return iterated < size;
        }

        public T next() {
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
}
