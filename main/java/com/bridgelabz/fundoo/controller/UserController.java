package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.service.IUserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
		System.out.println("User DTO : " + userDTO);
	String status=userService.register(userDTO);

		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping("/getdata")
	public ResponseEntity<List<User>> getDetails() {

		List<User> user = userService.getUsers();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/login")
	public ResponseEntity<String> Login(@RequestBody UserDTO userDTO) {
		System.out.println("Login data : " + userDTO);
		String str = userService.Login(userDTO);
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestParam String oldEmail, @RequestParam String newEmail) {
		System.out.println("User to be updated: " + oldEmail);
		userService.UpdateUser(oldEmail, newEmail);
		return new ResponseEntity<>("updated", HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> DeleteUser(@RequestBody UserDTO userDTO) {
		System.out.println("User To be Deleted: " + userDTO);
		userService.deleteUser(userDTO);
		return new ResponseEntity<>("Deleted succefully", HttpStatus.OK);
	}

	@GetMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@RequestParam String emailId) {
		System.out.println("password is resetting of the user :" + emailId);
		userService.sendEmail(emailId);

		return new ResponseEntity<>("Mail sent", HttpStatus.OK);
	}

	@PutMapping("/reset")
	public ResponseEntity<String> resetPassword(@RequestParam String token,@RequestParam String newPassword) {

		userService.resetPassword(token,newPassword);
		return new ResponseEntity<>("password reset successfull", HttpStatus.OK);
	}
}