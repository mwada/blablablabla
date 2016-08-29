package com.vivareal.spotippos.model;

import java.io.Serializable;

public class Province implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Boundaries boundaries;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boundaries getBoundaries() {
		return boundaries;
	}

	public void setBoundaries(Boundaries boundaries) {
		this.boundaries = boundaries;
	}
	
	public Province withName(String name) {
		setName(name);
		return this;
	}
	
	public Province withBoundaries(Boundaries boundaries) {
		setBoundaries(boundaries);
		return this;
	}



}