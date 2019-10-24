package com.bridgelabz.fundoo.note.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pratiksha Tamadalge
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteError {
	private int statusCode;
	private String statusMessage;
	private Date date;
}