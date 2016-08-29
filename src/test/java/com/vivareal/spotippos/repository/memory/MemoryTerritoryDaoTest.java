package com.vivareal.spotippos.repository.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;
import com.vivareal.spotippos.model.Position;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

public class MemoryTerritoryDaoTest {

	private MemoryTerritoryDao dao;

	private Map<Coordinate, Position> territoryDb;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void before() {
		dao = new MemoryTerritoryDao();
		territoryDb = (Map) Whitebox.getInternalState(dao, "territoryDb");
	}

	@Test
	public void testFindInvalidProvince() {
		Coordinate coordinate = new Coordinate(0,0);
		Set<String> names = dao.findProvinces(coordinate);
		assertThat(names, is(empty()));
	}
	
	@Test
	public void testFindValidProvince() {
		Coordinate coordinate = new Coordinate(0,0);
		Position position = createPosition(coordinate);
		territoryDb.put(coordinate, position);
		Set<String> names = dao.findProvinces(coordinate);
		assertThat(names.containsAll(position.getProvinceNames()), is(true));
	}
	
	@Test
	public void testAddPropertyInNewPosition() {
		Long id = 1L;
		Coordinate coordinate = new Coordinate(0,0);
		dao.addProperty(coordinate, id);
		Position position = territoryDb.get(coordinate);
		assertThat(position, is(not(nullValue())));
		Set<Long> ids = position.getPropertyIds();
		assertThat(ids, hasSize(1));
		assertThat(ids.contains(id), is(true));
	}

	@Test
	public void testAddPropertyInExistingPosition() {
		Coordinate coordinate = new Coordinate(0,0);
		Position position = createPosition(coordinate);
		territoryDb.put(coordinate, position);
		Long id = 3L;
		dao.addProperty(coordinate, id);
		Position retrievedPosition = territoryDb.get(coordinate);
		assertThat(retrievedPosition, is(not(nullValue())));
		Set<Long> ids = position.getPropertyIds();
		assertThat(ids, hasSize(3));
		assertThat(ids.containsAll(position.getPropertyIds()), is(true));
		assertThat(ids.contains(id), is(true));
	}
	

	@Test
	public void testFindEmptyBoundaries() {
		Boundaries boundaries = new Boundaries(0,0,5,5);
		Set<Long> ids = dao.findProperties(boundaries);
		assertThat(ids, is(empty()));
	}

	@Test
	public void testFindInvalidBoundaries() {
		territoryDb.put(new Coordinate(0,0), createPosition(new Coordinate(0,0), new HashSet<>(Arrays.asList(1L, 2L))));
		territoryDb.put(new Coordinate(1,1), createPosition(new Coordinate(1,1), new HashSet<>(Arrays.asList(2L))));
		territoryDb.put(new Coordinate(5,5), createPosition(new Coordinate(5,5), new HashSet<>(Arrays.asList(3L))));
		territoryDb.put(new Coordinate(6,6), createPosition(new Coordinate(6,6), new HashSet<>(Arrays.asList(4L))));
		Boundaries boundaries = new Boundaries(5,5,0,0);
		Set<Long> ids = dao.findProperties(boundaries);
		assertThat(ids, is(empty()));
	}

	@Test
	public void testFindValidBoundaries() {
		territoryDb.put(new Coordinate(0,0), createPosition(new Coordinate(0,0), new HashSet<>(Arrays.asList(1L, 2L))));
		territoryDb.put(new Coordinate(1,1), createPosition(new Coordinate(1,1), new HashSet<>(Arrays.asList(3L))));
		territoryDb.put(new Coordinate(5,5), createPosition(new Coordinate(5,5), new HashSet<>(Arrays.asList(4L))));
		territoryDb.put(new Coordinate(6,6), createPosition(new Coordinate(6,6), new HashSet<>(Arrays.asList(5L))));
		Boundaries boundaries = new Boundaries(0,5,5,0);
		Set<Long> ids = dao.findProperties(boundaries);
		assertThat(ids, hasSize(4));
		assertThat(ids.containsAll(Arrays.asList(1L, 2L, 3L, 4L)), is(true));
	}

	@Test
	public void testLoadTerritory() {
		List<Province> provinces = Arrays.asList(
				createProvince("Gode", 0, 3, 3, 0),
				createProvince("Ruja", 1, 5, 5, 1)
				);
		List<Property> properties = Arrays.asList(
				createProperty(1L).withX(0).withY(0), 
				createProperty(2L).withX(0).withY(0),
				createProperty(3L).withX(2).withY(2)
				);
		properties = dao.loadTerritory(provinces, properties);
		assertThat(properties.get(0).getProvinces().containsAll(Arrays.asList("Gode")), is(true));
		assertThat(properties.get(1).getProvinces().containsAll(Arrays.asList("Gode")), is(true));
		assertThat(properties.get(2).getProvinces().containsAll(Arrays.asList("Gode", "Ruja")), is(true));
		
		Coordinate coordinate = new Coordinate(0,0);
		assertThat(territoryDb.get(coordinate).getProvinceNames(), hasSize(1));
		assertThat(territoryDb.get(coordinate).getProvinceNames().containsAll(Arrays.asList("Gode")), is(true));
		assertThat(territoryDb.get(coordinate).getPropertyIds(), hasSize(2));
		assertThat(territoryDb.get(coordinate).getPropertyIds().containsAll(Arrays.asList(1L, 2L)), is(true));
		coordinate = new Coordinate(2,2);
		assertThat(territoryDb.get(coordinate).getProvinceNames(), hasSize(2));
		assertThat(territoryDb.get(coordinate).getProvinceNames().containsAll(Arrays.asList("Gode", "Ruja")), is(true));
		assertThat(territoryDb.get(coordinate).getPropertyIds(), hasSize(1));
		assertThat(territoryDb.get(coordinate).getPropertyIds().containsAll(Arrays.asList(3L)), is(true));
		coordinate = new Coordinate(4,4);
		assertThat(territoryDb.get(coordinate).getProvinceNames(), hasSize(1));
		assertThat(territoryDb.get(coordinate).getProvinceNames().containsAll(Arrays.asList("Ruja")), is(true));
		assertThat(territoryDb.get(coordinate).getPropertyIds(), hasSize(0));
		coordinate = new Coordinate(6,6);
		assertThat(territoryDb.get(coordinate), is(nullValue()));
	}
	
	private Position createPosition(Coordinate coordinate) {
		return createPosition(coordinate, 
				new HashSet<>(Arrays.asList(1L, 2L)), 
				new HashSet<>(Arrays.asList("Gode", "Ruja")));
	}

	private Position createPosition(Coordinate coordinate, Set<Long> ids) {
		return createPosition(coordinate, ids,
				new HashSet<>(Arrays.asList("Gode", "Ruja")));
	}


	private Position createPosition(Coordinate coordinate, Set<Long> ids, Set<String> names) {
		return new Position()
				.withCoordinate(coordinate)
				.withProvinceNames(names)
				.withPropertyIds(ids);
	}
	
	private Province createProvince(String name, Integer ax, Integer ay, Integer bx, Integer by) {
		return new Province().withName(name).withBoundaries(new Boundaries(ax, ay, bx, by));
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