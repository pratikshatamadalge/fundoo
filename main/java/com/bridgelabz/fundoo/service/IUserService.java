package com.bridgelabz.fundoo.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.User;

public interface IUserService {

	String register(RegisterDTO regDTO);

	String Login(UserDTO userDTO) throws LoginException;

	List<User> getUsers();

	void deleteUser(String emailId);

	void UpdateUser(String oldEmail, String newEmail);

	public void sendEmail(String emailId);

	void resetPassword(String token, String newPassword);

	String validateUser(String token);

}
