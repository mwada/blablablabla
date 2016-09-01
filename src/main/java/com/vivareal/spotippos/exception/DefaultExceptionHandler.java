package com.vivareal.spotippos.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequestMapping(produces = "application/json")
public class DefaultExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.error("Exception[handleConstraintViolationException]", ex);
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            String field = constraintViolation.getPropertyPath().toString();
            errors.add(
                    "Field[" + field.substring(field.lastIndexOf(".") + 1) + "] " + constraintViolation.getMessage());
        }
        return new ExceptionMessage(ex.getClass().getSimpleName(), errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        LOGGER.error("Exception[handleMethodArgumentTypeMismatchException]", ex);
        return new ExceptionMessage(ex);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionMessage handleEntityNotFoundException(EntityNotFoundException ex) {
        LOGGER.error("Exception[handleEntityNotFoundException]", ex);
        return new ExceptionMessage(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleOtherException(Exception ex) {
        LOGGER.error("Exception[handleOtherException]", ex);
        return new ExceptionMessage(ex);
    }

}