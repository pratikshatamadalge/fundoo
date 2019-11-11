package com.bridgelabz.fundoo.note.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.note.util.ENUM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private List<String> collab = new ArrayList<>();
	private LocalDateTime remainder;
	private ENUM repeat;
	@DBRef(lazy = true)
	private List<Label> labels = new ArrayList<>();
}
