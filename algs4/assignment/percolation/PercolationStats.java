import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

	private double mean;

	private double stddev;

	private double confidenceLo;

	private double confidenceHi; 

	/** perform trials independent experiments on an n-by-n grid */
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		int[] a = new int[trials];
		for (int i = 0; i < trials; i++) {
			Percolation pc = new Percolation(n);
			while(!pc.percolates()) {
				int row = StdRandom.uniform(n);
				int col = StdRandom.uniform(n);
				pc.open(row + 1, col + 1);
			}
			a[i] = pc.numberOfOpenSites();
		}
		mean = StdStats.mean(a) / (n * n);
		stddev = StdStats.stddev(a) / (n * n);
		confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
		confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
	}

	/** sample mean of percolation threshold */
	public double mean() {
		return mean;
	}

	/** sample standard deviation of percolation threshold */
	public double stddev() {
		return stddev;
	}

	/** low endpoint of 95% confidentce interval */
	public double confidenceLo() {
		return confidenceLo;
	}

	/** high endpoint of 95% confidence interval */
	public double confidenceHi() {
		return confidenceHi;
	}

	/** test client */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, trials);
		System.out.println(ps.mean() + " " + ps.stddev());
	}
}
	
