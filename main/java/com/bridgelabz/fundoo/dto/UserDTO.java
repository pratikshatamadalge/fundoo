package com.bridgelabz.fundoo.dto;

public class UserDTO {

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

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", password=" + password + ", emailId=" + emailId + "]";
	}

}
