package com.vivareal.spotippos.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;

import com.vivareal.spotippos.exception.EntityNotFoundException;
import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PropertyServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class PropertyServiceTest {

	@Autowired
	private PropertyService service;

	
	@Test(expected=EntityNotFoundException.class)
	public void testFindInvalidProperty() {
		service.getProperty(0L);
	}

	@Test
	public void testFindValidProperty() {
		Property retrievedProperty = service.getProperty(1L);
		assertThat(retrievedProperty, samePropertyValuesAs(createProperty(1L, Arrays.asList("Jaby"))));
	}
	
	@Test
	public void testFindInvalidProperties() {
		List<Property> retrievedProperties = service.getProperties(new Boundaries(0, 0, 1, 1));
		assertThat(retrievedProperties, is(empty()));
	}

	@Test
	public void testFindValidProperties() {
		List<Property> retrievedProperties = service.getProperties(new Boundaries(679, 680, 679, 680));
		assertThat(retrievedProperties, hasSize(1));
	}
	
	@Test
	public void testFindPropertiesWithBoundariesRange() {
		assertRange("x", 0, 1400);
	}
	
	@Test
	public void testAddPropertyWithXRange() {
		assertRange("x", 0, 1400);
	}

	@Test
	public void testAddPropertyWithYRange() {
		assertRange("y", 0, 1000);
	}

	@Test
	public void testAddPropertyWithBedsRange() {
		assertRange("beds", 1, 5);
	}

	@Test
	public void testAddPropertyWithBathsRange() {
		assertRange("baths", 1, 4);
	}

	@Test
	public void testAddPropertyWithSquareMetersRange() {
		assertRange("squareMeters", 20, 240);
	}

	private void assertRange(String attribute, Integer min, Integer max) {
		assertInvalidRange(attribute, min-1, "must be greater than or equal to");
		assertValidRange(attribute, min, max);
		assertInvalidRange(attribute, max+1, "must be less than or equal to");
	}
	
	private void assertInvalidRange(String attribute, Integer range, String message) {
		try {
			Property property = createNewProperty();
			ReflectionTestUtils.setField(property, attribute, range);
			service.addProperty(property);
			fail();
		}catch(ConstraintViolationException e) {
			assertThat(e.getConstraintViolations(), hasSize(1));
			for (ConstraintViolation<?> v : e.getConstraintViolations()) {
				assertThat(v.getMessage(), containsString(message));
			}
		}
	}

	private void assertValidRange(String attribute, Integer min, Integer max) {
		IntStream.range(min, max).forEach(
				i -> {
					Property property = createNewProperty();
					ReflectionTestUtils.setField(property, attribute, i);
					Property retrievedProperty = service.addProperty(property);
					property.withId(retrievedProperty.getId()).withProvinces(retrievedProperty.getProvinces());
					assertThat(retrievedProperty, samePropertyValuesAs(property));
				}
			  );
	}

	private Property createNewProperty() {
		return createProperty(null, null);
	}

	private Property createProperty(Long id, List<String> provinces) {
		Set<String> set = provinces == null? null: new HashSet<>(provinces);
		return new Property()
				.withId(id)
				.withTitle("Imóvel código 1, com 3 quartos e 2 banheiros.")
				.withPrice(643000)
				.withDescription("Laboris quis quis elit commodo eiusmod qui exercitation. In laborum fugiat quis minim occaecat id.")
				.withX(1257)
				.withY(928)
				.withBeds(3)
				.withBaths(2)
				.withProvinces(set)
				.withSquareMeters(61);
	}

}