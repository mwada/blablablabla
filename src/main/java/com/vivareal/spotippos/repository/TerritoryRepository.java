package com.vivareal.spotippos.repository;

import java.util.Set;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;

public interface TerritoryRepository  {

	void addProperty(Coordinate coordinate, Long id);

	Set<Long> findProperties(Boundaries boundaries);

	Set<String> findProvinces(Coordinate coordinate);
	
}