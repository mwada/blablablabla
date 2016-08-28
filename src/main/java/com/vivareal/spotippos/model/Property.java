package com.vivareal.spotippos.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Property implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private Integer price;
	private String description;
	private Integer x;
	private Integer y;
	private Integer beds;
	private Integer baths;
	private Set<String> provinces;
	private Integer squareMeters;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonGetter("lat")
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	@JsonGetter("long")
	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getBeds() {
		return beds;
	}

	public void setBeds(Integer beds) {
		this.beds = beds;
	}

	public Integer getBaths() {
		return baths;
	}

	public void setBaths(Integer baths) {
		this.baths = baths;
	}

	public Set<String> getProvinces() {
		return provinces;
	}

	public void setProvinces(Set<String> provinces) {
		this.provinces = provinces;
	}

	public Integer getSquareMeters() {
		return squareMeters;
	}

	public void setSquareMeters(Integer squareMeters) {
		this.squareMeters = squareMeters;
	}
	
	public Coordinate getCoordinate() {
		return new Coordinate(this.getX(), this.getY());
	}

}