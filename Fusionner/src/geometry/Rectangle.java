package geometry;

public class Rectangle implements Geometry {
	
	String name;
	Coords coords;
	String color;

	public Rectangle() {
		this.name = "R0";
		this.coords = new Coords(0, 0);
		this.color = "black";
	}
	
	public Rectangle(String name) {
		this.name = name;
		this.coords = new Coords(0, 0);
		this.color = "black";
	}
	
	public Rectangle(String name, int x, int y) {
		this.name = name;
		this.coords = new Coords(x, y);
		this.color = "black";
	}
	
	public Rectangle(String name, String color) {
		this.name = name;
		this.coords = new Coords(0, 0);
		this.color = color;

	}
	
	public Rectangle(String name, int x, int y, String color) {
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
	
	public Coords getPosition() {
		return this.coords;
	}

	@Override
	public void setPosition(int x, int y) {
		this.coords.setX(x);
		this.coords.setY(y);
		
	}

}
