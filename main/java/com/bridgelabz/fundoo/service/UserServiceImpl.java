package com.bridgelabz.fundoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.UserDTO;

import com.bridgelabz.fundoo.model.User;

import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TokenUtil tokenUtil;

	@Override
	public String register(UserDTO userDTO) {
		System.out.println("in service User DTO : " + userDTO);
		User user = new User();
		 if (userDTO.getUserName() == null || userDTO.getEmailId() == null ||userDTO.getPassword()==null) {
			return "All fields are mandtory to enter";
		} else if (userDTO.getPassword().length() < 8) {
			return "password must consist of 8 characters";
		}else {
			user.setUserName(userDTO.getUserName());
			user.setEmailId(userDTO.getEmailId());
			user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

			userRepository.save(user);
			return "Registered successfully";
		}
	}

	@Override
	public String Login(UserDTO userDTO) {
		System.out.println("In userDTO service :" + userDTO);
		if (userDTO.getEmailId() == null || userDTO.getPassword() == null) {
			return "please enter emailId and password";
		} else {
			User user = userRepository.findByEmailId(userDTO.getEmailId());

			if (new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
				return "Login successful";
			} else
				return "Invalid credientials";
		}
	}

	@Override
	public List<User> getUsers() {
		List<User> user = userRepository.findAll();

		return user;
	}

	public void deleteUser(UserDTO userDTO) {
		userRepository.deleteByEmailId(userDTO.getEmailId());
	}

	@Override
	public void UpdateUser(String oldEmailId, String newEmailId) {
		User user = userRepository.findByEmailId(oldEmailId);
		user.setEmailId(newEmailId);
		userRepository.save(user);
	}

	public User forgotAndResetPassword(String emailId) {
		User user = userRepository.findByEmailId(emailId);
		return user;
	}

	public void sendEmail(String emailId) {
		System.out.println("in userserviceImpl :" + emailId);

		User user = userRepository.findByEmailId(emailId);

		System.out.println("User info of given mail: " + user);
		SimpleMailMessage message = new SimpleMailMessage();

		String token = tokenUtil.createToken(emailId);

		message.setFrom("pratikshatamadalge@gmail.com");
		message.setTo(emailId);
		message.setSubject("Reset password Varification");
		message.setText("Varification token: " + token);

		javaMailSender.send(message);
	}

	@Override
	public void resetPassword(String token, String newPassword) {

		System.out.println("token is: " + token);
		String emailId = tokenUtil.decodeToken(token);
		System.out.println("token is: " + emailId);

		User user = userRepository.findByEmailId(emailId);

		System.out.println("user data :" + user);

		user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		userRepository.save(user);

	}
}
