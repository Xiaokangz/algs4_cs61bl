/** A simple implementation of the UnionFind abstract data structure with path
 *  compression. This UnionFind structure only holds integers and there are two
 *  critical operations: union and find. When unioning two elements, the element
 *  contained in a tree of smaller size is placed as a subtree to the root
 *  vertex of the larger tree. Meanwhile finding an element implements path
 *  compression. When we find a particular vertex, that vertex and the 
 *  other vertices from that vertex to the root are automatically
 *  connected to the root of that tree.
 *
 *  Using the union find data structure allows for a fast implementation of
 *  Kruskal's algorithm as well as other set based operations.
 *
 *  @author
 *  @since
 */
public class UnionFind {

    /** Instance variables go here? */
    private int[] uftree;

    /** Returns a UnionFind data structure holding N vertices. Initially, all
     *  vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO implement
        uftree = new int[n];
        for (int i = 0; i < n; i++) {
            uftree[i] = -1;
        }
    }

    /** Returns the size of the set V1 belongs to. */
    public int sizeOf(int v1) {
        // TODO implement
        return -uftree[find(v1)];
    }

    /** Returns the parent of v1. If v1 is the root of a tree, 
     *  returns the negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO implement
        return uftree[v1];
    }

    /** Returns true if nodes V1 and V2 are connected. */
    public boolean isConnected(int v1, int v2) {
        // TODO implement
        return find(v1) == find(v2);
    }

    /** Returns the root of the set VERTEX belongs to. Path-compression, where the
     *  vertices along the search path from VERTEX to its root and VERTEX are linked
     *  directly to the root, is employed allowing for fast search-time. */
    public int find(int vertex) {
        // TODO implement
        if (parent(vertex) < 0) {
            return vertex;
        }
        uftree[vertex] = find(parent(vertex));
        return uftree[vertex];
    }

    /** Connects two elements V1 and V2 together in the UnionFind structure. V1
     *  and V2 can be any element and a union-by-size heuristic is used.
     *  If the sizes are equal, tie break by connecting the first to the second.
     *  Union-ing a vertex with itself or vertices already connected should not 
     *  change anything. */
    public void union(int v1, int v2) {
        // TODO implement
        int r1 = find(v1);
        int r2 = find(v2);
        if (r1 == r2) {
            return;
        }
        if (uftree[r1] < uftree[r2]) {
            uftree[r1] += uftree[r2];
            uftree[r2] = r1;
        } else {
            uftree[r2] += uftree[r1];
            uftree[r1] = r2;
        }
    }
}
