package com.bridgelabz.fundoo.model;

import java.util.Date;

import javax.mail.Multipart;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Pratiksha Tamadalge
 *
 */
@Document(collection = "user")
@Data
@AllArgsConstructor
public class User {

	@Id
	private String id;

	private String userName;

	private String password;

	private String emailId;

	private Long mobileNo;

	private Date registeredDate;

	private Date updatedDate;

	private Boolean isActive;
	
	private Binary image;
}
