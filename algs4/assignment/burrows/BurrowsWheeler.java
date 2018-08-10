import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {

	private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
    	String s = BinaryStdIn.readString();
    	CircularSuffixArray csArray = new CircularSuffixArray(s);
    	int n = csArray.length();
    	for (int i = 0; i < n; i++) {
    		if (csArray.index(i) == 0) {
    			BinaryStdOut.write(i);
    			break;
    		}
    	}
    	for (int i = 0; i < n; i++) {
    		BinaryStdOut.write(s.charAt((csArray.index(i) + n - 1) % n));
    	}
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    	int first;
    	first = BinaryStdIn.readInt();
    	Queue<Character> q = new Queue<Character>();
    	while (!BinaryStdIn.isEmpty()) {
    		char ch = BinaryStdIn.readChar();
    		q.enqueue(ch);
    	}
    	int n = q.size();
    	Node[] t = new Node[n];
    	for (int i = 0; i < n; i++) {
    		t[i] = new Node(q.dequeue(), i);
    	}
    	int[] count = new int[R + 1];
    	Node[] aux = new Node[n];

    	for (int i = 0; i < n; i++) {
    		count[t[i].c + 1]++;
    	}

    	for (int r = 0; r < R; r++) {
    		count[r + 1] += count[r];
    	}

    	for (int i = 0; i < n; i++) {
    		aux[count[t[i].c]++] = t[i];
    	}

    	for (int i = 0; i < n; i++) {
    		BinaryStdOut.write(aux[first].c);
    		first = aux[first].index;
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	if (args[0].equals("-")) {
    		transform();
    	} else if (args[0].equals("+")) {
    		inverseTransform();
    	}
    }

    private static class Node {
    	public char c;

    	public int index;

    	public Node(char ch, int i) {
    		c = ch;
    		index = i;
    	}
    }
}