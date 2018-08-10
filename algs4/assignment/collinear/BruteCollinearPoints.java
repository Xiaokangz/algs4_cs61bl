import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {

	private int numberOfSegments;
	private Node first;


	/* finds all line segments containing 4 points. */
	public BruteCollinearPoints(Point[] points) {
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
		for (int p1 = 0; p1 < points.length; p1 += 1) {
			for (int p2 = p1 + 1; p2 < points.length; p2 += 1) {
				for (int p3 = p2 + 1; p3 < points.length; p3 += 1) {
					for (int p4 = p3 + 1; p4 < points.length; p4 += 1) {
						double s1 = points[p1].slopeTo(points[p2]);
						double s2 = points[p1].slopeTo(points[p3]);
						double s3 = points[p1].slopeTo(points[p4]);
						if (s1 == s2 && s1 == s3) {
							numberOfSegments += 1;
							Node temp = new Node();
							temp.ls = new LineSegment(points[p1], points[p4]);
							temp.next = first;
							first = temp;
						}
					}
				}
			}
		}
	}

	/*
	private static void sort(Point[] points) {
		for (int i = 1; i < points.length; i +=1) {
			Point temp = points[i];
			for (int j = i; j > 0; j -= 1) {
				if (temp.compareTo(points[j - 1]) < 0) {
					points[j] = points[j - 1];
				} else {
					points[j] = temp;
					break;
				}
			}
		}
	}*/

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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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