package com.vivareal.spotippos.repository.memory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

@Service
public class MemoryLoader {

	private final static String PROVINCE_FILE = "provinces.json";

	private final static String PROPERTY_FILE = "properties.json";

	@Autowired
	private MemoryTerritoryDao memoryTerritoryDao;

	@Autowired
	private MemoryPropertyDao memoryPropertyDao;
	
	@PostConstruct
	protected void load() throws JsonParseException, JsonMappingException, IOException {
		List<Province> provinces = loadProvinceFile();
		List<Property> properties = loadPropertyFile();
		properties = memoryTerritoryDao.loadTerritory(provinces, properties);
		memoryPropertyDao.loadProperties(properties);
	}
	
	protected List<Province> loadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(PROVINCE_FILE).getFile());
		Map<String, Province> provinces =  new ObjectMapper().readValue(file, new TypeReference<Map<String, Province>>() {
		});
		provinces.forEach((name, province)->province.setName(name));
		return new ArrayList<>(provinces.values());
	}

	protected List<Property> loadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(PROPERTY_FILE).getFile());
		Properties properties = new ObjectMapper().readValue(file, new TypeReference<Properties>() {
		});
		return properties.getProperties();
	}
	

	public static class Properties {
		private Integer totalProperties;

		private List<Property> properties;

		public Integer getTotalProperties() {
			return totalProperties;
		}

		public void setTotalProperties(Integer totalProperties) {
			this.totalProperties = totalProperties;
		}

		public List<Property> getProperties() {
			return properties;
		}
	}

}