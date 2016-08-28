package com.vivareal.spotippos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Boundaries implements Serializable {

	private static final long serialVersionUID = 1L;

	private Coordinate upperLeft;
	private Coordinate bottomRight;

	public Boundaries() {
	}

	public Boundaries(Integer upperLeftX,
			Integer upperLeftY,
			Integer bottomRightX,
			Integer bottomRightY) {
		this.upperLeft = new Coordinate(upperLeftX, upperLeftY);
		this.bottomRight = new Coordinate(bottomRightX, bottomRightY);
	}

	public Coordinate getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(Coordinate upperLeft) {
		this.upperLeft = upperLeft;
	}

	public Coordinate getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Coordinate bottomRight) {
		this.bottomRight = bottomRight;
	}
	
	public List<Coordinate> getCoordinates() {
		List<Coordinate> coordinates = new ArrayList<>();
		for (int x = upperLeft.getX(); x <= bottomRight.getX(); x++) {
			for (int y = upperLeft.getY(); y <= bottomRight.getY(); y++) {
				coordinates.add(new Coordinate(x, y));
			}
		}
		return coordinates;
	}
	
}