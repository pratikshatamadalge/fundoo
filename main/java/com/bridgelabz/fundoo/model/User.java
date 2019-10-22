package com.bridgelabz.fundoo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "user")

@Data
public class User {

	@Id
	private String id;

	private String userName;

	private String password;

	private String emailId;

	private Long mobileNo;

	private Boolean isActive;
}
