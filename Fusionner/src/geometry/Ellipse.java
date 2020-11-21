package geometry;

public class Ellipse implements Geometry {
	
	String name;
	Coords coords;
	String color;

	public Ellipse() {
		this.name = "E0";
		this.coords = new Coords(0, 0);
		this.color = "black";
	}
	
	public Ellipse(String name) {
		this.name = name;
		this.coords = new Coords(0, 0);
		this.color = "black";
	}
	
	public Ellipse(String name, int x, int y) {
		this.name = name;
		this.coords = new Coords(x, y);
		this.color = "black";
	}
	
	public Ellipse(String name, String color) {
		this.name = name;
		this.coords = new Coords(0, 0);
		this.color = color;

	}
	
	public Ellipse(String name, int x, int y, String color) {
		this.name = name;
		this.coords = new Coords(x, y);
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void translate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColor(String color) {
		this.color = color;
		
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	@Override
	public void setPosition(int x, int y) {
		this.coords.setX(x);
		this.coords.setY(y);
	}

	@Override
	public Coords getPosition() {
		// TODO Auto-generated method stub
		return this.coords;
	}

}
