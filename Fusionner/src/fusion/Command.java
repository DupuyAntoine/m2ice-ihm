package fusion;

import geometry.Geometry;

public class Command {

	Geometry geometry;
	String command;
	Boolean isComplete;

	public Command() {
		this.geometry = null;
		this.isComplete = false;
	}
	
	public void clear() {
		this.geometry = null;
		this.isComplete = false;
	}

}
