import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

	private SET<Point2D> pSet;

	/* construct an empty set of points */
	public PointSET() {
		pSet = new SET<Point2D>();
	}

	/* is the set empty? */
	public boolean isEmpty() {
		return pSet.isEmpty();
	}

	/* number of points in the set */
	public int size() {
		return pSet.size();
	}

	/* add the point to the set (if it is not already in the set) */
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (!pSet.contains(p)) {
			pSet.add(p);
		}
	}

	/* does the set contain point p? */
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return pSet.contains(p);
	}

	/* draw all points to standard draw */
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for (Point2D p : pSet) {
			p.draw();
		}
	}

	/* all points that are inside the rectangle (or on the boundary) */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		Queue<Point2D> q = new Queue<Point2D>();
		for (Point2D p : pSet) {
			if (rect.contains(p)) {
				q.enqueue(p);
			}
		}
		return q;
	}

	/* a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		Point2D minPoint = null;
		double minDis = -1.0;
		for (Point2D p2 : pSet) {
			double tempDis = p.distanceTo(p2);
			if (minPoint == null || tempDis < minDis) {
				minPoint = p2;
				minDis = tempDis;
			}
		}
		return minPoint;
	}

	/* unit testing */
	public static void main(String[] args) {

	}
}