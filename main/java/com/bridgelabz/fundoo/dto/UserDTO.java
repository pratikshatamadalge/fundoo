package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @author Pratiksha Tamadalge Purpose:For login Whichever data is required is
 *         mentioned in this DTO
 */
@Data
public class UserDTO {

	@NotNull(message = "emailId is mandtory")
	@Email
	private String emailId;
	
	@Length(min = 8)
	@NotNull(message = "password is mandtory")
	private String password;

	
}
