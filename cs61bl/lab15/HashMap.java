import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HashMap<K, V> implements Map61BL<K, V> {

    /** A wrapper class for holding each (KEY, VALUE) pair. */
    private static class Entry<K, V> {

        /** The key used for lookup. */
        private K _key;
        /** The associated value. */
        private V _value;

        public Entry next;

        /** Create a new (KEY, VALUE) pair. */
        public Entry(K key, V value) {
            _key = key;
            _value = value;
            next = null;
        }

        public Entry(K key, V value, Entry next) {
            _key = key;
            _value = value;
            this.next = next;
        }

        public V value() {
            return _value;
        }

        public K key() {
            return _key;
        }

        public void set(V value) {
            _value = value;
        }

        /** Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return _key.equals(other._key);
        }

        /** Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry &&
                    _key.equals(((Entry) other)._key) &&
                    _value.equals(((Entry) other)._value));
        }

    }

    private Object[] spmap;
    private int capacity;
    private int size;
    private float LF;


    /** Create a new hash map with a default array of size 16 and load factor of 0.75. */
    public HashMap() {
        spmap = new Object[16];
        size = 0;
        capacity = 16;
        LF = 0.75f;
    }

    /** Create a new hash map with an array of size INITIALCAPACITY and default load factor of 0.75. */
    public HashMap(int initialCapacity) {
        spmap = new Object[initialCapacity];
        size = 0;
        capacity = initialCapacity;
        LF = 0.75f;
    }

    /** Create a new hash map with INITIALCAPACITY and LOADFACTOR. */
    public HashMap(int initialCapacity, float loadFactor) {
        spmap = new Object[initialCapacity];
        size = 0;
        capacity = initialCapacity;
        LF = loadFactor;
    }

    /** Return the capacity of this hash table's internal array. */
    public int capacity() {
        return capacity;
    }


    /** Returns true if the given KEY is a valid name that starts with A-Z. */
    /*private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }*/

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            spmap[i] = null;
        }
        size = 0;
    }

    /** Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        int index = hash(key);
        Entry<K, V> temp = (Entry<K, V>) spmap[index];
        while(temp != null) {
            if (temp.key().equals(key)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /** Returns the value for the specified KEY. */
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> temp = (Entry<K, V>) spmap[index];
        while(temp != null) {
            if (temp.key().equals(key)) {
                return temp.value();
            }
            temp = temp.next;
        }
        return null;
    }

    private void resize() {
        Object oldspmap[] = spmap;
        spmap = new Object[2 * capacity];
        for (Object e : oldspmap) {
            Entry<K, V> temp = (Entry<K, V>) e;
            while (temp != null) {
                int index = temp.key().hashCode() % (2 * capacity);
                spmap[index] = new Entry<K, V>(temp.key(), temp.value(), (Entry<K, V>) spmap[index]);
                temp = temp.next;
            }
        }
        capacity = capacity * 2;
    }

    /** Put a (KEY, VALUE) pair into this map. */
    public void put(K key, V value) {
        //System.out.println("yes");
        if (size + 1 > LF * capacity) {
            resize();
        }
        int index = hash(key);
        if (spmap[index] == null) {
            spmap[index] = new Entry<K, V>(key, value);
            size++;
            return;
        }
        Entry<K, V> prev = null;
        Entry<K, V> temp = (Entry<K, V>) spmap[index];
        while(temp != null) {
            if (temp.key().equals(key)) {
                temp.set(value);
                return;
            }
            prev = temp;
            temp = temp.next;
        }
        prev.next = new Entry<>(key, value);
        size++;
        //System.out.println(size);
    }

    /** Remove a single entry, KEY, from this table and return the VALUE if successful or NULL otherwise. */
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> temp = (Entry<K, V>) spmap[index];
        Entry<K, V> prev = null;
        while (temp != null) {
            if (temp.key().equals(key)) {
                V v = temp.value();
                if (prev == null) {
                    spmap[index] = null;
                } else {
                    prev.next = temp.next;
                }
                size--;
                return v;
            }
            prev = temp;
            temp = temp.next;
        }
        throw new IllegalArgumentException();
    }

    public boolean remove(K key, V value) {
        int index = hash(key);
        Entry<K, V> temp = (Entry<K, V>) spmap[index];
        Entry<K, V> prev = null;
        while (temp != null) {
            if (temp.key().equals(key) && temp.value().equals(value)) {
                if (prev == null) {
                    spmap[index] = null;
                } else {
                    prev.next = temp.next;
                }
                size--;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    private class HashMapIterator implements Iterator<K> {
        private ArrayList<K> keys;
        private int index;

        public HashMapIterator() {
            keys = new ArrayList<>();
            for (Object e : spmap) {
                if (e != null) {
                    Entry<K, V> temp = (Entry<K, V>) e;
                    while (temp != null) {
                        keys.add(temp.key());
                        temp = temp.next;
                    }
                }
            }
            index = 0;
        }

        public K next() {
            if (index == keys.size()) {
                throw new IllegalArgumentException();
            }
            int temp = index;
            index++;
            return keys.get(temp);
        }

        public boolean hasNext() {
            return index < keys.size();
        }
    }

    public Iterator<K> iterator() {
        return new HashMapIterator();
    }
}

