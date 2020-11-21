package fusion;

import geometry.Geometry;

public class Command {

	Geometry geometry;
	String action;
	Boolean isComplete;

	public Command() {
		this.geometry = null;
		this.action = null;
		this.isComplete = false;
	}
	
	public void clear() {
		this.geometry = null;
		this.action = null;
		this.isComplete = false;
	}

	public void setGeometry(Geometry geom) {
		this.geometry = geom;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public Geometry getGeometry() {
		return this.geometry;
	}

	public String getAction() {
		return this.action;
	}

}
