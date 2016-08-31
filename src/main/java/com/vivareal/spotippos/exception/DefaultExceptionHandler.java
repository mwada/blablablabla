package com.vivareal.spotippos.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping(produces = "application/json")
public class DefaultExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage handleConstraintViolationException(ConstraintViolationException ex) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			String field = constraintViolation.getPropertyPath().toString();
			errors.add("Field[" + field.substring(field.lastIndexOf(".")) + "] "+ constraintViolation.getMessage());
		}
		return new ErrorMessage(ex.getClass().getSimpleName(), errors);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage handleEntityNotFoundException(EntityNotFoundException ex) {
		return new ErrorMessage(ex);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleOtherException(Exception ex) {
		return new ErrorMessage(ex);
	}
	
	class ErrorMessage {
		private String exception;
		private List<String> errors;
		
		public ErrorMessage(String exception, List<String> errors) {
			this.exception = exception;
			this.errors = errors;
		}
		public ErrorMessage(Exception e) {
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
	
}