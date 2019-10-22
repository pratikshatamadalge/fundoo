package com.bridgelabz.fundoo.model;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
@ResponseStatus
public class Response {

	private int statusCode;
	private String message;
	private Object data;

	public Response(int statusCode, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

}
