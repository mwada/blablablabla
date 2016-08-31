package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

public class MemoryLoaderTest {

	private MemoryLoader loader = new MemoryLoader();

	@Before
	public void before() {
		ReflectionTestUtils.setField(loader, "provinceFile", "/provinces.json");
		ReflectionTestUtils.setField(loader, "propertyFile", "/properties.json");
	}
	
	@Test
	public void testLoadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
		List<Province> provinces = loader.loadProvinceFile();
		assertThat(provinces, hasSize(6));
		for (Province province : provinces) {
			assertThat(province.getName(), is(not(nullValue())));
		}
	}

	@Test
	public void testLoadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
		List<Property> properties = loader.loadPropertyFile();
		assertThat(properties, hasSize(8000));
	}

}