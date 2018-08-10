import java.util.Objects;

/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 * Encapsulated version.
 */
public class IntList {

    /**
     * The head of the list is the first node in the list. 
     * If the list is empty, head is null 
     */
    private IntListNode head;
    private int size;

    /**
     * IntListNode is a nested class. It can be instantiated
     * when associated with an instance of IntList.
     */
    public class IntListNode {
        int item;
        IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        public String toString() {
            return "IntListNode{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item &&
                    Objects.equals(next, that.next);
        }

    }

    public int getSize() {
        return size;
    }

    public IntList() {}

    public IntList(int[] initial) {
        for (int i = initial.length - 1; i >= 0; i--) {
            head = new IntListNode(initial[i], head);
        }
        size = initial.length;
    }

    /**
     * Get the value at position pos. If the position does not exist, throw an
     * IndexOutOfBoundsException.
     * @param position to get from
     * @return the int at the position in the list.
     */
    public int get(int position) {
        if (position >= size) throw new IndexOutOfBoundsException("Position larger than size of list.");
        IntListNode curr = head;
        while (position > 0) {
            curr = curr.next;
            position--;
        }
        return curr.item;
    }

    /* Fill in below! */

    /**
     * Insert a new node into the IntList.
     * @param x value to insert
     * @param position position to insert into. If position exceeds the size of the list, insert into
     *            the end of the list.
     */
    public void insert(int x, int position) {
        // Fill me in!
        if (position >= size) {
            position = size;
        }
        IntListNode curr = head;
        IntListNode prev = null;
        while (position > 0) {
            prev = curr;
            curr = curr.next;
            position--;
        }
        IntListNode e = new IntListNode(x, curr);
        if (prev == null) {
            head = e;
        } else {
            prev.next = e;
        }
        size++;
    }

    /**
     * Merge two sorted IntLists a and b into one sorted IntList containing all of their elements.
     * @return a new IntList without modifying either parameter
     */
    public static IntList merge(IntList a, IntList b) {
        // Fill me in!
        IntList c = new IntList();
        int i = 0;
        int j = 0;
        while (i < a.getSize() && j < b.getSize()) {
            if (a.get(i) < b.get(j)) {
                c.insert(a.get(i),c.getSize());
                i++;
            } else {
                c.insert(b.get(j), c.getSize());
                j++;
            }
        }
        while (i < a.getSize()) {
            c.insert(a.get(i), c.getSize());
            i++;
        }
        while (j < b.getSize()) {
            c.insert(b.get(j), c.getSize());
            j++;
        }
        return c;
        //return null;
    }

    /**
     * Reverse the current list recursively, using a helper method.
     */
    public void reverse() {
        // Fill me in!
        head = reverseHelper(this.head, null);
    }

    /* Optional! */
    public static IntListNode reverseHelper(IntListNode l, IntListNode soFar) {
        if (l == null) {
            return soFar;
        } else {
            IntListNode m = l.next;
            l.next = soFar;
            return reverseHelper(m, l);
        }
    }

    /**
     * Remove the node at position from this list.
     * @param position int representing the index of the node to remove. If greater than the size
     *                 of this list, throw an IndexOutOfBoundsException.
     */
    private IntListNode getNode(int position) {
        if (position >= size) throw new IndexOutOfBoundsException("Position larger than size of list.");
        IntListNode curr = head;
        while (position > 0) {
            curr = curr.next;
            position--;
        }
        return curr;
    }

    public void remove(int position) {
        if (position >= size) throw new IndexOutOfBoundsException();
        // fill me in
        if (position == 0) {
            head = head.next;
        } else {
            IntListNode prev = getNode(position - 1);
            prev.next = prev.next.next;

        }
        size--;
    }

    public String toString() {
        return "IntList{" +
                "head=" + head +
                ", size=" + size +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntList intList = (IntList) o;
        return size == intList.size &&
                Objects.equals(head, intList.head);
    }

}
