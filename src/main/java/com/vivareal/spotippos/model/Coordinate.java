package com.vivareal.spotippos.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Coordinate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(0)
    @Max(1400)
    private Integer x;

    @Min(0)
    @Max(1000)
    private Integer y;

    public Coordinate() {
    }

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(x)
                .append(y)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate other = (Coordinate) obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(getX(), other.getX());
            builder.append(getY(), other.getY());
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public String toString() {
        StandardToStringStyle stringStyle = new StandardToStringStyle();
        stringStyle.setUseShortClassName(true);
        return ToStringBuilder.reflectionToString(this, stringStyle);
    }

}