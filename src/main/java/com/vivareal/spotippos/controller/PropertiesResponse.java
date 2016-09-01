package com.vivareal.spotippos.controller;

import java.util.List;

import com.vivareal.spotippos.model.Property;

public class PropertiesResponse {

	private Integer foundProperties;
	private List<Property> properties;

	public PropertiesResponse() {
	}

	public PropertiesResponse(List<Property> properties) {
		this.foundProperties = properties.size();
		this.properties = properties;
	}

	public Integer getFoundProperties() {
		return foundProperties;
	}

	public List<Property> getProperties() {
		return properties;
	}
}
