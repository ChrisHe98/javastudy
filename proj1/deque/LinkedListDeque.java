package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class IntNode {
        public T item;
        public IntNode next;

        public IntNode prev;

        public IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntNode sentinel;
    public int size;

    /** Creates an empty timingtest.SLList. */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        this();
        addFirst(x);
    }

    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        sentinel.next = new IntNode(x, sentinel.next, sentinel);
        size = size + 1;
        sentinel.next.next.prev = sentinel.next;
    }

    /** Returns the first item in the list. */
    public T getFirst() {
        if (sentinel.next != sentinel){
        return sentinel.next.item;
        }else{
            return null;
        }
    }

    /** returns last item in the list */
    public T getLast() {
        if(sentinel.next!=sentinel) {
            return sentinel.prev.item;
        }else{
            return null;
        }
    }


    /** Adds x to the end of the list. */
    public void addLast(T x) {
        size = size + 1;
        sentinel.prev.next = new IntNode(x, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    public void printDeque(){
        for(IntNode startNode = sentinel; startNode.next != sentinel; startNode = startNode.next){
            System.out.print(startNode.item + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if (sentinel.next == sentinel){
            return null;
        } else {
            size = size - 1;
            T firstItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return firstItem;
        }
    }
    public T removeLast(){
        if (sentinel.prev == sentinel){
            return null;
        } else {
            this.size = this.size - 1;
            T lastItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return lastItem;
        }
    }

    public T get(int index){
        if (index<0){
            return null;
        }
        IntNode p = sentinel;
        for (int i =0; i <= index; i++){
            if (p.next == sentinel){
                return null;
            }
            p = p.next;
        }
        return p.item;
    }

    public T getRecursion(int index) {
        return getRecursionHelper(sentinel.next, index);
    }

    private T getRecursionHelper(IntNode node, int index) {
        // Base case: index is out of bounds
        if(index < 0 || node == sentinel) {
            return null;
        }
        // Base case: found the item at the specified index
        if(index == 0) {
            return node.item;
        }
        // Recursive case: move to the next node and decrement the index
        return getRecursionHelper(node.next, index - 1);
    }



    public boolean equals(Object o){
        if(o== null){return false;}
        if (!(o instanceof LinkedListDeque)){
            return false;
        }
        // Cast o to LinkedListDeque
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;
        if (this.size()!= other.size()){
            return false;
        }
        for (int i = 0; i < this.size(); i++){
            if (!(this.get(i).equals(other.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    //needs pondering//
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
            wizPos += 1;
            return item;
        }
    }

}
