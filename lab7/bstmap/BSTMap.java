package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private int wizPos;

        private BSTMapIterator(){
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size();
        }

        public K next() {
            K item = get(wizPos);
            wizPos++;
            return item;
        }
    }


    BSTNode rootNode;

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        K getKey() {return this.key;}
        void insert(K key, V value) {
            if (key.equals(this.key)) {
                this.value = value;
            }
            if (key.compareTo(this.key) < 0) {
                if (this.left != null) {
                    this.left.insert(key, value);
                } else {
                    this.left = new BSTNode(key, value, null, null);
                }
            }
            if (this.right != null) {
                this.right.insert(key, value);
            } else {
                this.right = new BSTNode(key, value, null, null);
            }
        }
        V findValue(K key) {
            if (key.equals(this.key)) return this.value;
            if (key.compareTo(this.key) < 0) {
                if (this.left != null) {
                    return this.left.findValue(key);
                } else return null;
            }
            if (this.right != null) {
                return this.right.findValue(key);
            } else return null;
        }

        int size() {
            if (this.key == null) return 0;
            return this.left.size() + this.right.size();
        }
    }

    /** Removes all the mappings from this map. */
    public void clear() {
        rootNode = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return (rootNode.findValue(key) != null);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (rootNode == null) {
            return null;
        } else return rootNode.findValue(key);
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return rootNode.size();
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (rootNode == null) {
            rootNode = new BSTNode(key, value, null, null);
        } else {
            rootNode.insert(key, value);
        }
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrder(rootNode);
    }

    private void printInOrder(BSTNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println(node.key);
            printInOrder(node.right);
        }
    }

    private int visited;

    public K get(int i) {
        visited = 0; // Reset visited count
        return select(rootNode, i).getKey();
    }
    private BSTNode select(BSTNode node, int i) {
        if (node == null) {
            return null;
        }

        // Traverse the left subtree
        BSTNode left = select(node.left, i);
        if (left != null) {
            return left;
        }

        // Visit the current node
        if (++visited == i) {
            return node;
        }

        // Traverse the right subtree
        return select(node.right, i);
    }
}
