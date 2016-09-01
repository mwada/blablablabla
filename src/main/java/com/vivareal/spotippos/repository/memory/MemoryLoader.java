package com.vivareal.spotippos.repository.memory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

@Service
public class MemoryLoader {

	@Value("${province.file:/provinces.json}")
	private String provinceFile;

	@Value("${properties.file:/properties.json}")
	private String propertyFile;

	@Autowired
	private MemoryTerritoryRepository memoryTerritoryDao;

	@Autowired
	private MemoryPropertyRepository memoryPropertyDao;
	
	@PostConstruct
	protected void load() throws JsonParseException, JsonMappingException, IOException {
		List<Province> provinces = loadProvinceFile();
		List<Property> properties = loadPropertyFile();
		properties = memoryTerritoryDao.loadTerritory(provinces, properties);
		memoryPropertyDao.loadProperties(properties);
	}
	
	protected List<Province> loadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(provinceFile);
		Map<String, Province> provinces =  new ObjectMapper().readValue(in, new TypeReference<Map<String, Province>>() {
		});
		provinces.forEach((name, province)->province.setName(name));
		return new ArrayList<>(provinces.values());
	}

	protected List<Property> loadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
	    InputStream in = getClass().getResourceAsStream(propertyFile);
	    JsonProperties properties = new ObjectMapper().readValue(in, new TypeReference<JsonProperties>() {
		});
		return properties.getProperties();
	}
	
	public static class JsonProperty extends Property {
		
		private static final long serialVersionUID = 1L;

		public void setLat(Integer x) {
			this.setX(x);
		}
		public void setLong(Integer y) {
			this.setY(y);
		}
	}
	
	public static class JsonProperties {
		private Integer totalProperties;

		private List<JsonProperty> properties;

		public Integer getTotalProperties() {
			return totalProperties;
		}

		public void setTotalProperties(Integer totalProperties) {
			this.totalProperties = totalProperties;
		}

		public List<Property> getProperties() {
			List<Property> propertiesList = new ArrayList<>();
			for (JsonProperty p : properties) {
				Property property = new Property();
				BeanUtils.copyProperties(p, property);
				propertiesList.add(property);
			}
			return propertiesList;
		}
	}

}