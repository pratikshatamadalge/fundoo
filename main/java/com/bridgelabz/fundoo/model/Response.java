package com.bridgelabz.fundoo.model;

import java.io.Serializable;

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
public class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus statusCode;
	private String message;
	private Object data;
}
