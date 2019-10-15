package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.User;

public interface IUserService {

	String register(UserDTO userDTO);

	String Login(UserDTO userDTO);

	List<User> getUsers();

	void deleteUser(UserDTO userDTO);

	void UpdateUser(String oldEmail, String newEmail);

	public void sendEmail(String emailId);

	void resetPassword(String token, String newPassword);

}
