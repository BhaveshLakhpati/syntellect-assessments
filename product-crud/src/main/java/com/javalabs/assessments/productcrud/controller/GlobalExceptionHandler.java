package com.javalabs.assessments.productcrud.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleException(final MethodArgumentNotValidException exception) {
		String message = exception.getFieldErrors().stream()
				.map(field -> field.getField() + "-" + field.getDefaultMessage()).collect(Collectors.joining(", "));

		return ResponseEntity.badRequest().body(message);
	}
}
