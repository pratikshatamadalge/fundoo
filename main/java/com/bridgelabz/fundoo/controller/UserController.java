package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

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

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public Response register(@Valid @RequestBody RegisterDTO regDTO) {
		System.out.println("User DTO : " + regDTO);
		String status = userService.register(regDTO);

		return new Response(200, status, null);
	}

	@GetMapping("/getdata")
	public Response getDetails() {

		List<User> user = userService.getUsers();
		return new Response(200, "fetched all data successfully", user);
	}

	@GetMapping("/login")
	public Response Login(@Valid @RequestBody UserDTO userDTO) {// throws LoginException {
		System.out.println("Login data : " + userDTO);
		String str = null;
		try {
			str = userService.Login(userDTO);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Response(200, str, null);
	}

	@PutMapping("/update")
	public Response updateUser(@RequestParam String oldEmail, @RequestParam String newEmail) {

		userService.UpdateUser(oldEmail, newEmail);
		return new Response(200, "updated", null);
	}

	@DeleteMapping("/delete")
	public Response DeleteUser(@RequestParam String emailId) {
		System.out.println("User To be Deleted: " + emailId);
		userService.deleteUser(emailId);
		return new Response(200, "deleted", null);
	}

	@GetMapping("/forgot")
	public Response forgotPassword(@RequestParam String emailId) {
		System.out.println("password is resetting of the user :" + emailId);
		userService.sendEmail(emailId);

		return new Response(200, "mail sent", null);
	}

	@PutMapping("/reset")
	public Response resetPassword(@RequestHeader String token, @RequestHeader String newPassword) {

		userService.resetPassword(token, newPassword);
		return new Response(200, "password reset successfull", null);
	}

	@GetMapping("/validate")
	public Response validateUser(@RequestHeader String token) {
		String status = userService.validateUser(token);
		return new Response(200, status, null);
	}
}