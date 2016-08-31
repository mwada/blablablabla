package com.vivareal.spotippos.repository;

import com.vivareal.spotippos.model.Property;

public interface PropertyRepository  {

	Property add(Property property);

	Property find(Long id);

}