package com.bridgelabz.fundoo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.model.MailData;
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

	@Autowired
	private RabbitTemplate rabbitTemplate;

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
			throw new RegistrationException(StaticReference.EXIST);
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRegisteredDate(new Date());
		String token = tokenUtil.createToken(regDTO.getEmailId());
		MailData maildata = new MailData();
		maildata.setEmailId(regDTO.getEmailId());
		maildata.setToken(token);
		maildata.setMailMessage("Varification mail");
		rabbitTemplate.convertAndSend("key", maildata);
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
		return new Response(HttpStatus.OK, StaticReference.UPDATE, null);
	}

	/**
	 * Purpose:To send a verification token to particular user
	 * 
	 * @param emailId
	 */
	public Response sendEmail(String emailId) {

		User user = userRepository.findByEmailId(emailId);
		if (user != null) {
			SimpleMailMessage message = new SimpleMailMessage();

			String token = tokenUtil.createToken(emailId);

			message.setFrom("pratikshatamadalge@gmail.com");
			message.setTo(emailId);
			message.setSubject("Reset password Varification");
			message.setText("Verification token: " + token);

			javaMailSender.send(message);

			return new Response(HttpStatus.OK, environment.getProperty("sent"), null);
		}
		return new Response(HttpStatus.BAD_REQUEST, StaticReference.NOTEXIST, null);
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

			userRepository.save(user);
			return new Response(HttpStatus.OK, environment.getProperty("validate"), null);
		}
		throw new RegistrationException("register again");
	}

	/**
	 * Purpose:To save profile pic
	 * 
	 * @param file
	 * @param emailId
	 * @return
	 * @throws LoginException
	 */
	public Response saveProfilePic(MultipartFile file, String emailId) throws IOException, LoginException {
		User user = userRepository.findByEmailId(emailId);
		if (user == null)
			throw new LoginException(StaticReference.NOTEXIST);

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		String fileLocation = StaticReference.PROFILE_PIC_LOCATION + emailId + "." + extension;
		Path path = Paths.get(fileLocation);
		Files.write(path, bytes);
		user.setProfilePic(fileLocation);
		userRepository.save(user);
		return new Response(HttpStatus.OK, null, StaticReference.SUCCESSFULL);
	}

	/**
	 * Purpose: To delete profile pic
	 * 
	 * @param emailId
	 * @return
	 * @throws LoginException
	 */
	@Override
	public Response deleteProfilePic(String emailId) throws LoginException {
		User user = userRepository.findByEmailId(emailId);
		if (user == null)
			throw new LoginException(StaticReference.NOTEXIST);
		String fileLocation = user.getProfilePic();
		File file = new File(fileLocation);
		Boolean flag = file.delete();
		if (flag) {
			user.setProfilePic("");
			userRepository.save(user);
			return new Response(HttpStatus.OK, null, StaticReference.DELETE);
		}
		return new Response(HttpStatus.OK, null, StaticReference.DELETE);
	}

	/**
	 * Purpose:To update profile pic
	 * 
	 * @param file
	 * @param emailId
	 * @return
	 * @throws IOException
	 * @throws LoginException
	 */
	public Response updateProfilePic(MultipartFile file, String emailId) throws IOException, LoginException {

		User user = userRepository.findByEmailId(emailId);
		if (user == null)
			throw new LoginException(StaticReference.NOTEXIST);

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		String fileLocation = StaticReference.PROFILE_PIC_LOCATION + emailId + "." + extension;
		Path path = Paths.get(fileLocation);
		Files.write(path, bytes);

		user.setProfilePic(fileLocation);
		userRepository.save(user);
		return new Response(HttpStatus.OK, null, StaticReference.UPDATE);
	}
}
