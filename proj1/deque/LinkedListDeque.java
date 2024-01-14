package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private IntNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T x) {
        sentinel.next = new IntNode(x, sentinel.next, sentinel);
        size++;
        sentinel.next.next.prev = sentinel.next;
    }

    private T getFirst() {
        if (sentinel.next != sentinel) {
            return sentinel.next.item;
        } else {
            return null;
        }
    }

    private T getLast() {
        if (sentinel.next != sentinel) {
            return sentinel.prev.item;
        } else {
            return null;
        }
    }

    public void addLast(T x) {
        size++;
        sentinel.prev.next = new IntNode(x, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (IntNode startNode = sentinel; startNode.next != sentinel; startNode = startNode.next) {
            System.out.print(startNode.item + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            size--;
            T firstItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return firstItem;
        }
    }

    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            size--;
            T lastItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return lastItem;
        }
    }

    public T get(int index) {
        if (index < 0) {
            return null;
        }
        IntNode p = sentinel;
        for (int i = 0; i <= index; i++) {
            if (p.next == sentinel) {
                return null;
            }
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        return getRecursionHelper(sentinel.next, index);
    }

    private T getRecursionHelper(IntNode node, int index) {
        if (index < 0 || node == sentinel) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return getRecursionHelper(node.next, index - 1);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> otherDeque = (Deque<T>) o;
        if (this.size() !=  otherDeque.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(otherDeque.get(i))) {
                return false;
            }
        }
        return true;
    }


    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int wizPos;

        private LinkedListIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T item = get(wizPos);
            wizPos++;
            return item;
        }
    }
}
