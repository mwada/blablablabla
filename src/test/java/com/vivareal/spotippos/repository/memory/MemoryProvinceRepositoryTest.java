package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

@RunWith(MockitoJUnitRunner.class)
public class MemoryProvinceRepositoryTest {

	@InjectMocks
	private MemoryProvinceRepository repository = new MemoryProvinceRepository();

	@Spy
	private Map<Long, Property> provinceDb = new HashMap<>();

	@Test
	public void testAddProvince() {
		Province province = new Province()
				.withName("name")
				.withBoundaries(new Boundaries(0,0,0,0));
		Province retrievedProvince = repository.add(province);
		assertThat(retrievedProvince, samePropertyValuesAs(province));
	}

}