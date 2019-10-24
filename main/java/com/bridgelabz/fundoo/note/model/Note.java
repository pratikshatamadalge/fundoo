package com.bridgelabz.fundoo.note.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Note {

	@Id
	private String id;
	private String title;
	private String description;
	private Date createdTime;
	private Date editedTime;
	private Boolean isPinned;
	private Boolean isTrashed;
	private Boolean isArcheived;
	private String emailId;
	private List<String> collab = new ArrayList<String>();
	@DBRef(lazy = true)
	private List<Label> labels = new ArrayList<Label>();

}
