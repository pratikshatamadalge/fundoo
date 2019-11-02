package com.bridgelabz.fundoo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.Multipart;
import javax.security.auth.login.LoginException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;

import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.utility.StaticReference;
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
		return new Response(HttpStatus.OK, environment.getProperty("registerSuccess"), null);
	}

	/**
	 * Purpose:Login the user
	 * 
	 * @param userDTO
	 * @return Environment variable as a string
	 * @throws LoginException
	 */
	@Override
	public Response login(UserDTO userDTO) throws LoginException {

		User user = userRepository.findByEmailId(userDTO.getEmailId());
		if (user == null) {
			throw new LoginException("this emailId is not registered yet!!");
		}
		if (new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
			return new Response(HttpStatus.OK, environment.getProperty("loginSuccess"), null);
		}
		return new Response(HttpStatus.BAD_REQUEST, environment.getProperty("invalid"), null);
	}

	/**
	 * Purpose:to fetch all the users
	 * 
	 * @return user list
	 */
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();

	}

	/**
	 * Purpose:To delete a particular user
	 * 
	 * @param emailId
	 */
	public Response deleteUser(String emailId) {
		userRepository.deleteByEmailId(emailId);
		return new Response(HttpStatus.OK, environment.getProperty("delete"), null);
	}

	/**
	 * Purpose:To update a particular data of user
	 * 
	 * @param oldEmail
	 * @param newEmail
	 */
	@Override
	public Response updateUser(String oldEmailId, String newEmailId) {
		User user = userRepository.findByEmailId(oldEmailId);
		if (user == null) {
			throw new RegistrationException("email is already is registered");
		}
		user.setEmailId(newEmailId);
		user.setUpdatedDate(new Date());
		userRepository.save(user);
		return new Response(HttpStatus.OK, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:To send a verification token to particular user
	 * 
	 * @param emailId
	 */
	public Response sendEmail(String emailId) {

		userRepository.findByEmailId(emailId);
		SimpleMailMessage message = new SimpleMailMessage();

		String token = tokenUtil.createToken(emailId);

		message.setFrom("pratikshatamadalge@gmail.com");
		message.setTo(emailId);
		message.setSubject("Reset password Varification");
		message.setText("Varification token: " + token);

		javaMailSender.send(message);

		return new Response(HttpStatus.OK, environment.getProperty("sent"), null);
	}

	/**
	 * Purpose:To reset the password of particular user
	 * 
	 * @param token
	 * @param newPassword
	 */
	@Override
	public Response resetPassword(String token, String newPassword) {
		String emailId = tokenUtil.decodeToken(token);

		User user = userRepository.findByEmailId(emailId);
		if (user == null) {
			throw new RegistrationException("This emailId is not registerd");
		}
		user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		userRepository.save(user);
		return new Response(HttpStatus.OK, environment.getProperty("update"), null);
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
		if (Boolean.TRUE.equals(flag)) {
			User user = userRepository.findByEmailId(emailId);
			user.setIsActive(true);
			user.setRegisteredDate(new Date());
			userRepository.save(user);
			return new Response(HttpStatus.OK, environment.getProperty("validate"), null);
		}
		throw new RegistrationException("register again");
	}

	@Override
	public Response saveProfile(MultipartFile file, String emailId) throws IOException {

		User user = userRepository.findByEmailId(emailId);
		user.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		userRepository.save(user);
		return new Response(HttpStatus.OK, environment.getProperty("update"), null);
	}

	@Override
	public Response updateProfile(Multipart image, String emailId) {

		return null;
	}

	@Override
	public Response deleteProfile(String emailId) {

		return null;
	}

}
