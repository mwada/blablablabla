package com.vivareal.spotippos.service;

import java.util.List;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;

public interface PropertyService {

	Property addProperty(Property property);

	Property getProperty(Long id);
	
	List<Property> getProperties(Boundaries boundaries);

}