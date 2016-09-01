package com.vivareal.spotippos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.model.Province;

@Component
public class MemoryLoad  {
	
	@Value("${province.file:/provincesrequest.json}")
	private String provinceFile;

	@Value("${properties.file:/propertiesrequest.json}")
	private String propertyFile;
	
	@Autowired
	private ServerController serverController;
	
	@PostConstruct
	public void load() throws JsonParseException, JsonMappingException, IOException {
        serverController.addProvinces(loadProvinceFile());
        serverController.addProperties(loadPropertyFile());
	}
	
	protected Map<String, Province> loadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(provinceFile);
		return  new ObjectMapper().readValue(in, new TypeReference<Map<String, Province>>() {
		});
	}

	protected PropertiesRequest loadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(propertyFile);
	    return new ObjectMapper().readValue(in, new TypeReference<PropertiesRequest>() {
		});
	}

}