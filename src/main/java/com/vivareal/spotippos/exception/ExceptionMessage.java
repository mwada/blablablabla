package com.vivareal.spotippos.exception;

import java.util.Arrays;
import java.util.List;

public class ExceptionMessage {
	
	private String exception;
	private List<String> errors;

	public ExceptionMessage() {
	}

	public ExceptionMessage(String exception, List<String> errors) {
		this.exception = exception;
		this.errors = errors;
	}

	public ExceptionMessage(Exception e) {
		this.exception = e.getClass().getSimpleName();
		this.errors = Arrays.asList(e.getMessage());
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}