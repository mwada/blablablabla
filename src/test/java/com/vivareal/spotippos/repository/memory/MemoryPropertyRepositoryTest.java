package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import com.vivareal.spotippos.model.Property;

@RunWith(MockitoJUnitRunner.class)
public class MemoryPropertyRepositoryTest {

	@InjectMocks
	private MemoryPropertyRepository repository = new MemoryPropertyRepository();

	@Spy
	private AtomicLong propertySeq = new AtomicLong(0);

	@Spy
	private Map<Long, Property> propertyDb = new HashMap<>();

	@Test
	public void testAddProperty() {
		Property property = createProperty(0L);
		Property retrievedProperty = repository.add(property);
		assertThat(retrievedProperty, new ReflectionEquals(property, "id"));
		Long id = property.getId();
		assertThat(id, is(propertySeq.get() - 1));
		assertThat(retrievedProperty, samePropertyValuesAs(propertyDb.get(id)));
	}

	@Test
	public void testFindInvalidProperty() {
		Property retrievedProperty = repository.find(1L);
		assertThat(retrievedProperty, is(nullValue()));
	}

	@Test
	public void testFindValidProperty() {
		Property property = createProperty(0L);
		property.setId(propertySeq.getAndIncrement());
		propertyDb.put(property.getId(), property);
		Property retrievedProperty = repository.find(property.getId());
		assertThat(retrievedProperty, samePropertyValuesAs(property));
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