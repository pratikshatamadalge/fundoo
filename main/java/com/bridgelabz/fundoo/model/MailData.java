package com.bridgelabz.fundoo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MailData implements Serializable {

	/**
	 * To set mail data
	 */
	private static final long serialVersionUID = 1L;
	private String mailMessage;
	private String emailId;
	private String token;

}
