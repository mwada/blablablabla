package com.vivareal.spotippos.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;

public class BoundariesTest {

	@Test
	public void testGetInvalidCoordinates() {
		Boundaries boundaries = new Boundaries(1, 1, 0, 0);
		List<Coordinate> coordinates = boundaries.getCoordinates();
		assertThat(coordinates, is(empty()));
	}

	@Test
	public void testXCoordinates() {
		Boundaries boundaries = new Boundaries(0, 9, 0, 0);
		List<Coordinate> coordinates = boundaries.getCoordinates();
		assertThat(coordinates, hasSize(10));
		for (int i = 0; i < 10; i++) {
			assertThat(coordinates.get(i).getX(), is(0));
			assertThat(coordinates.get(i).getY(), is(i));
		}
	}

	@Test
	public void testYCoordinates() {
		Boundaries boundaries = new Boundaries(0, 0, 9, 0);
		List<Coordinate> coordinates = boundaries.getCoordinates();
		assertThat(coordinates, hasSize(10));
		for (int i = 0; i < 10; i++) {
			assertThat(coordinates.get(i).getX(), is(i));
			assertThat(coordinates.get(i).getY(), is(0));
		}
	}

	@Test
	public void testXYCoordinates() {
		Boundaries boundaries = new Boundaries(0, 9, 9, 0);
		List<Coordinate> coordinates = boundaries.getCoordinates();
		assertThat(coordinates, hasSize(100));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertThat(coordinates.get(i * 10 + j).getX(), is(i));
				assertThat(coordinates.get(i * 10 + j).getY(), is(j));
			}
		}
	}

}