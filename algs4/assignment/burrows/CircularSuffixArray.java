import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

	private int n;

	private Integer[] index;

	private String str;

	// circular suffix array of s
	public CircularSuffixArray(String s) {
		if (s == null) {
			throw new java.lang.IllegalArgumentException();
		}
		n = s.length();
		index = new Integer[n];
		str = s;
		for (int i = 0; i < n; i++) {
			index[i] = i;
		}
		Arrays.sort(index, new CircularSuffixComparator());
	}

	// length of s
   	public int length() {
   		return n;
   	}

   	// returns index of ith sorted suffix
  	public int index(int i) {
  		if (i < 0 || i >= n) {
  			throw new java.lang.IllegalArgumentException();
  		}
  		return index[i];
  	}

  	// unit testing (required)
   	public static void main(String[] args) {
   		String s = "ABRACADABRA!";
    	CircularSuffixArray testArr = new CircularSuffixArray(s);
    	int len = testArr.length();
    	for (int i = 0; i < len; i++) {
    		StdOut.println(testArr.index(i));
    	}
   	}

   	private class CircularSuffixComparator implements Comparator<Integer> {
   		public int compare(Integer a, Integer b) {
   			for (int i = 0; i < n; i++) {
   				int i1 = (i + a) % n;
   				int i2 = (i + b) % n;
   				if (str.charAt(i1) < str.charAt(i2)) {
   					return -1;
   				} else if (str.charAt(i1) > str.charAt(i2)) {
   					return 1;
   				}
   			}
   			return 0;
   		}
   	}
}