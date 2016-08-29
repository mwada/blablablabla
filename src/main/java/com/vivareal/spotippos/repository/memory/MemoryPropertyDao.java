package com.vivareal.spotippos.repository.memory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.repository.PropertyDao;

@Repository
public class MemoryPropertyDao implements PropertyDao {

	private AtomicLong propertySeq;

	private Map<Long, Property> propertyDb;

	public MemoryPropertyDao() {
		propertySeq = new AtomicLong(1);
		propertyDb = new ConcurrentHashMap<>();
	}

	@Override
	public Property add(Property property) {
		property.setId(propertySeq.getAndIncrement());
		propertyDb.put(property.getId(), property);
		return property;
	}

	@Override
	public Property find(Long id) {
		return propertyDb.get(id);
	}

	protected void loadProperties(List<Property> properties) {
		for (Property property : properties) {
			propertyDb.put(property.getId(), property);
		}
		propertySeq.set(Collections.max(propertyDb.keySet()) + 1);
	}
}