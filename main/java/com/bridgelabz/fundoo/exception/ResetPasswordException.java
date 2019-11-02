package com.bridgelabz.fundoo.exception;

/**
 * @author Pratiksha Tamadalge
 *
 */
public class ResetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ResetPasswordException(String message) {
		super(message);
	}
}
