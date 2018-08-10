import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

	private WordNet wordnet;

	// construct takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given and array of WordNet nouns, return an object
	public String outcast(String[] nouns) {
		int[] d = new int[nouns.length];
		for (int i = 0; i < nouns.length; i += 1) {
			for (int j = i + 1; j < nouns.length; j+= 1) {
				int dist = wordnet.distance(nouns[i], nouns[j]);
				d[i] += dist;
				d[j] += dist;
			}
		}
		int maxid = 0;
		for (int i = 1; i < nouns.length; i += 1) {
			if (d[i] > d[maxid]) {
				maxid = i;
			}
		}
		return nouns[maxid];
	}

	// see test client below
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}