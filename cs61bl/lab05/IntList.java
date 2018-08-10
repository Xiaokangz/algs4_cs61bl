import jh61b.junit.In;

/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    private int item;
    /** The next node in this IntList. */
    private IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints (1 2 3) */
    public static IntList list(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /** Returns the integer stored by this IntList. */
    public int item() {
        return item;
    }

    /** Returns the next node stored by this IntList. */
    public IntList next() {
        return next;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        // YOUR CODE HERE
        if (position < 0) {
            throw new IllegalArgumentException("illegal position");
        } else if (position == 0) {
            return item();
        } else if (next() == null) {
            throw new IllegalArgumentException("position out of range");
        } else {
            return next().get(position - 1);
        }
        //return 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     */
    public int size() {
        // YOUR CODE HERE
        if (next() == null) {
            return 1;
        } else {
            return 1 + next().size();
        }
        //return 0;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "( 1 2 3 )".
     *
     * @return The String representation of the list.
     */
    public String helpertS(String result) {
        if (next() == null) {
            return result + item() + " ";
        } else {
            return next().helpertS(result + item() + " ");
        }
    }

    public String toString() {
        // YOUR CODE HERE
        return "( " + helpertS("") + ")";
        //return null;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        // YOUR CODE HERE
        if (IntList.class.isInstance(obj)) {
            IntList other = (IntList)obj;
            if (this.item() != other.item()) {
                return false;
            } else if (other.next() == null && this.next() == null) {
                return true;
            } else if (this.next() == null) {
                return false;
            } else {
                return this.next().equals(other.next());
            }
        } else {
            return false;
        }
    }

    /**
     * Adds the given item at the end of the list.
     *
     * @param item, the int to be added.
     */
    public void add(int item) {
        if (this.next() == null) {
            this.next = new IntList(item);
        } else {
            this.next().add(item);
        }
        // YOUR CODE HERE
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        // YOUR CODE HERE
        if (this.next() == null) {
            return this.item();
        } else {
            int temp = this.next().smallest();
            if (temp < this.item()) {
                return temp;
            } else {
                return this.item();
            }
        }
        //return 0;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        // YOUR CODE HERE
        if (this.next() == null) {
            return this.item() * this.item();
        } else {
            return this.item() * this.item() + this.next().squaredSum();
        }
        //return 0;
    }

    /**
     * Returns a new IntList consisting of L1 followed by L2,
     * non-destructively.
     *
     * @param l1 list to be on the front of the new list.
     * @param l2 list to be on the back of the new list.
     * @return new list with L1 followed by L2.
     */
    private static IntList appendhelper(IntList l, IntList r) {
        if (l == null) {
            return r;
        } else {
            return appendhelper(l.next(), new IntList(l.item(), r));
        }
    }

    private  static IntList reverse(IntList l) {
        return appendhelper(l, null);
    }

    public static IntList append(IntList l1, IntList l2) {
        // YOUR CODE HERE
        return reverse(appendhelper(l2, appendhelper(l1, null)));
        //return null;
    }
}