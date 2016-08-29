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

	private Map<Long, Property> propertyDB;

	public MemoryPropertyDao() {
		propertySeq = new AtomicLong(1);
		propertyDB = new ConcurrentHashMap<>();
	}

	@Override
	public Property add(Property property) {
		property.setId(propertySeq.getAndIncrement());
		propertyDB.put(property.getId(), property);
		return property;
	}

	@Override
	public Property find(Long id) {
		return propertyDB.get(id);
	}

	protected void load(List<Property> properties) {
		for (Property property : properties) {
			propertyDB.put(property.getId(), property);
		}
		propertySeq.set(Collections.max(propertyDB.keySet()) + 1);
	}
}