package com.bridgelabz.fundoo.note.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoteRestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<NoteError> mapException(Exception exception) {
		NoteError error = new NoteError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), new Date());
		return new ResponseEntity<NoteError>(error, HttpStatus.BAD_REQUEST);
	}
}