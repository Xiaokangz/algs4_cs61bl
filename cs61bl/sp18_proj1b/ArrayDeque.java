public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private int capacity;

    /** A resizing method */
    private void resize(int c) {
        T[] newitems = (T[]) new Object[c];
        for (int i = 0; i < size; i += 1) {
            newitems[i] = items[(nextFirst + 1 + i) % capacity];
        }
        nextFirst = c - 1;
        nextLast = size;
        capacity = c;
        items = newitems;
    }

    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
        capacity = 8;
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = (nextFirst + capacity - 1) % capacity;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % capacity;
    }

    /** Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[(nextFirst + 1 + i) % capacity] + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (capacity >= 16 && capacity / 4 == size) {
            resize(capacity / 2);
        }
        nextFirst = (nextFirst + 1) % capacity;
        size -= 1;
        return items[nextFirst];
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if ( capacity >= 16 && capacity / 4 == size) {
            resize(capacity / 2);
        }
        nextLast = (nextLast + capacity -1) % capacity;
        size -= 1;
        return items[nextLast];
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque! */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % capacity];
    }

}
