package com.employeemanagement.employee;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(EmployeeNotFoundException ex) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleEmailConflict(EmailAlreadyExistsException ex) {
		return build(HttpStatus.CONFLICT, ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(
						org.springframework.validation.FieldError::getField,
						org.springframework.validation.FieldError::getDefaultMessage,
						(first, second) -> first,
						LinkedHashMap::new));
		return build(HttpStatus.BAD_REQUEST, "Validation failed", errors);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
		return build(HttpStatus.BAD_REQUEST, "Malformed request body", null);
	}

	private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message,
			Map<String, String> errors) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now().toString());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		if (errors != null) {
			body.put("errors", errors);
		}
		return ResponseEntity.status(status).body(body);
	}
}
