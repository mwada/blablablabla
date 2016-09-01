package com.vivareal.spotippos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.service.PropertyService;

@RestController
public class ServerController {

	@Autowired
	private PropertyService propertyService;
	
	@PostMapping(value = "/properties",
            consumes =  { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Property addProperty(@RequestBody Property property)  {
        return propertyService.addProperty(property);
    }

    @GetMapping(value = "/properties/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Property getProperty(@PathVariable("id") Long id)  {
        return propertyService.getProperty(id);
    }

    @GetMapping(value = "/properties",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public PropertiesResponse getProperties(Integer ax, Integer ay, Integer bx, Integer by)  {
        return new PropertiesResponse(propertyService.getProperties(new Boundaries(ax, ay, bx, by)));
    }
    
    public static class PropertiesResponse {
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

}