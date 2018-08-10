/**
 *  A simple mapping from string names to string values backed by an array.
 *  Supports only A-Z for the first character of the key name. Values can be
 *  any valid string.
 *
 *  @author You
 */
public class SimpleNameMap {

    /** A wrapper class for holding each (KEY, VALUE) pair. */
    private static class Entry {

        /** The key used for lookup. */
        private String _key;
        /** The associated value. */
        private String _value;

        public Entry next;

        /** Create a new (KEY, VALUE) pair. */
        public Entry(String key, String value) {
            _key = key;
            _value = value;
            next = null;
        }

        public Entry(String key, String value, Entry next) {
            _key = key;
            _value = value;
            this.next = next;
        }

        public String value() {
            return _value;
        }

        public String key() {
            return _key;
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

    private Entry[] spmap;
    private int size;
    private int count;

    public SimpleNameMap() {
        spmap = new Entry[10];
        size = 10;
        count = 0;
    }

    /** Returns true if the given KEY is a valid name that starts with A-Z. */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }


    /** Returns true if the map contains the KEY. */
    boolean containsKey(String key) {
        if (!isValidName(key)) {
            return false;
        }
        int index = hash(key);
        Entry temp = spmap[index];
        Entry k = new Entry(key, "");
        while(temp != null) {
            if (temp.keyEquals(k)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /** Returns the value for the specified KEY. */
    String get(String key) {
        int index = hash(key);
        Entry temp = spmap[index];
        Entry k= new Entry(key, "");
        while(temp != null) {
            if (temp.keyEquals(k)) {
                return temp.value();
            }
            temp = temp.next;
        }
        throw new IllegalArgumentException();
    }

    private void resize() {
        Entry oldspmap[] = spmap;
        spmap = new Entry[2 * size];
        for (Entry e : oldspmap) {
            Entry temp = e;
            while (temp != null) {
                int index = temp.key().hashCode() % (2 * size);
                spmap[index] = new Entry(temp.key(), temp.value(), spmap[index]);
                temp = temp.next;
            }
        }
        size = size * 2;
    }

    /** Put a (KEY, VALUE) pair into this map. */
    void put(String key, String value) {
        if (4 * (count + 1) > 3 * size) {
            resize();
        }
        int index = hash(key);
        if (spmap[index] == null) {
            spmap[index] = new Entry(key, value);
            return;
        }
        Entry prev = spmap[index];
        while(prev.next != null) {
            prev = prev.next;
        }
        prev.next = new Entry(key, value);
        count++;
    }

    /** Remove a single entry, KEY, from this table and return the VALUE if successful or NULL otherwise. */
    String remove(String key) {
        int index = hash(key);
        Entry temp = spmap[index];
        Entry k = new Entry(key, "");
        Entry prev = null;
        while (temp != null) {
            if (temp.keyEquals(k)) {
                if (prev == null) {
                    spmap[index] = null;
                } else {
                    prev.next = temp.next;
                }
                count--;
            }
            prev = temp;
            temp = temp.next;
        }
        throw new IllegalArgumentException();
    }

}