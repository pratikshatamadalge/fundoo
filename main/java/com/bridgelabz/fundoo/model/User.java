package com.bridgelabz.fundoo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	@Id
	private long id;

	private String userName;

	private String password;

	private String emailId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", emailId=" + emailId + "]";
	}
}
