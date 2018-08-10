/**
 * A doubly-linked list of integers supporting various sorting algorithms.
 * @author You
 */
public class IntList {

    /* The head of this IntList. */
    IntListNode head;
    /* The tail of this IntList. */
    IntListNode tail;
    /* The size or number of integers in this IntList. */
    int size;

    /**
     * Constructs an empty IntList.
     */
    public IntList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Constructs an IntList with one node. Head and tail are the same.
     */
    public IntList(IntListNode head) {
        this.head = head;
        size = 1;
        this.tail = head;
        for (IntListNode i = head.next; i != null; i = i.next) {
            size++;
            this.tail = i;
        }
        this.size = size;
    }

    /**
     * Returns true if this list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a new node with the given value to the front of this list.
     */
    public void addToFront(int k) {
        if (head == null) {
            head = tail = new IntListNode(k);
        } else {
            head = new IntListNode(k, null, head);
            head.next.prev = head;
        }
        size++;
    }

    /**
     * Adds a new node with the given value to the end of this list.
     */
    public void addToEnd(int k) {
        if (head == null) {
            head = tail = new IntListNode(k);
        } else {
            tail.next = new IntListNode(k, tail, null);
            tail = tail.next;
        }
        size++;
    }

    /**
     * Attaches the input list to the end of this list.
     */
    public void append(IntList list) {
        if (list.isEmpty()) {
            return;
        }
        if (isEmpty()) {
            head = list.head;
            tail = list.tail;
            size = list.size;
            return;
        }
        tail.next = list.head;
        list.head.prev = tail;
        tail = list.tail;
        size += list.size;
    }

    /**
     * Removes the node reference by p from this list.
     */
    private void remove(IntListNode p) {
        if (head == tail) {
            head = tail = null;
        } else if (p == head) {
            head = head.next;
            head.prev = null;
        } else if (p == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            p.next.prev = p.prev;
            p.prev.next = p.next;
        }
        size--;
    }

    @Override
    public String toString() {
        String s = "";
        for (IntListNode p = head; p != null; p = p.next) {
            s = s + p.item + " ";
        }
        return s;
    }

    private class IntListNode {

        int item;
        IntListNode prev;
        IntListNode next;

        public IntListNode(int k) {
            this.item = k;
            this.prev = this.next = null;
        }

        public IntListNode(int k, IntListNode prev, IntListNode next) {
            this.item = k;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Returns the result of sorting the values in this list using the insertion
     * sort algorithm. Only the list returned will be checked for the correct value.
     * The original list can be modified if you choose to.
     */
    public IntList insertionSort() {
        IntListNode soFar = null;
        for (IntListNode p = head; p != null; p = p.next) {
            soFar = insert(p, soFar);
        }
        return new IntList(soFar);
    }

    /**
     * Inserts the node p into the list headed by head so that the node values
     * are in increasing order. You are allowed to create new IntListNodes.
     */
    private IntListNode insert(IntListNode p, IntListNode head) {
        // TODO your code here!
        IntListNode pre = null;
        IntListNode temp = head;
        while (temp != null && temp.item < p.item) {
            pre = temp;
            temp = temp.next;
        }
        IntListNode newp = new IntListNode(p.item, pre, temp);
        if (pre == null) {
            head = newp;
        } else {
            pre.next = newp;
        }
        if (temp != null) {
            temp.prev = newp;
        }
        return head;
    }

    /**
     * Sorts this list using the selection sort algorithm.
     */
    public void selectionSort() {
        IntList sorted = new IntList();
        while (head != null) {
            int minSoFar = head.item;
            IntListNode minPtr = head;
            // Find the node in the list pointed to by head
            // whose value is largest.
            for (IntListNode p = head; p != null; p = p.next) {
                if (p.item < minSoFar) {
                    minSoFar = p.item;
                    minPtr = p;
                }
            }
            sorted.addToEnd(minSoFar);
            remove(minPtr);
        }
        head = sorted.head;
    }

    /**
     * Returns the result of sorting the values in this list using the Quicksort
     * algorithm. This list is no longer usable after this operation.
     */
    public IntList quicksort() {
        if (size <= 1) {
            return this;
        }
        // Assume first element is the divider.
        IntList smallElements = new IntList();
        IntList largeElements = new IntList();
        int divider = head.item;
        // TODO your code here!
        IntListNode temp = head.next;
        while (temp != null) {
            if (temp.item < divider) {
                smallElements.addToEnd(temp.item);
            } else {
                largeElements.addToEnd(temp.item);
            }
            temp = temp.next;
        }
        IntList newsmallElements = smallElements.quicksort();
        IntList newlargeElements = largeElements.quicksort();
        newsmallElements.addToEnd(divider);
        newsmallElements.append(newlargeElements);
        return newsmallElements;
    }

    /**
     * Returns the result of sorting the values in this list using the merge
     * sort algorithm. This list is no longer usable after this operation.
     */
    public IntList mergeSort() {
        if (size <= 1) {
            return this;
        }
        IntList oneHalf = new IntList();
        IntList otherHalf = new IntList();
        // TODO your code here!
        IntListNode temp = head;
        for (int i = 0; i < size; i++) {
            if (i < size / 2) {
                oneHalf.addToEnd(temp.item);
            } else {
                otherHalf.addToEnd(temp.item);
            }
            temp = temp.next;
        }
        IntList newoneHalf = oneHalf.mergeSort();
        IntList newotherHalf = otherHalf.mergeSort();
        return merge(newoneHalf.head, newotherHalf.head);
    }

    /**
     * Returns the result of merging the two sorted lists / represented by list1
     * and list2.
     */
    private static IntList merge(IntListNode list1, IntListNode list2) {
        IntList rtn = new IntList();
        while (list1 != null && list2 != null) {
            if (list1.item < list2.item) {
                rtn.addToEnd(list1.item);
                list1 = list1.next;
            } else {
                rtn.addToEnd(list2.item);
                list2 = list2.next;
            }
        }
        while (list1 != null) {
            rtn.addToEnd(list1.item);
            list1 = list1.next;
        }
        while (list2 != null) {
            rtn.addToEnd(list2.item);
            list2 = list2.next;
        }
        return rtn;
    }

    /**
     * Returns a random integer between 0 and 99.
     */
    private static int randomInt() {
        return (int) (100 * Math.random());
    }

    public static void main(String[] args) {
        IntList values;
        IntList sortedValues;
        values = new IntList();
        System.out.print("Before selection sort: ");
        for (int k = 0; k < 10; k++) {
            values.addToFront(randomInt());
        }
        System.out.println(values);
        values.selectionSort();
        System.out.print("After selection sort: ");
        System.out.println(values);

        values = new IntList();
        System.out.print("Before insertion sort: ");
        for (int k = 0; k < 10; k++) {
            values.addToFront(randomInt());
        }
        System.out.println(values);
        sortedValues = values.insertionSort();
        System.out.print("After insertion sort: ");
        System.out.println(sortedValues);

        values = new IntList();
        System.out.print("Before quicksort: ");
        for (int k = 0; k < 10; k++) {
            values.addToFront(randomInt());
        }
        System.out.println(values);
        sortedValues = values.quicksort();
        System.out.print("After quicksort: ");
        System.out.println(sortedValues);

        values = new IntList();
        System.out.print("Before merge sort: ");
        for (int k = 0; k < 10; k++) {
            values.addToFront(randomInt());
        }
        System.out.println(values);
        sortedValues = values.mergeSort();
        System.out.print("After merge sort: ");
        System.out.println(sortedValues);
    }

}
