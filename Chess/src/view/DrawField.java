package view;

/**
 *
 * @author Robbert
 */
public class DrawField {
	
	private Point[] points;
	private float[] color;
	private Point middle;
	private double scale;
	
	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double factor) {
		this.scale = factor;
	}

	public Point getMiddle() {
		return middle;
	}

	public void setMiddle(Point middle) {
		this.middle = middle;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}
	
	public DrawField() {
		
	}
}
