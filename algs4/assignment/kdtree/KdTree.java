import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTree {

	private Node root;
	private int size;

	/* construct an empty set of points */
	public KdTree() {
		root = null;
		size = 0;
	}

	/* is the set empty? */
	public boolean isEmpty() {
		return size == 0;
	}

	/* number of points in the set */
	public int size() {
		return size;
	}

	/* add the point to the set (if it is not already in the set) */
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		root = insert(root, p, null, true);
	}

	private Node insert(Node temp, Point2D p, Node prev, boolean isVertical) {
		if (temp == null) {
			size += 1;
			if (prev == null) {
				return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
			}
			if (isVertical) {
				if (p.y() < prev.p.y()) {
					return new Node(p, new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.rect.xmax(), prev.p.y()));
				} else {
					return new Node(p, new RectHV(prev.rect.xmin(), prev.p.y(), prev.rect.xmax(), prev.rect.ymax()));
				}
			} else {
				if (p.x() < prev.p.x()) {
					return new Node(p, new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.p.x(), prev.rect.ymax()));
				} else {
					return new Node(p, new RectHV(prev.p.x(), prev.rect.ymin(), prev.rect.xmax(), prev.rect.ymax()));
				}
			}
		}
		if (p.equals(temp.p)) {
			return temp;
		}
		if (isVertical) {
			if (p.x() < temp.p.x()) {
				temp.lb = insert(temp.lb, p, temp, !isVertical);
			} else {
				temp.rt = insert(temp.rt, p, temp, !isVertical);
			}
		} else {
			if (p.y() < temp.p.y()) {
				temp.lb = insert(temp.lb, p, temp, !isVertical);
			} else {
				temp.rt = insert(temp.rt, p, temp, !isVertical);
			}
		}
		return temp;
	}

	/* does the set contain point p? */
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return contains(root, p);
	}

	private boolean contains(Node temp, Point2D p) {
		if (temp == null || !temp.rect.contains(p)) {
			return false;
		}
		if (p.equals(temp.p)) {
			return true;
		}
		if (contains(temp.lb, p)) {
			return true;
		}
		return contains(temp.rt, p);
	}

	/* draw all points to standard draw */
	public void draw() {
		StdDraw.setScale();
		draw(root, true);
	}

	private void draw(Node temp, boolean isVertical) {
		if (temp == null) {
			return;
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(temp.p.x(), temp.p.y());
		StdDraw.setPenRadius();
		if (isVertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(temp.p.x(), temp.rect.ymin(), temp.p.x(), temp.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(temp.rect.xmin(), temp.p.y(), temp.rect.xmax(), temp.p.y());
		}
		//StdDraw.show();
		draw(temp.lb, !isVertical);
		draw(temp.rt, !isVertical);
	}

	/* all points that are inside the rectangle (or on the boundary) */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		Queue<Point2D> q = new Queue<Point2D>();
		range(q, rect, root);
		return q;
	}

	private void range(Queue<Point2D> q, RectHV rect, Node temp) {
		if (temp == null || !rect.intersects(temp.rect)) {
			return;
		}
		if (rect.contains(temp.p)) {
			q.enqueue(temp.p);
		}
		range(q, rect, temp.lb);
		range(q, rect, temp.rt);
	}

	/* a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (root == null) {
			return null;
		}
		return nearest(root, root.p, p);
	}

	private Point2D nearest(Node temp, Point2D nearestSofar, Point2D p) {
		if (temp == null) {
			return nearestSofar;
		}
		if (p.distanceSquaredTo(temp.p) < nearestSofar.distanceSquaredTo(p)) {
			nearestSofar = temp.p;
		}
		if (temp.lb == null && temp.rt == null) {
			return nearestSofar;
		} else if (temp.lb == null) {
			if (temp.rt.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.rt, nearestSofar, p);
			}
		} else if (temp.rt == null) {
			if (temp.lb.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.lb, nearestSofar, p);
			}
		} else if (temp.lb.rect.distanceSquaredTo(p) < temp.rt.rect.distanceSquaredTo(p)) {
			if (temp.lb.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.lb, nearestSofar, p);
			}
			if (temp.rt.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.rt, nearestSofar, p);
			}
		} else {
			if (temp.rt.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.rt, nearestSofar, p);
			}
			if (temp.lb.rect.distanceSquaredTo(p) < nearestSofar.distanceSquaredTo(p)) {
				nearestSofar = nearest(temp.lb, nearestSofar, p);
			}
		}
		return nearestSofar;
	}

	/* unit testing */
	public static void main(String[] args) {
		double timeOfInsert = 0.0;
        double timeOfNearest = 0.0;
        double timeOfRange = 0.0;
        KdTree kdtree = new KdTree();
        Stopwatch timer;
        Point2D p;

        for (int i = 0; i < 1000000; i++)
        {
            p = new Point2D(StdRandom.uniform(0.0, 1.0), 
                            StdRandom.uniform(0.0, 1.0));
            timer = new Stopwatch();
            kdtree.insert(p);
            timeOfInsert += timer.elapsedTime();
        }
        StdOut.print("time cost of insert(random point)(1M times)    : ");
        StdOut.println(timeOfInsert);

        for (int i = 0; i < 100; i++)
        {
            p = new Point2D(StdRandom.uniform(0.0, 1.0), 
                            StdRandom.uniform(0.0, 1.0));
            timer = new Stopwatch();
            kdtree.nearest(p);
            timeOfNearest +=  timer.elapsedTime();
        }
        StdOut.print("time cost of nearest(random point)(100 times)  : ");
        StdOut.println(timeOfNearest);

        for (int i = 0; i < 100; i++)
        {
            double xmin = StdRandom.uniform(0.0, 1.0);
            double ymin = StdRandom.uniform(0.0, 1.0);
            double xmax = StdRandom.uniform(0.0, 1.0);
            double ymax = StdRandom.uniform(0.0, 1.0);
            RectHV rect;

            if (xmin > xmax) 
            {
                double swap = xmin;

                xmin = xmax;
                xmax = swap;
            }
            if (ymin > ymax) 
            {
                double swap = ymin;

                ymin = ymax;
                ymax = swap;
            }
            rect = new RectHV(xmin, ymin, xmax, ymax);
            timer = new Stopwatch();
            kdtree.range(rect);
            timeOfRange += timer.elapsedTime();
        }
        StdOut.print("time cost of range(random rectangle)(100 times): ");
        StdOut.println(timeOfRange);
    }

	private static class Node{
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree

		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
			lb = null;
			rt = null;
		}
	}
}