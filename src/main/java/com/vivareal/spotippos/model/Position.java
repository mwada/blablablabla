package com.vivareal.spotippos.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Position implements Serializable {

	private static final long serialVersionUID = 1L;

	private Coordinate coordinate;
	private Set<String> provinceNames;
	private Set<Long> propertyIds;

	public Position() {
		provinceNames = new HashSet<>();
		propertyIds = new HashSet<>();
	}

	public Position(Coordinate coordinate) {
		this();
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Set<String> getProvinceNames() {
		return provinceNames;
	}

	public void setProvinceNames(Set<String> provinceNames) {
		this.provinceNames = provinceNames;
	}

	public Set<Long> getPropertyIds() {
		return propertyIds;
	}

	public void setPropertyIds(Set<Long> propertyIds) {
		this.propertyIds = propertyIds;
	}

	public void addProvince(String name) {
		provinceNames.add(name);
	}

	public void addProperty(Long id) {
		propertyIds.add(id);
	}

	public Position withCoordinate(Coordinate coordinate) {
		setCoordinate(coordinate);
		return this;
	}

	public Position withProvinceNames(Set<String> provinceNames) {
		setProvinceNames(provinceNames);
		return this;
	}

	public Position withPropertyIds(Set<Long> propertyIds) {
		setPropertyIds(propertyIds);
		return this;
	}

}