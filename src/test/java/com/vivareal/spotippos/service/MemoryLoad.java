package com.vivareal.spotippos.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

@Component
public class MemoryLoad  {
	
	@Value("${province.file:/provinces.json}")
	private String provinceFile;

	@Value("${properties.file:/properties.json}")
	private String propertyFile;
	
	@Autowired
	private PropertyService propertyService;
	
	@PostConstruct
	public void load() throws JsonParseException, JsonMappingException, IOException {
		loadProvinceFile().forEach(p->propertyService.addProvince(p));
		loadPropertyFile().forEach(p->propertyService.addProperty(p));
	}
	
	protected List<Province> loadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(provinceFile);
		return  new ObjectMapper().readValue(in, new TypeReference<List<Province>>() {
		});
	}

	protected List<Property> loadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(propertyFile);
	    return new ObjectMapper().readValue(in, new TypeReference<List<Property>>() {
		});
	}

}