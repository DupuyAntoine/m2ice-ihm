package fusion;

import geometry.Coords;
import geometry.Geometry;

public class Command {

	Geometry geometry;
	Coords position;
	

	public Command() {
		this.geometry = null;
		this.position = new Coords(0, 0);
	}

}
