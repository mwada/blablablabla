package com.vivareal.spotippos.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Property implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private String title;

	@NotNull
	private Integer price;

	@NotNull
	private String description;

	@Min(0)
	@Max(1400)
	private Integer x;

	@Min(0)
	@Max(1000)
	private Integer y;

	@Min(1)
	@Max(5)
	private Integer beds;

	@Min(1)
	@Max(4)
	private Integer baths;

	private Set<String> provinces;

	@Min(20)
	@Max(240)
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

	public Property withId(Long id) {
		setId(id);
		return this;
	}

	public Property withTitle(String title) {
		setTitle(title);
		return this;
	}

	public Property withPrice(Integer price) {
		setPrice(price);
		return this;
	}

	public Property withDescription(String description) {
		setDescription(description);
		return this;
	}

	public Property withX(Integer x) {
		setX(x);
		return this;
	}

	public Property withY(Integer y) {
		setY(y);
		return this;
	}

	public Property withBeds(Integer beds) {
		setBeds(beds);
		return this;
	}

	public Property withBaths(Integer baths) {
		setBaths(baths);
		return this;
	}

	public Property withProvinces(Set<String> provinces) {
		setProvinces(provinces);
		return this;
	}

	public Property withSquareMeters(Integer squareMeters) {
		setSquareMeters(squareMeters);
		return this;
	}

}