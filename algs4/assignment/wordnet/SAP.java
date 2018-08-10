import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class SAP {

	private Digraph g;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null) {
			throw new java.lang.IllegalArgumentException();
		}
		g = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
		int ancestor = ancestor(bfsv,bfsw);
		if (ancestor != -1) {
			return bfsv.distTo(ancestor) + bfsw.distTo(ancestor);
		}
		return -1;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
		return ancestor(bfsv, bfsw);
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
		int ancestor = ancestor(bfsv,bfsw);
		if (ancestor != -1) {
			return bfsv.distTo(ancestor) + bfsw.distTo(ancestor);
		}
		return -1;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(g, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(g, w);
		return ancestor(bfsv, bfsw);
	}

	private int ancestor(BreadthFirstDirectedPaths bfsv, BreadthFirstDirectedPaths bfsw) {
		int minL = -1;
		int ancestor = -1;
		for (int i = 0; i < g.V(); i += 1) {
			if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
				int d = bfsv.distTo(i) + bfsw.distTo(i);
				if (minL == -1 || d < minL) {
					minL = d;
					ancestor = i;
				}
			}
		}
		return ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}