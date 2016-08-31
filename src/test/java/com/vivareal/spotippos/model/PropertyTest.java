package com.vivareal.spotippos.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertyTest {

    private JacksonTester<Property> json;

	@Before
	public void before() {
		 JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void testSerializeProperty() throws IOException {
		Property property = new Property()
			.withId(665L)
			.withTitle("Imóvel código 665, com 1 quarto e 1 banheiro")
			.withPrice(540000)
			.withDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
			.withX(667)
			.withY(556)
			.withBeds(1)
			.withBaths(1)
			.withSquareMeters(42)
			.withProvinces(new HashSet<>(Arrays.asList("Ruja")));
		assertThat(this.json.write(property)).isEqualToJson("property.json");
	}

	
	@Test
	public void testDeserializeProperty() throws IOException {
		Property property = new Property()
				.withId(665L)
				.withTitle("Imóvel código 665, com 1 quarto e 1 banheiro")
				.withPrice(540000)
				.withDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
				.withX(667)
				.withY(556)
				.withBeds(1)
				.withBaths(1)
				.withSquareMeters(42)
				.withProvinces(new HashSet<>(Arrays.asList("Ruja")));
		assertThat(json.read("property.json").getObject(), new ReflectionEquals(property));
	}

}