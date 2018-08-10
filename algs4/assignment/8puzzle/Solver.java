import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

	private boolean isSolvable;
	private int moves;
	private searchNode goal;

	/* find a solution to the initial board
	 * (using the A* algorithm) */
	public Solver(Board initial) {
		if (initial == null) {
			throw new java.lang.IllegalArgumentException();
		}
		//System.out.println(initial.toString());
		Board twin = initial.twin();
		//System.out.println(initial.toString());
		MinPQ<searchNode> nodes = new MinPQ<searchNode>();
		MinPQ<searchNode> twinnodes = new MinPQ<searchNode>();
		nodes.insert(new searchNode(initial, null));
		twinnodes.insert(new searchNode(twin, null));
		while (true) {
			//System.out.println("yes");
			if (!nodes.isEmpty()) {
				searchNode node = nodes.delMin();
				Board board = node.board;
				//System.out.println("yes");
				//System.out.println(board.toString());
				if (board.isGoal()) {
					goal = node;
					moves = node.moves;
					isSolvable = true;
					return;
				}
				for (Board b : board.neighbors()) {
					//System.out.println(b.toString());
					if (node.predecessor == null || !b.equals(node.predecessor.board)) {
						nodes.insert(new searchNode(b, node));
					}
				}
			}
			if (!twinnodes.isEmpty()) {
				searchNode twinnode = twinnodes.delMin();
				Board twinboard = twinnode.board;
				//System.out.println(twinboard.toString());
				if (twinboard.isGoal()) {
					goal = null;
					moves = -1;
					isSolvable = false;
					return;
				}
				for (Board b : twinboard.neighbors()) {
					if (twinnode.predecessor == null || !b.equals(twinnode.predecessor.board)) {
						twinnodes.insert(new searchNode(b, twinnode));
					}
				}
			}
		}
	}


	/* is the initial board solvable? */
	public boolean isSolvable() {
		return isSolvable;
	}

	/* min number of moves to solve initial board; -1 if unsolvable. */
	public int moves() {
		return moves;
	}

	/* sequence of board in a shortest solution; null if unsolvable. */
	public Iterable<Board> solution() {
		if (!isSolvable) {
			return null;
		}
		Stack<Board> stack = new Stack<Board>();
		searchNode temp = goal;
		while (temp != null) {
			stack.push(temp.board);
			temp = temp.predecessor;
		}
		return stack;
	}

	/* solve a slider puzzle. */
	public static void main(String[] args) {

	}

	private class searchNode implements Comparable<searchNode> {
		public int hamming;
		public int manhattan;
		public int moves;
		public Board board;
		public searchNode predecessor;

		public searchNode(Board board, searchNode predecessor) {
			this.board = board;
			this.predecessor = predecessor;
			hamming = board.hamming();
			manhattan = board.manhattan();
			if (predecessor == null) {
				moves = 0;
			} else {
				moves = predecessor.moves + 1;
			}
		}

		public int compareTo(searchNode other) {
			int priority = manhattan + moves;
			int otherPriority = other.manhattan + other.moves;
			if (priority < otherPriority) {
				return -1;
			} 
			if (priority > otherPriority) {
				return 1;
			}
			return 0;
		}
	}
}