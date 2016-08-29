package com.vivareal.spotippos.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.vivareal.spotippos.model.Coordinate;

public class CoordinateTest {

	@Test
	public void testEqualsCoordinate() {
		Coordinate a = new Coordinate(0, 0);
		Coordinate b = new Coordinate(0, 0);
		assertThat(a.equals(b), is(true));
	}

	@Test
	public void testNotEqualsCoordinate() {
		Coordinate a = new Coordinate(0, 0);
		Coordinate b = new Coordinate(0, 1);
		assertThat(a.equals(b), is(false));
	}

}