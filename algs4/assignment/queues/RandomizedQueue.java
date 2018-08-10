import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int capacity;

	private int size;

	private Item[] q; 

	/** construct an empty randomized queue */
	public RandomizedQueue() {
		capacity = 1;
		q = (Item[]) new Object[capacity];
		size = 0;
	}

	/** is the randomized queue empty? */
	public boolean isEmpty() {
		return size == 0;
	}

	/** return the number of items on the randomized queue */
	public int size() {
		return size;
	}

	private void resize(int c) {
		Item[] temp = (Item[]) new Object[c];
		for (int i = 0; i < size; i++) {
			temp[i] = q[i];
		}
		capacity = c;
		q = temp;
	}

	private void swap(int i, int j) {
		Item temp = q[i];
		q[i] = q[j];
		q[j] = temp;
	}

	/** add the item */
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (size == capacity) {
			resize(capacity * 2);
		}
		q[size++] = item;
		int r = StdRandom.uniform(size);
		swap(size - 1, r);
	}

	/** remove and return a random item */
	public Item dequeue() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		if (size * 4 == capacity) {
			resize(capacity / 2);
		}
		Item item = q[size - 1];
		q[size - 1] = null;
		size -= 1;
		return item;
	}

	/** return a random item (but do not remove it) */
	public Item sample() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		int r = StdRandom.uniform(size);
		//return q[(head + r) % capacity];
		return q[r];
	}

	/** return an independent iterator over items in random order */
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	/** unit testing */
	public static void main(String[] args) {

	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		private int[] randomSeq;

		private int cnt;

		private void swap(int i, int j) {
			int temp = randomSeq[i];
			randomSeq[i] = randomSeq[j];
			randomSeq[j] = temp;
		}

		public RandomizedQueueIterator() {
			randomSeq = new int[size];
			for (int i = 0; i < size; i++) {
				randomSeq[i] = i;
			}
			for (int i = 0; i < size; i++) {
				int r = StdRandom.uniform(i, size);
				swap(i, r);
			}
			cnt = 0;
		}

		public boolean hasNext() {
			return cnt < size;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item item = q[randomSeq[cnt]];
			cnt += 1;
			return item;
		}
	}
}