package com.vivareal.spotippos.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.internal.util.reflection.Whitebox;

import com.vivareal.spotippos.exception.EntityNotFoundException;
import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.repository.PropertyDao;
import com.vivareal.spotippos.repository.TerritoryDao;

public class PropertyServiceTest {

	private PropertyService service;
	
	private PropertyDao propertyDao;

	private TerritoryDao territoryDao;

	@Before
	public void before() {
		service = new PropertyServiceImpl();
		propertyDao = mock(PropertyDao.class);
        Whitebox.setInternalState(service, "propertyDao", propertyDao);
		territoryDao = mock(TerritoryDao.class);
        Whitebox.setInternalState(service, "territoryDao", territoryDao);
	}

	@Test
	public void testAddProperty() {
		Property property = createProperty(null);
		Long id = 1L;
		List<String> provinces = Arrays.asList("Goja");
		when(territoryDao.findProvinces(property.getCoordinate())).thenReturn(new HashSet<>(provinces));
		when(propertyDao.add(property)).thenReturn(property.withId(id));
		Property retrievedProperty = service.addProperty(property);
		assertThat(retrievedProperty.getId(), is(id));
		assertThat(retrievedProperty.getProvinces().containsAll(provinces), is(true));
		verify(territoryDao).addProperty(property.getCoordinate(), id);
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testFindInvalidProperty() {
		service.getProperty(1L);
	}

	@Test
	public void testFindValidProperty() {
		Long id = 1L;
		Property property = createProperty(id);
        when(propertyDao.find(id)).thenReturn(property);
		Property retrievedProperty = service.getProperty(id);
		assertThat(retrievedProperty, new ReflectionEquals(property));
	}
	
	@Test
	public void testFindInvalidProperties() {
		Boundaries boundaries = new Boundaries(0, 0, 1, 1);
		List<Property> retrievedProperties = service.getProperties(boundaries);
		assertThat(retrievedProperties, is(empty()));
	}

	@Test
	public void testFindValidProperties() {
		Long id = 1L;
		Property property = createProperty(id);
		when(propertyDao.find(id)).thenReturn(property);
		Boundaries boundaries = new Boundaries(0, 0, 1, 1);
		when(territoryDao.findProperties(boundaries)).thenReturn(new HashSet<>(Arrays.asList(id)));
		List<Property> retrievedProperties = service.getProperties(boundaries);
		assertThat(retrievedProperties, hasSize(1));
		assertThat(retrievedProperties.get(0), new ReflectionEquals(property));
	}

	private Property createProperty(Long id) {
		return new Property()
				.withId(id)
				.withTitle("title " + id)
				.withPrice(1)
				.withDescription("description " +id)
				.withX(1)
				.withY(1)
				.withBeds(1)
				.withBaths(1)
				.withSquareMeters(1);
	}

}