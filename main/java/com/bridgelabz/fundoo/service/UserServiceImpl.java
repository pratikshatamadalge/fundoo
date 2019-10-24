package com.bridgelabz.fundoo.service;

import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;

import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

/**
 * Purpose:User Service implementation to provide service to the user controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Service
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private Environment environment;

	/**
	 * Pupose:Register the user
	 * 
	 * @param regDTO
	 * @return Environment variable as a string
	 */
	@Override
	public Response register(RegisterDTO regDTO) {
		System.out.println("in service User DTO : " + regDTO);

		User user = new ModelMapper().map(regDTO, User.class);
		if (userRepository.findByEmailId(user.getEmailId()) != null) {
			throw new RegistrationException("email is already registered");
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		String token = tokenUtil.createToken(regDTO.getEmailId());
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pratikshatamadalge@gmail.com");
		message.setTo(regDTO.getEmailId());
		message.setSubject("varify user");
		message.setText("Varification token: " + token);
		javaMailSender.send(message);
		userRepository.save(user);
		return new Response(200, environment.getProperty("registerSuccess"), null);
	}

	/**
	 * Purpose:Login the user
	 * 
	 * @param userDTO
	 * @return Environment variable as a string
	 * @throws LoginException
	 */
	@Override
	public Response Login(UserDTO userDTO) throws LoginException {

		System.out.println("In userDTO service :" + userDTO);
		User user = userRepository.findByEmailId(userDTO.getEmailId());
		if (user == null) {
			throw new LoginException("this emailId is not registered yet!!");
		}
		if (new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
			return new Response(200, environment.getProperty("loginSuccess"), null);
		}
		return new Response(400, environment.getProperty("invalid"), null);
	}

	/**
	 * Purpose:to fetch all the users
	 * 
	 * @return user list
	 */
	@Override
	public List<User> getUsers() {
		List<User> user = userRepository.findAll();
		return user;
	}

	/**
	 * Purpose:To delete a particular user
	 * 
	 * @param emailId
	 */
	public Response deleteUser(String emailId) {
		userRepository.deleteByEmailId(emailId);
		return new Response(200, environment.getProperty("delete"), null);
	}

	/**
	 * Purpose:To update a particular data of user
	 * 
	 * @param oldEmail
	 * @param newEmail
	 */
	@Override
	public Response UpdateUser(String oldEmailId, String newEmailId) {
		User user = userRepository.findByEmailId(oldEmailId);
		if (user == null) {
			throw new RegistrationException("email is already is registered");
		}
		user.setEmailId(newEmailId);
		user.setUpdatedDate(new Date());
		userRepository.save(user);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:To send a verification token to particular user
	 * 
	 * @param emailId
	 */
	public Response sendEmail(String emailId) {
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

		return new Response(200, environment.getProperty("sent"), null);
	}

	/**
	 * Purpose:To reset the password of particular user
	 * 
	 * @param token
	 * @param newPassword
	 */
	@Override
	public Response resetPassword(String token, String newPassword) {

		System.out.println("token is: " + token);
		String emailId = tokenUtil.decodeToken(token);
		System.out.println("token is: " + emailId);

		User user = userRepository.findByEmailId(emailId);
		if (user == null) {
			throw new RegistrationException("This emailId is not registerd");
		}
		System.out.println("user data :" + user);

		user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		userRepository.save(user);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:To validate the registered email id
	 * 
	 * @param token
	 * @return String
	 */
	@Override
	public Response validateUser(String token) {
		String emailId = tokenUtil.decodeToken(token);
		Boolean flag = userRepository.findAll().stream().anyMatch(i -> i.getEmailId().equals(emailId));
		if (flag == true) {
			User user = userRepository.findByEmailId(emailId);
			user.setIsActive(true);
			user.setRegisteredDate(new Date());
			userRepository.save(user);
			return new Response(200, environment.getProperty("validate"), null);
		}
		throw new RegistrationException("register again");
	}
}
