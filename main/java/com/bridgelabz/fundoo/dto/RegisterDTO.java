package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * Purpose: For the registration Whichever data is required is mentioned in this
 * DTO
 * 
 * @author Pratiksha Tamadalge
 * 
 */
@Data
public class RegisterDTO {
	@NotNull(message = "userName is mandtory")
	private String userName;

	@Length(min = 8)
	@NotNull(message = "password is mandtory")
	private String password;

	@NotNull(message = "emailId is mandtory")
	private String emailId;

	@NotNull(message = "mobileNo is mandtory")
	private long mobileNo;

}
