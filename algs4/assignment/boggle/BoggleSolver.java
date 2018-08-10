import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class BoggleSolver {

	private TrieSET trieSet;

	private final int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};

	private final int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		trieSet = new TrieSET();
		for (int i = 0; i < dictionary.length; i += 1) {
			trieSet.add(dictionary[i]);
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		SET<String> allValidWords = new SET<String>();
		int m = board.rows();
		int n = board.cols();
		boolean[][] vi = new boolean[m][n];
		for (int i = 0; i < m; i += 1) {
			for (int j = 0; j < n; j += 1) {
				String word;
				char ch  = board.getLetter(i, j);
				if (ch == 'Q') {
					word = "QU";
				} else {
					word = "" + ch;
				}
				dfs(board, i, j, vi, allValidWords, word);
			}
		}
		return allValidWords;
	}

	private void dfs(BoggleBoard board, int x, int y, boolean[][] vi, SET<String> allValidWords, String word) {
		if (!trieSet.hasPrefix(word)) {
			return;
		}
		if (word.length() >= 3 && trieSet.contains(word) && !allValidWords.contains(word)) {
			allValidWords.add(word);
		}
		vi[x][y] = true;
		int m = board.rows();
		int n = board.cols();
		for (int i = 0; i < dx.length; i += 1) {
			int x1 = x + dx[i];
			int y1 = y + dy[i];
			if (x1 >= 0 && x1 < m && y1 >= 0 && y1 < n) {
				if (!vi[x1][y1]) {
					String newWord;
					char ch = board.getLetter(x1, y1);
					if (ch == 'Q') {
						newWord = word + "QU";
					} else {
						newWord = word + ch;
					}
					dfs(board, x1, y1, vi, allValidWords, newWord);
				}
			} 
		}
		vi[x][y] = false;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		if (!trieSet.contains(word)) {
			return 0;
		}
		if (word.length() <= 2) {
			return 0;
		} else if (word.length() <= 4) {
			return 1;
		} else if (word.length() == 5) {
			return 2;
		} else if (word.length() == 6) {
			return 3;
		} else if (word.length() == 7) {
			return 5;
		} else {
			return 11;
		}
	}

	public static void main(String[] args) {
    	In in = new In(args[0]);
    	String[] dictionary = in.readAllStrings();
    	BoggleSolver solver = new BoggleSolver(dictionary);
    	BoggleBoard board = new BoggleBoard(args[1]);
    	int score = 0;
	    for (String word : solver.getAllValidWords(board)) {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
	    }
	    StdOut.println("Score = " + score);
	}

	private class TrieSET {
		private static final int R = 26;

		private static final char OFFSET = 'A';

		private Node root;

		private class Node {
			private Node[] next = new Node[R];
			private boolean isString;
		}

		public TrieSET() {
		}

		public boolean contains(String key) {
			Node x = get(root, key, 0);
			if (x == null) {
				return false;
			}
			return x.isString;
		}

		private Node get(Node x, String key, int d) {
			if (x == null) {
				return null;
			}
			if (d == key.length()) {
				return x;
			}
			int c = key.charAt(d) - OFFSET;
			return get(x.next[c], key, d + 1);
		}

		public void add(String key) {
			root = add(root, key, 0);
		}

		private Node add(Node x, String key, int d) {
			if (x == null) {
				x = new Node();
			} 
			if (d == key.length()) {
				x.isString = true;
			} else {
				int c = key.charAt(d) - OFFSET;
				x.next[c] = add(x.next[c], key, d + 1);
			}
			return x;
		}

		public boolean hasPrefix(String prefix) {
			Node x = get(root, prefix, 0);
			if (x == null) {
				return false;
			}
			return true;
		}
	}

}