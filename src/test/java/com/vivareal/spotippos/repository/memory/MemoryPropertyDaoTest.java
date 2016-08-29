package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.internal.util.reflection.Whitebox;

import com.vivareal.spotippos.model.Property;

public class MemoryPropertyDaoTest {

	private MemoryPropertyDao dao;

	private AtomicLong propertySeq;

	private Map<Long, Property> propertyDB;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void before() {
		dao = new MemoryPropertyDao();
		propertySeq = (AtomicLong) Whitebox.getInternalState(dao, "propertySeq");
		propertyDB = (Map) Whitebox.getInternalState(dao, "propertyDB");
	}

	@Test
	public void testAddProperty() {
		Property property = createProperty(0L);
		Property retrievedProperty = dao.add(property);
		assertThat(retrievedProperty, new ReflectionEquals(property, "id"));
		Long id = property.getId();
		assertThat(id, is(propertySeq.get() - 1));
		assertThat(retrievedProperty, new ReflectionEquals(propertyDB.get(id)));
	}

	@Test
	public void testFindInvalidProperty() {
		Property retrievedProperty = dao.find(1L);
		assertThat(retrievedProperty, is(nullValue()));
	}

	@Test
	public void testFindValidProperty() {
		Property property = createProperty(0L);
		property.setId(propertySeq.getAndIncrement());
		propertyDB.put(property.getId(), property);
		Property retrievedProperty = dao.find(1L);
		assertThat(retrievedProperty, new ReflectionEquals(property));
	}
	
	@Test
	public void testLoadProperties() {
		List<Property> properties = Arrays.asList(createProperty(1L), createProperty(2L));
		dao.load(properties);
		assertThat(propertyDB.keySet(), hasSize(2));
		assertThat(propertyDB.keySet().containsAll(Arrays.asList(1L, 2L)), is(true));
		assertThat(propertySeq.get(), is(3L));
	}
	
	private Property createProperty(Long id) {
		return new Property()
				.withId(id)
				.withTitle("title " + id)
				.withPrice((int) (long) id)
				.withDescription("description " +id)
				.withX((int) (long)id)
				.withY((int) (long)id)
				.withBeds((int) (long)id)
				.withBaths((int) (long)id)
				.withSquareMeters((int) (long)id);
	}

}