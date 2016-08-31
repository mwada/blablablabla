package com.vivareal.spotippos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.service.PropertyService;

@RestController
public class ServerController {

	@Autowired
	private PropertyService propertyService;
	
    @RequestMapping(value = "/properties",
            method = RequestMethod.POST,
            consumes =  { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Property addProperty(@RequestBody Property property)  {
        return propertyService.addProperty(property);
    }

    @RequestMapping(value = "/properties/{id}",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Property getProperty(@PathVariable("id") Long id)  {
        return propertyService.getProperty(id);
    }

    @RequestMapping(value = "/properties",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public PropertiesResponse getProperties(Integer ax, Integer ay, Integer bx, Integer by)  {
        return new PropertiesResponse(propertyService.getProperties(new Boundaries(ax, ay, bx, by)));
    }

}