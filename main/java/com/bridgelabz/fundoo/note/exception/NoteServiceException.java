package com.bridgelabz.fundoo.note.exception;

/**
 * @author Pratiksha Tamadalge
 *
 */
public class NoteServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 */
	public NoteServiceException(String message) {
		super(message);
	}

}
