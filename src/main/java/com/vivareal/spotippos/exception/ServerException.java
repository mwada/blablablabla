package com.vivareal.spotippos.exception;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(String message, Throwable e) {
		super(message, e);
	}

	public ServerException(Throwable e) {
		super(e);
	}

}