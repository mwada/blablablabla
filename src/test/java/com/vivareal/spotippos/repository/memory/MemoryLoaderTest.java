package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

public class MemoryLoaderTest {

	private MemoryLoader loader;

	@Before
	public void before() {
		loader = new MemoryLoader();
	}

	@Test
	public void testLoadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
		Map<String, Province> provinces = loader.loadProvinceFile();
		assertThat(provinces.keySet(), hasSize(6));
	}

	@Test
	public void testLoadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
		List<Property> properties = loader.loadPropertyFile();
		assertThat(properties, hasSize(8000));
	}

}