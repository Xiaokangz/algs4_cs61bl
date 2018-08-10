/** A class that represents a path via pursuit curves.
 *  @author You!
 */
public class Path {

    /** What to do, what to do... */
    private Point currPoint;
    private Point nextPoint;

    public Path(double x, double y) {
    	this.currPoint = new Point();
    	this.nextPoint = new Point(x, y);
    }

   	public double getCurrX() {
   		return this.currPoint.getX();
   	}

   	public double getCurrY() {
   		return this.currPoint.getY();
   	}

   	public double getNextX() {
   		return this.nextPoint.getX();
   	}

   	public double getNextY() {
   		return this.nextPoint.getY();
   	}

   	public Point getCurrentPoint() {
   		return this.currPoint;
   	}

   	public void setCurrentPoint(Point point) {
   		this.currPoint.setX(point.getX());
   		this.currPoint.setY(point.getY());
   	}

   	public void iterate(double dx, double dy) {
   		this.setCurrentPoint(this.nextPoint);
   		this.nextPoint.setX(this.getCurrX() + dx);
   		this.nextPoint.setY(this.getCurrY() + dy);
   	}
}
