package com.ai.exception;

import com.ai.common.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle validation errors (e.g. @Valid failures)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseBean> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<Object, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ResponseBean resBean = new ResponseBean();
		resBean.setStatusCode("400");
		resBean.setStatusDesc("Validation Failed");
		errors.put("query", "must not be blank");
		resBean.setErrors(errors);

		return new ResponseEntity<>(resBean, HttpStatus.BAD_REQUEST);
	}

	// Handle all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseBean> handleGlobalException(Exception ex) {
		ResponseBean resBean = new ResponseBean();
		resBean.setStatusCode("500");
		resBean.setStatusDesc("Internal Server Error");
		resBean.setError(ex.getMessage());

		return new ResponseEntity<>(resBean, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}