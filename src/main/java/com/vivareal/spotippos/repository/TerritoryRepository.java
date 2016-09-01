package com.vivareal.spotippos.repository;

import java.util.Set;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;

public interface TerritoryRepository  {

	void addProperty(Coordinate coordinate, Long id);

	Set<Long> findProperties(Boundaries boundaries);

	void addProvince(Boundaries boundaries, String name);

	Set<String> findProvinces(Coordinate coordinate);
	
}