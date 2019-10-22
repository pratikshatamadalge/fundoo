package com.bridgelabz.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorResponse> loginException(Exception ex) {

		return new ResponseEntity<>(new ErrorResponse(400, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<ErrorResponse> registrationException(Exception ex) {

		return new ResponseEntity<>(new ErrorResponse(400, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler()
	public ResponseEntity<ErrorResponse> resetPasswordException(Exception ex) {

		return new ResponseEntity<>(new ErrorResponse(400, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
