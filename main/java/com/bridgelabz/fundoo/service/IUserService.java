package com.bridgelabz.fundoo.service;

import java.io.IOException;
import java.util.List;
import javax.security.auth.login.LoginException;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;

/**
 * Purpose:User service interface for user service implementation
 * 
 * @author Pratiksha Tamadalge
 *
 */
public interface IUserService {

	Response register(RegisterDTO regDTO);

	Response login(UserDTO userDTO) throws LoginException;

	List<User> getUsers();

	Response deleteUser(String emailId);

	Response updateUser(String oldEmail, String newEmail);

	Response sendEmail(String emailId);

	Response resetPassword(String token, String newPassword);

	Response validateUser(String token);

	Response saveProfilePic(MultipartFile file, String emailId) throws IOException, LoginException;

	Response updateProfilePic(MultipartFile file, String emailId) throws IOException, LoginException;

	Response deleteProfilePic(String emailId) throws LoginException;
}
