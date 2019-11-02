package com.bridgelabz.fundoo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Pratisksha Tamadalge Purpose:To throw which exception error occured
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

	private int statusCode;
	private String message;
	private Object data;
}
