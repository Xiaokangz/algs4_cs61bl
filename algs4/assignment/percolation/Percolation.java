import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] pcGrid;

    private int numberOfOpenSites;

    private WeightedQuickUnionUF qUF;

    private WeightedQuickUnionUF qUF2;

    private int size;

    private int d2s(int row, int col) {
        return row * size + col;
    }

    private void check(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private void unionNeighbor(int row, int col, int row2, int col2) {
        if (row2 >= 0 && row2 < size && col2 >= 0 && col2 < size && pcGrid[row2][col2]) {
            qUF.union(d2s(row, col), d2s(row2, col2));
            qUF2.union(d2s(row, col), d2s(row2, col2));
        }
    }

    /** create n-by-n grid, with all sites blocked */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = n;
        pcGrid = new boolean[size][size];
        qUF = new WeightedQuickUnionUF(size * size);
        qUF2 = new WeightedQuickUnionUF(size * size);
        for (int c = 1; c < size; c++) {
            qUF.union(d2s(0, 0), d2s(0, c));
            qUF2.union(d2s(0, 0), d2s(0, c));
            qUF2.union(d2s(size - 1, 0), d2s(size - 1, c));
            //System.out.print(qUF2.connected(d2s(n - 1, 0), d2s(n - 1, c)) + " ");
        }
        numberOfOpenSites = 0;
    }
    
    /** open site (row, col) if it is not open already */
    public void open(int row, int col) {
        row -= 1;
        col -= 1;
        check(row, col);
        if (pcGrid[row][col] == false) {
            pcGrid[row][col] = true;
            numberOfOpenSites++;
            unionNeighbor(row, col, row + 1, col);
            unionNeighbor(row, col, row - 1, col);
            unionNeighbor(row, col, row, col + 1);
            unionNeighbor(row, col, row, col - 1);
        }
    }
    
    /** is site (row, col) open? */
    public boolean isOpen(int row, int col) {
        row -= 1;
        col -= 1;
        check(row, col);
        return pcGrid[row][col];
    }
    
    /** is site (row, col) full? */
    public boolean isFull(int row, int col) {
        row -= 1;
        col -= 1;
        check(row, col);
        return pcGrid[row][col] && qUF.connected(d2s(row, col), d2s(0, 0));
    }
    
    /** number of open sites */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    
    /** does the system percolate? */
    public boolean percolates() {
        if (size == 1) {
            return pcGrid[0][0];
        }
        return qUF2.connected(d2s(size - 1, size - 1), d2s(0, 0));
    }
    
    /** test client */
    public static void main(String[] args) {
        
    }
}