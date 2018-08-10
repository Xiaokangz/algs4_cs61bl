public class FixedSizeList implements SimpleList {

    /** List of elements. */
    protected int[] values;
    /** Number of array cells used by the list. */
    int count;

    /** Initializes a FixedSizeList with specified capacity. The capacity is the
     *  the actual size of the array (i.e. the max number of items it can hold).
     */
    public FixedSizeList(int capacity) {
        // YOUR CODE HERE
        count = 0;
        values = new int[capacity];
    }

    /** Returns the number of items in the list. */
    public int size() {
        // YOUR CODE HERE
        return count;
    }

    /** Returns true if the list is empty, else false. */
    public boolean isEmpty() {
        // YOUR CODE HERE
        return count == 0;
    }

    /** Adds the int k to the list by placing it in the first unused spot in
     *  values.
     */
    public void add(int k) {
        // YOUR CODE HERE
        add(count, k);
    }

    /** Removes k from the list if it is present. If k appears multiple times,
     *  it only removes the first occurence of k.
     */
    public void remove(int k) {
        // YOUR CODE HERE
        for (int i = 0; i < count; i++) {
            if (values[i] == k) {
                removeIndex(i);
                return;
            }
        }
    }

    /** Returns if the collection contains k. */
    public boolean contains(int k) {
        // YOUR CODE HERE
        for (int i = 0; i < count; i++) {
            if (values[i] == k) {
                return true;
            }
        }
        return false;
    }

    /** Returns the integer stored at the i-th index in the list. */
    public int get(int i) {
        // YOUR CODE HERE
        if (i >= count) {
            throw new ListException("i out of range");
        }
        return values[i];
    }

    /** Inserts k into the list at position i by shifting each element at index
     *  i and onwards one entry to the right.
     *  Precondition: i is between 0 and count, inclusive.
     */
    public void add(int i, int k) {
        if (count == values.length) {
            throw new ListException("No more space for adding");
        }
        for (int j = count; j >= i + 1; j--) {
            values[j] = values[j-1];
        }
        values[i] = k;
        count++;
    }

    /** Removes the entry at position i by shifting each element after position
     *  i one entry to the left.
     */
    public void removeIndex(int i) {
        // YOUR CODE HERE
        if (i < 0 || i >= count) {
            throw new ListException("remove error");
        }
        for (int j = i + 1; j < count; j ++) {
            values[j - 1] = values[j];
        }
        count--;
    }

}
