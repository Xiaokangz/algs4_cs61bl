public class LinkedListDeque<T> implements Deque<T> {

    /** A private LinkedListNode class. */
    private class LinkedListNode<T> {

        public T item;
        public LinkedListNode<T> prev;
        public LinkedListNode<T> next;

        /** The default constructor method. */
        public LinkedListNode() {
            item = null;
            prev = null;
            next = null;
        }

        /** A one parameter constructor method. */
        public LinkedListNode(T item) {
            this.item = item;
            prev = null;
            next = null;
        }

        /** A recursive get function in LinkedListNode class. */
        public T get(int index) {
            if (index == 0) {
                return item;
            }
            if (next == sentinel) {
                return null;
            }
            return next.get(index - 1);
        }
    }

    private LinkedListNode<T> sentinel;    // sentinel node
    private int size;                      // size of the LinkedListDeque

    /** Creates an empyt linked list deque. */
    public LinkedListDeque() {
        sentinel = new LinkedListNode<T>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        LinkedListNode<T> first = new LinkedListNode<>(item);
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        LinkedListNode<T> last = new LinkedListNode<>(item);
        last.prev = sentinel.prev;
        last.next = sentinel;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
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
        LinkedListNode<T> l = sentinel.next;
        while (l != sentinel) {
            System.out.print(l.item + " ");
            l = l.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return item;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque! */
    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        LinkedListNode<T> l = sentinel.next;
        while (l != sentinel) {
            if (index == 0) {
                return l.item;
            }
            index -= 1;
            l = l.next;
        }
        return null;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (index < 0 || size == 0) {
            return null;
        }
        return sentinel.next.get(index);
    }
}
