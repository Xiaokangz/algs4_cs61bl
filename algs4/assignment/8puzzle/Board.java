import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class Board {

	private int[][] tiles;
	private int n;

	/* construct a board from an n-by-n array of blocks
	 * (where blocks[i][j] = block in row i, column j)*/
	public Board(int[][] blocks) {
		n = blocks.length;
		tiles = copyBlocks(blocks);
	}

	private int[][] copyBlocks(int[][] blocks) {
		int[][] copy = new int[blocks.length][blocks.length];
		for (int i = 0; i < n; i += 1) {
			for (int j = 0; j < n; j += 1) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}

	private int getRow(int value) {
		return (value - 1) / n;
	}

	private int getCol(int value) {
		return (value - 1) % n;
	}

	private int getVal(int row, int col) {
		return row * n + col + 1;
	}

	/* board dimension n. */
	public int dimension() {
		return n;
	}

	/* number of blocks out of place. */
	public int hamming() {
		int nSquare = n * n;
		int nums = 0;
		for (int i = 1; i < nSquare; i += 1) {
			int row = getRow(i);
			int col = getCol(i);
			if (tiles[row][col] != i) {
				nums += 1;
			}
		}
		return nums;
	}

	/* sum of Manhattan distances between blocks and goal. */
	public int manhattan() {
		int nums = 0;
		for (int row = 0; row < n; row += 1) {
			for (int col = 0; col < n; col += 1) {
				if (tiles[row][col] != 0) {
					int rRow = getRow(tiles[row][col]);
					int rCol = getCol(tiles[row][col]);
					nums += (Math.abs(row - rRow) + Math.abs(col - rCol));
				}
			}
		}
		return nums;
	}

	/* is this board the goal board? */
	public boolean isGoal() {
		int nSquare = n * n;
		for (int i = 1; i < nSquare; i += 1) {
			int row = getRow(i);
			int col = getCol(i);
			if (tiles[row][col] != i) {
				return false;
			}
		}
		return tiles[n - 1][n - 1] == 0;
	}

	/* a board that is obtained by exchanging any pair of blocks. */
	public Board twin() {
		int nSquare = n * n;
		for (int i = 1; i < nSquare; i += 1) {
			int row1 = getRow(i);
			int col1 = getCol(i);
			int row2 = getRow(i + 1);
			int col2 = getCol(i + 1);
			if (tiles[row1][col1] != 0 && tiles[row2][col2] != 0) {
				return newBoard(row1, col1, row2, col2);
			}
		}
		return null;
	}

	private static void exch(int[][] a, int row1, int col1, int row2, int col2) {
		int temp = a[row1][col1];
		a[row1][col1] = a[row2][col2];
		a[row2][col2] = temp;
	}

	private Board newBoard(int row1, int col1, int row2, int col2) {
		int[][] copy = copyBlocks(tiles);
		exch(copy, row1, col1, row2, col2);
		return new Board(copy);
	}

	/* does this board equal y? */
	public boolean equals(Object y) {
		if (y == this) {
			return true;
		}
		if (y == null) {
			return false;
		}
		if (this.getClass() != y.getClass()) {
			return false;
		}
		Board anotherBoard = (Board) y;
		if (this.n != anotherBoard.n) {
			return false;
		}
		for (int i = 0; i < n; i += 1) {
			for (int j = 0; j < n; j += 1) {
				if (tiles[i][j] != anotherBoard.tiles[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/* all neighboring boards */
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<Board>();
		for (int row = 0; row < n; row += 1) {
			for (int col = 0; col < n; col += 1) {
				if (tiles[row][col] == 0) {
					//System.out.println("yes");
					//System.out.println(toString());
					if (row > 0) {
						//System.out.println(newBoard(row, col, row - 1, col));
						neighbors.enqueue(newBoard(row, col, row - 1, col));
					}
					if (row < n - 1) {
						//System.out.println(newBoard(row, col, row + 1, col));
						neighbors.enqueue(newBoard(row, col, row + 1, col));
					}
					if (col > 0) {
						//System.out.println(newBoard(row, col, row, col - 1));
						neighbors.enqueue(newBoard(row, col, row, col - 1));
					}
					if (col < n - 1) {
						//System.out.println(newBoard(row, col, row, col + 1));
						neighbors.enqueue(newBoard(row, col, row, col + 1));
					}
					return neighbors;
				}
			}
		}
		return neighbors;
	}

	/* string representation of this board
	 * (in the output format specified below). */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int row = 0; row < n; row += 1) {
			for (int col = 0; col < n; col += 1) {
				s.append(String.format("%2d ", tiles[row][col]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	/* unit tests. */
	public static void main(String[] args) {

	}
}