package com.bridgelabz.fundoo.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pratiksha Tamadalge
 *
 */
@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String userName;

	private String password;

	private String emailId;

	private Long mobileNo;

	private Date registeredDate;

	private Date updatedDate;

	private Boolean isActive;

	private String profilePic;
}
