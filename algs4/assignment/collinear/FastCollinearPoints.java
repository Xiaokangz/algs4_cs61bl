import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

	private Node first;
	private int numberOfSegments;

	/* finds all line segments containing 4 or more points. */
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}
		for (Point p : points) {
			if (p == null) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		Point[] copy = Arrays.copyOf(points, points.length);
		points = copy;
		Arrays.sort(points);
		for (int i = 1; i < points.length; i += 1) {
			if (points[i].compareTo(points[i - 1]) == 0) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		first = null;
		if (points.length < 4) {
			return;
		}

		for (int i = 0; i < points.length; i += 1) {
			//System.out.println(points[i]);
			Point p = points[i];
			Point[] otherPoints = new Point[points.length - 1];
			int nums = 0;
			for (int j = 0; j < points.length; j += 1) {
				if (i != j) {
					otherPoints[nums] = points[j];
					nums += 1;
				}
			}
			Arrays.sort(otherPoints, points[i].slopeOrder());

			Point begin = min(p, otherPoints[0]);
			Point end = max(p, otherPoints[0]);
			double s = begin.slopeTo(end);

			int numOfPoints = 2;
			for (int j = 1; j < otherPoints.length; j += 1) {
				Point mid = otherPoints[j];
				//System.out.print(begin + " -> " + mid);
				double s1 = p.slopeTo(mid);
				if (s1 != s) {
					if (numOfPoints >= 4 && begin == p) {
						addLineSegment(begin, end);
						numberOfSegments += 1;
					}
					//System.out.println("yes");
					begin = min(p, mid);
					end = max(p, mid);
					s = s1;
					numOfPoints = 2;
				} else {
					numOfPoints += 1;
					//System.out.println(" " + begin + " -> " + end);
					if (mid.compareTo(begin) < 0) {
						//System.out.println(p + " " + mid);
						begin = mid;
					} else if (mid.compareTo(end) > 0) {
						end = mid;
					}
				}
			}
			if (numOfPoints >= 4 && begin == p) {
				addLineSegment(begin, end);
				numberOfSegments += 1;
			}

		}
	}

	private void addLineSegment(Point begin, Point end) {
		Node temp = new Node();
		temp.ls = new LineSegment(begin, end);
		temp.next = first;
		first = temp;
	}

	private Point min(Point p1, Point p2) {
		if (p1.compareTo(p2) < 0) {
			return p1;
		} else {
			return p2;
		}
	}

	private Point max(Point p1, Point p2) {
		if (p1.compareTo(p2) > 0) {
			return p1;
		} else {
			return p2;
		}
	}

	/* the number of line segments. */
	public int numberOfSegments() {
		return numberOfSegments;
	}

	/* the line segments. */
	public LineSegment[] segments() {
		LineSegment[] linesegments = new LineSegment[numberOfSegments];
		Node temp = first;
		for (int i = 0; i < linesegments.length; i += 1) {
			linesegments[i] = temp.ls;
			temp = temp.next;
		}
		return linesegments;
	}

	public static void main(String[] args) {

		
		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}	

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}

	private class Node {
		public LineSegment ls;
		public Node next;
	}
}