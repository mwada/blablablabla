package com.vivareal.spotippos.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;

@Validated
public interface PropertyService {

	Property addProperty(@Valid Property property);

	Property getProperty(Long id);
	
	List<Property> getProperties(@Valid Boundaries boundaries);

}