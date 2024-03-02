package hashmap;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Collection;



/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private int initialSize = 16;
    private double maxLoad = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(initialSize);
        size = 0;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        buckets = createTable(initialSize);
        size = 0;
    }


    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    };

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[tableSize];
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = createBucket();
        }
        return newBuckets;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        buckets = createTable(buckets.length);
        size = 0;
    }
    private int index(K key) {
        int hashCode = key.hashCode();
        while (hashCode < 0) {
            hashCode += buckets.length;
        }
        return hashCode % buckets.length;
    }

    @Override
    public boolean containsKey(K key) {
        for (Node n : buckets[index(key)]) {
            if (key.equals(n.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        for (Node n : buckets[index(key)]) {
            if (key.equals(n.key)){
                return n.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        double loadFactor = size / buckets.length;
        if (loadFactor > maxLoad) {
            buckets = resize(buckets);
        }

        if (!containsKey(key)) {
            size++;
            Node node = new Node(key, value);
            buckets[index(key)].add(node);
        } else {
            for (Node n: buckets[index(key)]) {
                if (key.equals(n.key)) {
                    n.value = value;
                }
            }
        }
    }

    private Collection<Node>[] resize(Collection<Node>[] buckets) {
        Collection<Node>[] buckets_old = buckets;
        buckets = createTable(buckets.length * 2);
        for (Collection<Node> N : buckets_old) {
            for (Node n : N) {
                buckets[index(n.key)].add(n);
            }
        }
        return buckets;
    }

    @Override
    public Set<K> keySet() {
        Set<K> K_Set = new HashSet<K>();
        for (Collection<Node> N : buckets) {
            for (Node n : N) {
                K_Set.add(n.key);
            }
        }
        return K_Set;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }

}
