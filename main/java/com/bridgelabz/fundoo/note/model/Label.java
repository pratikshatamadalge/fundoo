package com.bridgelabz.fundoo.note.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Pratiksha Tamadalge
 *
 */
@Data
@Document(collection = "label")
public class Label {
	@Id
	private String labelId;
	private String labelName;
	private String emailId;
	@DBRef(lazy = true)
	private List<Note> note = new ArrayList<Note>();
}
