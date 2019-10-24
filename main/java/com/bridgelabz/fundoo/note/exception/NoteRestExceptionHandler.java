package com.bridgelabz.fundoo.note.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Purpose:To throw Note Exceptions
 * @author Pratiksha Tamadalge
 *
 */
@ControllerAdvice
public class NoteRestExceptionHandler {
	/**
	 * @param exception
	 * @return Note Error and http status
	 */
	@ExceptionHandler
	public ResponseEntity<NoteError> mapException(Exception exception) {
		NoteError error = new NoteError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), new Date());
		return new ResponseEntity<NoteError>(error, HttpStatus.BAD_REQUEST);
	}
}