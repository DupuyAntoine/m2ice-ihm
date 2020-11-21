package geometry;

public interface Geometry {
	public void translate(int x, int y);
	// public void rotate(int angle);
	public void setColor(String color);
	public String getName();
	public String getColor();
	public void setPosition(int x, int y);
	public Coords getPosition();

}
