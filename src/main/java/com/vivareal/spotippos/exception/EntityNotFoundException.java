package com.vivareal.spotippos.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Long id) {
		super("Could not find property[" + id + "]");
	}
	
}