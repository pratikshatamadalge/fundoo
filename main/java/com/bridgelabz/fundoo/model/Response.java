package com.bridgelabz.fundoo.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Pratiksha Tamadalge
 *
 */
@Data
@AllArgsConstructor
@ResponseStatus
public class Response {

	private HttpStatus statusCode;
	private String message;
	private Object data;
}
