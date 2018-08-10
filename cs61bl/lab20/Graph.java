import java.util.*;

public class Graph implements Iterable<Integer>{

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;
    private int startVertex;

    // Initialize a graph with the given number of vertices and no edges.
    public Graph(int numVertices) {
        adjLists = new LinkedList[numVertices];
        startVertex = 0;
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    // Change the vertex the iterator will start DFS from
    public void setStartVertex(int v){
        if (v < vertexCount && v >= 0){
            startVertex = v;
        } else {
            throw new IllegalArgumentException("Cannot set iteration start vertex to " + v + ".");
        }
    }


    // Add to the graph a directed edge from vertex v1 to vertex v2.
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, null);
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2.
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, null);
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addEdge(int v1, int v2, Object edgeInfo) {
        //your code here
        for (int i = 0; i < adjLists[v1].size(); i++) {
            if (adjLists[v1].get(i).to() == v2) {
                adjLists[v1].set(i, new Edge(v1, v2, edgeInfo));
                return;
            }
        }
        adjLists[v1].add(new Edge(v1, v2, edgeInfo));
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addUndirectedEdge(int v1, int v2, Object edgeInfo) {
        //your code here
        addEdge(v1, v2, edgeInfo);
        addEdge(v2, v1, edgeInfo);
    }

    // Return true if there is an edge from vertex "from" to vertex "to";
    // return false otherwise.
    public boolean isAdjacent(int from, int to) {
        //your code here
        for (int i = 0; i < adjLists[from].size(); i++) {
            if (adjLists[from].get(i).to() == to) {
                return true;
            }
        }
        return false;
    }

    // Returns a list of all the neighboring  vertices 'u'
    // such that the edge (VERTEX, 'u') exists in this graph.
    public List<Integer> neighbors(int vertex) {
        // your code here
        List<Integer> nbs = new LinkedList<>();
        for (int i = 0; i < adjLists[vertex].size(); i++) {
            nbs.add(adjLists[vertex].get(i).to());
        }
        return nbs;
    }

    // Return the number of incoming vertices for the given vertex,
    // i.e. the number of vertices v such that (v, vertex) is an edge.
    public int inDegree(int vertex) {
        int count = 0;
        //your code here
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < adjLists[i].size(); j++) {
                if (adjLists[i].get(j).to() == vertex) {
                    count++;
                }
            }
        }
        return count;
    }

    public Iterator<Integer> iterator(){
        return new TopologicalIterator();
    }

    // A class that iterates through the vertices of this graph, starting with a given vertex.
    // Does not necessarily iterate through all vertices in the graph: if the iteration starts
    // at a vertex v, and there is no path from v to a vertex w, then the iteration will not
    // include w
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            //your code here
            fringe = new Stack<>();
            fringe.push(start);
            visited = new HashSet<>();
            visited.add(start);
        }

        public boolean hasNext() {
            //your code here
            return !fringe.isEmpty();
        }

        public Integer next() {
            //your code here
            int v = fringe.pop();
            for (int i = 0; i < adjLists[v].size(); i++) {
                if (!visited.contains(adjLists[v].get(i).to())) {
                    fringe.push(adjLists[v].get(i).to());
                    visited.add(adjLists[v].get(i).to());
                }
            }
            return v;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    // Return the collected result of iterating through this graph's
    // vertices as an ArrayList, starting from STARTVERTEX.
    public ArrayList<Integer> visitAll(int startVertex) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(startVertex);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    // Returns true iff there exists a path from STARVETEX to
    // STOPVERTEX. Assumes both STARTVERTEX and STOPVERTEX are
    // in this graph. If STARVERTEX == STOPVERTEX, returns true.
    public boolean pathExists(int startVertex, int stopVertex) {
        // your code here
        ArrayList<Integer> temp = visitAll(startVertex);
        if (temp.contains(stopVertex)) {
            return true;
        }
        return false;
    }


    // Returns the path from startVertex to stopVertex.
    // If no path exists, returns an empty arrayList.
    // If startVertex == stopVertex, returns a one element arrayList.
    public ArrayList<Integer> path(int startVertex, int stopVertex) {
        ArrayList<Integer> visitedV = new ArrayList<Integer>();
        ArrayList<Integer> p = new ArrayList<Integer>();
        ArrayList<Integer> f = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(startVertex);
        while(iter.hasNext()) {
            int temp = iter.next();
            visitedV.add(temp);
            if (temp == stopVertex) {
                int pre = visitedV.size() - 1;
                p.add(visitedV.get(pre));
                for (int i = pre - 1; i >= 0; i--) {
                    if (isAdjacent(visitedV.get(i), visitedV.get(pre))) {
                        p.add(visitedV.get(i));
                        pre = i;
                    }
                }
                for (int i = p.size() - 1; i >=0; i--) {
                    f.add(p.get(i));
                }
                return f;
            }
        }
        return f;
        // you supply the body of this method
    }

    public ArrayList<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private int[] currentInDegree;

        // more instance variables go here

        public TopologicalIterator() {
            fringe = new Stack<Integer>();
            // more statements go here
            currentInDegree = new int[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < adjLists[i].size(); j++) {
                    currentInDegree[adjLists[i].get(j).to()]++;
                }
            }
            for (int i = 0; i < vertexCount; i++) {
                if (currentInDegree[i] == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            // you supply the real body of this method
            if (!hasNext()) {
                return null;
            }
            int temp = fringe.pop();
            for (int i = 0; i < adjLists[temp].size(); i++) {
                int k = adjLists[temp].get(i).to();
                currentInDegree[k]--;
                if (currentInDegree[k] == 0) {
                    fringe.push(k);
                }
            }
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    private class Edge {

        private Integer from;
        private Integer to;
        private Object edgeInfo;

        public Edge(int from, int to, Object info) {
            this.from = new Integer(from);
            this.to = new Integer(to);
            this.edgeInfo = info;
        }

        public Integer to() {
            return to;
        }

        public Object info() {
            return edgeInfo;
        }

        public String toString() {
            return "(" + from + "," + to + ",dist=" + edgeInfo + ")";
        }

    }

    public static void main(String[] args) {
        ArrayList<Integer> result;

        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(0, 4);
        g1.addEdge(1, 2);
        g1.addEdge(2, 0);
        g1.addEdge(2, 3);
        g1.addEdge(4, 3);
        System.out.println("Traversal starting at 0");
        result = g1.visitAll(0);
        Iterator<Integer> iter;
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 2");
        result = g1.visitAll(2);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 3");
        result = g1.visitAll(3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 4");
        result = g1.visitAll(4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 3");
        result = g1.path(0, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 4");
        result = g1.path(0, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 3");
        result = g1.path(1, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 4");
        result = g1.path(1, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 4 to 0");
        result = g1.path(4, 0);
        if (result.size() != 0) {
            System.out.println("*** should be no path!");
        }

        Graph g2 = new Graph(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(0, 4);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(4, 3);
        System.out.println();
        System.out.println();
        System.out.println("Topological sort");
        result = g2.topologicalSort();
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public Edge getEdge(int u, int v) {
        //your code here...
        for (int i = 0; i < adjLists[u].size(); i++) {
            if (adjLists[u].get(i).to() == v) {
                return adjLists[u].get(i);
            }
        }
        return null;
    }

    public class CompareByDist implements Comparator {
        public int compare(Object o1, Object o2) {
            Dist d1 = (Dist)o1;
            Dist d2 = (Dist)o2;
            if (d1.d() <= d2.d()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private class Dist {

        private int v;
        private int d;
        private int p;

        public Dist(int v, int d, int p) {
            this.v = v;
            this.d = d;
            this.p = p;
        }

        public int v() {
            return v;
        }

        public int d() {
            return d;
        }

        public int p() {
            return p;
        }
    }

    public ArrayList<Integer> shortestPath (int startVertex, int endVertex) {
        //your code here...
        PriorityQueue<Dist> pq = new PriorityQueue<Dist>(3, new CompareByDist());
        int[] prev = new int[vertexCount];
        boolean[] vi = new boolean[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            prev[i] = -1;
            vi[i] = false;
        }
        pq.add(new Dist(startVertex, 0, -1));
        while (!pq.isEmpty()) {
            Dist mind = pq.poll();
            int v = mind.v();
            int d = mind.d();
            int p = mind.p();
            if (!vi[v]) {
                vi[v] = true;
                prev[v] = p;
                for (int i = 0; i < adjLists[v].size(); i++) {
                    Edge e = getEdge(v, adjLists[v].get(i).to());
                    if (!vi[e.to()]) {
                        pq.add(new Dist(e.to(), (Integer)e.info() + d, v));
                    }
                }
            }
            if (v == endVertex) {
                break;
            }
        }
        int temp = endVertex;
        ArrayList<Integer> sp = new ArrayList<>();
        while(temp != -1) {
            sp.add(0, temp);
            temp = prev[temp];
        }
        return sp;
    }
}
