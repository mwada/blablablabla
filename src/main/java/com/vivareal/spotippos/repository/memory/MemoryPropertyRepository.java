package com.vivareal.spotippos.repository.memory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.repository.PropertyRepository;

@Repository
public class MemoryPropertyRepository implements PropertyRepository {

	private AtomicLong propertySeq;

	private Map<Long, Property> propertyDb;

	public MemoryPropertyRepository() {
		propertySeq = new AtomicLong(1);
		propertyDb = new ConcurrentHashMap<>();
	}

	@Override
	public Property add(Property property) {
		if (property.getId() == null) {
			property.setId(propertySeq.getAndIncrement());
			propertyDb.put(property.getId(), property);
		} else {
			propertyDb.put(property.getId(), property);
			propertySeq.set(Collections.max(propertyDb.keySet()) + 1);
		}
		return property;
	}

	@Override
	public Property find(Long id) {
		return propertyDb.get(id);
	}

}