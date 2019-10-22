package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserDTO {

	@Length(min = 8)
	@NotNull(message = "password is mandtory")
	private String password;

	@NotNull(message = "emailId is mandtory")
	@Email
	private String emailId;

}
