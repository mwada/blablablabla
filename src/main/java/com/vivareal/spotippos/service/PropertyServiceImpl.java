package com.vivareal.spotippos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivareal.spotippos.exception.EntityNotFoundException;
import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;
import com.vivareal.spotippos.repository.PropertyRepository;
import com.vivareal.spotippos.repository.ProvinceRepository;
import com.vivareal.spotippos.repository.TerritoryRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private TerritoryRepository territoryRepository;

	@Override
	public Property addProperty(Property property) {
		Set<String> provinces = territoryRepository.findProvinces(property.getCoordinate());
		property.setProvinces(provinces);
		property = propertyRepository.add(property);
		territoryRepository.addProperty(property.getCoordinate(), property.getId());
		return property;
	}

	@Override
	public Property getProperty(Long id) {
		Property property =  propertyRepository.find(id);
		if (property == null){
			throw new EntityNotFoundException(id);
		}
		return property;
	}

	@Override
	public List<Property> getProperties(Boundaries boundaries) {
		List<Property> properties = new ArrayList<>();
		for (Long id : territoryRepository.findProperties(boundaries)) {
			properties.add(propertyRepository.find(id));
		}
		return properties;
	}

	@Override
	public Province addProvince(Province province) {
		territoryRepository.addProvince(province.getBoundaries(), province.getName());
		return provinceRepository.add(province);
	}

}