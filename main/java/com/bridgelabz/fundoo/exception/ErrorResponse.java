package com.bridgelabz.fundoo.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private int statusCode;
	private String message;
	private Object data;

	public ErrorResponse(int statusCode, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

}
