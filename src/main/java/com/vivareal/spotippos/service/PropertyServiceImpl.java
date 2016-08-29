package com.vivareal.spotippos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.repository.TerritoryDao;
import com.vivareal.spotippos.repository.PropertyDao;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDao propertyDao;

	@Autowired
	private TerritoryDao territoryDao;

	@Override
	public Property addProperty(Property property) {
		Set<String> provinces = territoryDao.findProvinces(property.getCoordinate());
		property.setProvinces(provinces);
		property = propertyDao.add(property);
		territoryDao.addProperty(property.getCoordinate(), property.getId());
		return property;
	}

	@Override
	public Property getProperty(Long id) {
		return propertyDao.find(id);
	}

	@Override
	public List<Property> getProperties(Boundaries boundaries) {
		List<Property> properties = new ArrayList<>();
		for (Long id : territoryDao.findProperties(boundaries)) {
			properties.add(propertyDao.find(id));
		}
		return properties;
	}

}