package com.vivareal.spotippos.controller;

import java.io.Serializable;
import java.util.List;

import com.vivareal.spotippos.model.Property;

public class PropertiesResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer foundProperties;
	
	private List<Property> properties;

	public PropertiesResponse(List<Property> properties) {
		this.foundProperties = properties.size();
		this.properties = properties;
	}

	public Integer getFoundProperties() {
		return foundProperties;
	}

	public void setFoundProperties(Integer foundProperties) {
		this.foundProperties = foundProperties;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}