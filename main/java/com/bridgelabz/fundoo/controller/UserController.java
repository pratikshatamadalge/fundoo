package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.Multipart;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.service.IUserService;
import com.bridgelabz.fundoo.utility.StaticReference;
import com.bridgelabz.fundoo.utility.TokenUtil;

/**
 * Purpose:User Controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;

	@Autowired
	private TokenUtil tokenUtil;

	/**
	 * Purpose:Register the user
	 * 
	 * @param regDTO
	 * @return Response( http status,error code,data )
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody RegisterDTO regDTO) {
		LOG.info(StaticReference.CONTROLLER_REGISTER_USER);
		Response status = userService.register(regDTO);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Fetch all the users from the data base
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/getdata")
	public ResponseEntity<List<User>> getDetails() {
		LOG.info(StaticReference.CONTROLLER_REGISTER_USER);
		List<User> user = userService.getUsers();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Purpose: login user
	 * 
	 * @param userDTO
	 * @return Response( http status,error code,data )
	 * @throws LoginException
	 */
	@GetMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestBody UserDTO userDTO) throws LoginException {
		LOG.info(StaticReference.CONTROLLER_LOGIN_USER);
		Response str = null;
		try {
			str = userService.login(userDTO);
		} catch (LoginException e) {
			throw new LoginException(StaticReference.INVALID);
		}
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:update any data of a particular user
	 * 
	 * @param oldEmail
	 * @param newEmail
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/update")
	public ResponseEntity<Response> updateUser(@RequestParam String oldEmail, @RequestParam String newEmail) {
		LOG.info(StaticReference.CONTROLLER_UPDATE_USER);
		Response status = userService.updateUser(oldEmail, newEmail);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Delete particular user using unique id
	 * 
	 * @param emailId
	 * @return Response( http status,error code,data )
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String emailId) {
		LOG.info(StaticReference.CONTROLLER_DELETE_USER);
		Response status = userService.deleteUser(emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:If user Forgot password then varification token is send to the user
	 * 
	 * @param emailId
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/forgot")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId) {
		LOG.info(StaticReference.CONTROLLER_FORGOT);
		Response status = userService.sendEmail(emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Reset password with the help of jwt token
	 * 
	 * @param token
	 * @param newPassword
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/reset")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, @RequestHeader String newPassword) {

		LOG.info(StaticReference.CONTROLLER_RESET);
		Response status = userService.resetPassword(token, newPassword);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Validate the user by verifying the emailId using jwt token
	 * 
	 * @param token
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/validate")
	public ResponseEntity<Response> validateUser(@RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_VALIDATE_USER);
		Response status = userService.validateUser(token);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/profilepic")
	public ResponseEntity<Response> addProfile(@RequestParam("file") MultipartFile file, @RequestHeader String token)
			throws IOException, LoginException {
		Response response = userService.saveProfilePic(file, tokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@DeleteMapping("/profilepic")
	public ResponseEntity<Response> deleteProfile(@RequestHeader String token) throws LoginException {
		Response response = userService.deleteProfilePic(tokenUtil.decodeToken(token));

		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/profilepic")
	public ResponseEntity<Response> updateProfile(@RequestParam("file") MultipartFile file, @RequestHeader String token)
			throws IOException, LoginException {
		Response response = userService.updateProfilePic(file, tokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}
}