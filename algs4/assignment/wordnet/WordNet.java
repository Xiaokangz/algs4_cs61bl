import java.util.ArrayList;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {

	private SAP sap;
	private ST<String, Queue<Integer>> st;
	private ArrayList<String> synset;
	private int[] outdegree;

	// construct takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) {
			throw new IllegalArgumentException();
		}
		st = new ST<String, Queue<Integer>>();
		In in = new In(synsets);
		synset = new ArrayList<String>();
		int len  = 0;
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			String[] contents = line.split(",");
			int num = Integer.parseInt(contents[0]);
			synset.add(contents[1]);
			String strs[] = contents[1].split(" ");
			for (int i = 0; i < strs.length; i += 1) {
				if (!st.contains(strs[i])) {
					Queue<Integer> q = new Queue<Integer>();
					q.enqueue(num);
					st.put(strs[i], q);
				} else {
					Queue<Integer> q = st.get(strs[i]);
					q.enqueue(num);
				}
			}
			len += 1;
		}
		in.close();
		Digraph G = new Digraph(len);
		outdegree = new int[len];
		in = new In(hypernyms);
		while(true) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			String[] contents = line.split(",");
			int v = Integer.parseInt(contents[0]);
			for (int i = 1; i < contents.length; i += 1) {
				int w = Integer.parseInt(contents[i]);
				G.addEdge(v, w);
				outdegree[v] += 1;
			}
		}
		int cnt = 0;
		for (int i = 0; i < len ; i += 1) {
			if (outdegree[i] == 0) {
				cnt += 1;
			}
		}
		DirectedCycle dc = new DirectedCycle(G);
		if (cnt != 1 || dc.hasCycle()) {
			throw new java.lang.IllegalArgumentException();
		}
		sap = new SAP(G);
		in.close();
	}

	// returns  all WordNet nouns
	public Iterable<String> nouns() {
		return st.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		return st.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB)) {
			throw new IllegalArgumentException();
		}
		return sap.length(st.get(nounA), st.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB)) {
			throw new IllegalArgumentException();
		}
		int ancestor = sap.ancestor(st.get(nounA), st.get(nounB));
		if (ancestor == -1) {
			return null;
		}
		return synset.get(ancestor);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		
	}
}