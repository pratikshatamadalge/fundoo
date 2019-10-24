package com.bridgelabz.fundoo.note.controller;

import java.util.List;

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

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.service.LabelService;

/**
 * Purpose:Label controller To do CRUD operations
 * 
 * @author Pratiksha Tamadalge
 *
 */
@RequestMapping("/label")
@RestController
public class LabelController {
	@Autowired
	LabelService labelService;

	/**
	 * Purpose:To create label
	 * 
	 * @param token
	 * @param labelDTO
	 * @return Response( http status,error code,data )
	 */
	@PostMapping("/createLabel")
	public ResponseEntity<Response> createLabel(@RequestParam String token, @RequestBody LabelDTO labelDTO) {
		System.out.println("inside the controller :" + labelDTO);
		Response str = labelService.createLabel(token, labelDTO);
		return new ResponseEntity<Response>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:To Fetch all the label
	 * 
	 * @param token
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/getLabel")
	public ResponseEntity<List<Label>> getLabel(@RequestHeader String token) {
		List<Label> label = labelService.getLabel(token);
		return new ResponseEntity<List<Label>>(label, HttpStatus.OK);
	}

	/**
	 * Purpose:To delete a particular label of user
	 * 
	 * @param token
	 * @param labelId
	 * @return Response( http status,error code,data )
	 */
	@DeleteMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLabel(@RequestHeader String token, @RequestParam String labelId) {
		Response str = labelService.deleteLabel(token, labelId);
		return new ResponseEntity<Response>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:To update Particular label of particular user
	 * 
	 * @param token
	 * @param labelId
	 * @param labelName
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/updateLabel")
	public ResponseEntity<Response> updateLabel(@RequestHeader String token, @RequestParam String labelId,
			@RequestParam String labelName) {
		Response str = labelService.updateLabel(token, labelId, labelName);
		return new ResponseEntity<Response>(str, HttpStatus.OK);
	}
}
