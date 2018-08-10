import java.util.*;

/** A class that runs Kruskal's algorithm on a Graph. Given a graph G, Kruskal's
 *  algorithm constructs a new graph T such that T is a spanning tree of G and
 *  the sum of its edge weights is less than the sum of the edge weights for
 *  every possible spanning tree T* of G. This is called the Minimum Spanning
 *  Tree (MST).
 *
 *  @author
 */
public class Kruskal {

    /** Returns the MST of INPUT using a naive isConnected implementation. */
    public static Graph minSpanTree(Graph input) {
        // TODO implement!
        TreeSet<Edge> e = input.getAllEdges();
        PriorityQueue<Edge> pe = new PriorityQueue<>();
        for (Edge n : e) {
            pe.add(n);
        }
        Graph mst = new Graph();
        TreeSet<Integer> v = input.getAllVertices();
        for (int n : v) {
            mst.addVertex(n);
        }
        while (!pe.isEmpty()) {
            Edge now = pe.poll();
            if (!isConnected(mst, now.getDest(), now.getSource())) {
                mst.addEdge(now);
            }
        }
        return mst;
    }

    /** Returns the MST of INPUT using the Union Find data structure. */
    public static Graph minSpanTreeFast(Graph input) {
        // TODO implement!
        TreeSet<Edge> e = input.getAllEdges();
        PriorityQueue<Edge> pe = new PriorityQueue<>();
        for (Edge n : e) {
            pe.add(n);
        }
        Graph mst = new Graph();
        TreeSet<Integer> v = input.getAllVertices();
        for (int n : v) {
            mst.addVertex(n);
        }
        UnionFind uset = new UnionFind(v.size());
        while (!pe.isEmpty()) {
            Edge now = pe.poll();
            if (!uset.isConnected(now.getDest(), now.getSource())) {
                mst.addEdge(now);
                uset.union(now.getDest(), now.getSource());
            }
        }
        return mst;
    }

    /** A naive implementation of BFS or DFS to check if two nodes are
     *  connected. */
    public static boolean isConnected(Graph g, int v1, int v2) {
        // TODO implement!
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;
        vertices.add(v1);
        visited.add(v1);
        while((curr = vertices.poll()) != null) {
            if (curr == v2) {
                return true;
            } else {
                for (int n : g.getNeighbors(curr)) {
                    if (!visited.contains(n)) {
                        vertices.add(n);
                        visited.add(n);
                    }
                }
            }
        }
        return false;
    }
}
