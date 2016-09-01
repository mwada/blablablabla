package com.vivareal.spotippos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.vivareal.spotippos.model.Property;

public class PropertiesRequest {

	private Integer totalProperties;

	private List<PropertyRequest> properties;
	
	public PropertiesRequest() {
	}

	public Integer getTotalProperties() {
		return totalProperties;
	}

	public void setTotalProperties(Integer totalProperties) {
		this.totalProperties = totalProperties;
	}

	public List<Property> getProperties() {
		List<Property> propertiesList = new ArrayList<>();
		for (PropertyRequest p : properties) {
			Property property = new Property();
			BeanUtils.copyProperties(p, property);
			propertiesList.add(property);
		}
		return propertiesList;
	}

	public static class PropertyRequest extends Property {

		private static final long serialVersionUID = 1L;

		public PropertyRequest() {
		}

		public void setLat(Integer x) {
			this.setX(x);
		}

		public void setLong(Integer y) {
			this.setY(y);
		}
	}

}