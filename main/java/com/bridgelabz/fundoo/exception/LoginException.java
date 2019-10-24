package com.bridgelabz.fundoo.exception;

/**
 * @author Pratiksha Tamadalge
 *
 */
public class LoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public LoginException(String message) {
		super(message);
	}
}
