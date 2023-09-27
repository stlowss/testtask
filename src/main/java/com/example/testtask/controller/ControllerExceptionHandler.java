package com.example.testtask.controller;

import com.example.testtask.exception.AgeNotValidException;
import com.example.testtask.exception.ErrorResponse;
import com.example.testtask.exception.InvalidDateRangeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice(basePackages = {"com.example.testtask.controller"})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        logger.error("Entity not found", ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(AgeNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleAgeNotValid(AgeNotValidException ex, HttpServletRequest request) {
        logger.error("Age is not valid", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidRangeException(InvalidDateRangeException ex,
                                                                        HttpServletRequest request) {
        logger.error("Age is not valid", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleGeneralException(RuntimeException ex, HttpServletRequest request) {
        String message = "Internal server error";
        logger.error(message, ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        logger.error("Not valid data", ex);
        Map<String, String> errors = processValidationFields(ex);

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ErrorResponse errorResponse = new ErrorResponse(status.value(), httpStatus.name(), errors.toString(),
                servletRequest.getServletPath());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), message, request.getRequestURI());
        return new ResponseEntity<>(errorResponse, status);
    }

    private Map<String, String> processValidationFields(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
