import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Item item;
		Node prev;
		Node next;
	}

	private Node sentinel;

	private int size;

	/** construct an empty deque */
	public Deque() {
		sentinel = new Node();
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}

	/** is the deque empty? */
	public boolean isEmpty() {
		return size == 0;
	}

	/** return the number of items on the deque */
	public int size() {
		return size;
	}

	/** add the item to the front */
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node temp = new Node();
		temp.item = item;
		temp.next = sentinel.next;
		sentinel.next.prev = temp;
		sentinel.next = temp;
		temp.prev = sentinel;
		size += 1;
	}

	/** add the item to the end */
	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node temp = new Node();
		temp.item = item;
		temp.prev = sentinel.prev;
		sentinel.prev.next = temp;
		sentinel.prev = temp;
		temp.next = sentinel;
		size += 1;
	}

	/** remove and return the item from the front */
	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item = sentinel.next.item;
		sentinel.next.next.prev = sentinel;
		sentinel.next = sentinel.next.next;
		size -= 1;
		return item;
	}

	/** remove and return the item from the end */
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item item = sentinel.prev.item;
		sentinel.prev.prev.next = sentinel;
		sentinel.prev = sentinel.prev.prev;
		size -= 1;
		return item;
	}

	/** return an iterator over items in order from front to end */
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	/** unit testing */
	public static void main(String[] args) {
		
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current = sentinel.next;

		public boolean hasNext() {
			return current != sentinel;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}