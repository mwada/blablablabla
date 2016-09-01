package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;
import com.vivareal.spotippos.model.Position;

@RunWith(MockitoJUnitRunner.class)
public class MemoryTerritoryRepositoryTest {

	@InjectMocks
	private MemoryTerritoryRepository repository = new MemoryTerritoryRepository();

	@Spy
	private Map<Coordinate, Position> territoryDb = new HashMap<>();

	@Test
	public void testFindInvalidProvince() {
		Coordinate coordinate = new Coordinate(0, 0);
		Set<String> names = repository.findProvinces(coordinate);
		assertThat(names, is(empty()));
	}

	@Test
	public void testFindValidProvince() {
		Coordinate coordinate = new Coordinate(0, 0);
		Position position = createPosition(coordinate);
		territoryDb.put(coordinate, position);
		Set<String> names = repository.findProvinces(coordinate);
		assertThat(names, is(position.getProvinceNames()));
	}

	@Test
	public void testAddPropertyInNewPosition() {
		Long id = 1L;
		Coordinate coordinate = new Coordinate(0, 0);
		repository.addProperty(coordinate, id);
		Position position = territoryDb.get(coordinate);
		assertThat(position, is(not(nullValue())));
		Set<Long> ids = position.getPropertyIds();
		assertThat(ids, hasSize(1));
		assertThat(ids, contains(id));
	}

	@Test
	public void testAddPropertyInExistingPosition() {
		Coordinate coordinate = new Coordinate(0, 0);
		Position position = createPosition(coordinate);
		territoryDb.put(coordinate, position);
		Long id = 3L;
		repository.addProperty(coordinate, id);
		Position retrievedPosition = territoryDb.get(coordinate);
		assertThat(retrievedPosition, is(not(nullValue())));
		Set<Long> ids = position.getPropertyIds();
		assertThat(ids, hasSize(3));
	}

	@Test
	public void testFindEmptyBoundaries() {
		Boundaries boundaries = new Boundaries(0, 0, 5, 5);
		Set<Long> ids = repository.findProperties(boundaries);
		assertThat(ids, is(empty()));
	}

	@Test
	public void testFindInvalidBoundaries() {
		prepareRepository();
		Boundaries boundaries = new Boundaries(5, 5, 0, 0);
		Set<Long> ids = repository.findProperties(boundaries);
		assertThat(ids, is(empty()));
	}

	@Test
	public void testFindValidBoundaries() {
		prepareRepository();
		Boundaries boundaries = new Boundaries(0, 5, 5, 0);
		Set<Long> ids = repository.findProperties(boundaries);
		assertThat(ids, hasSize(4));
	}

	@Test
	public void testAddProvince() {
		String province = "province";
		Boundaries boundaries = new Boundaries(0, 5, 5, 0);
		repository.addProvince(boundaries, province);
		for (int i = 0; i <= 5; i++) {
			for (int j = 0; j <= 5; j++) {
				Position position = territoryDb.get(new Coordinate(i, j));
				assertThat(position.getProvinceNames(), contains(province));
			}
		}
	}

	private void prepareRepository() {
		territoryDb.put(new Coordinate(0, 0),
				createPosition(new Coordinate(0, 0), new HashSet<>(Arrays.asList(1L, 2L))));
		territoryDb.put(new Coordinate(1, 1), createPosition(new Coordinate(1, 1), new HashSet<>(Arrays.asList(3L))));
		territoryDb.put(new Coordinate(5, 5), createPosition(new Coordinate(5, 5), new HashSet<>(Arrays.asList(4L))));
		territoryDb.put(new Coordinate(6, 6), createPosition(new Coordinate(6, 6), new HashSet<>(Arrays.asList(5L))));
	}

	private Position createPosition(Coordinate coordinate) {
		return createPosition(coordinate, new HashSet<>(Arrays.asList(1L, 2L)),
				new HashSet<>(Arrays.asList("Gode", "Ruja")));
	}

	private Position createPosition(Coordinate coordinate, Set<Long> ids) {
		return createPosition(coordinate, ids, new HashSet<>(Arrays.asList("Gode", "Ruja")));
	}

	private Position createPosition(Coordinate coordinate, Set<Long> ids, Set<String> names) {
		return new Position().withCoordinate(coordinate).withProvinceNames(names).withPropertyIds(ids);
	}

}