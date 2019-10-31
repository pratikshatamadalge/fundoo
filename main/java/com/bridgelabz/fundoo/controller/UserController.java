package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

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

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.service.IUserService;

/**
 * Purpose:User Controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * Purpose:Register the user
	 * 
	 * @param regDTO
	 * @return Response( http status,error code,data )
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody RegisterDTO regDTO) {
		System.out.println("User DTO : " + regDTO);
		Response status = userService.register(regDTO);

		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Fetch all the users from the data base
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/getdata")
	public ResponseEntity<List<User>> getDetails() {
		List<User> user = userService.getUsers();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Purpose: login user
	 * 
	 * @param userDTO
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/login")
	public ResponseEntity<Response> Login(@Valid @RequestBody UserDTO userDTO) {
		System.out.println("Login data : " + userDTO);
		Response str = null;
		try {
			str = userService.Login(userDTO);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Response>(str, HttpStatus.OK);
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

		Response status = userService.UpdateUser(oldEmail, newEmail);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Delete particular user using unique id
	 * 
	 * @param emailId
	 * @return Response( http status,error code,data )
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> DeleteUser(@RequestParam String emailId) {
		System.out.println("User To be Deleted: " + emailId);
		Response status = userService.deleteUser(emailId);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:If user Forgot password then varification token is send to the user
	 * 
	 * @param emailId
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/forgot")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId) {
		System.out.println("password is resetting of the user :" + emailId);
		Response status = userService.sendEmail(emailId);

		return new ResponseEntity<Response>(status, HttpStatus.OK);
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

		Response status = userService.resetPassword(token, newPassword);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Validate the user by verifying the emailId using jwt token
	 * 
	 * @param token
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/validate")
	public ResponseEntity<Response> validateUser(@RequestHeader String token) {
		Response status = userService.validateUser(token);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}
}