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

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.service.LabelService;

@RequestMapping("/label")
@RestController
public class LabelController {
	@Autowired
	LabelService labelService;

	@PostMapping("/createLabel")
	public ResponseEntity<String> createLabel(@RequestParam String token, @RequestBody LabelDTO labelDTO) {
		System.out.println("inside the controller :" + labelDTO);
		String str = labelService.createLabel(token, labelDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@GetMapping("/getLabel")
	public ResponseEntity<List> getLabel(@RequestHeader String token) {
		List<Label> label = labelService.getLabel(token);
		return new ResponseEntity<>(label, HttpStatus.OK);
	}

	@DeleteMapping("/deleteLabel")
	public ResponseEntity<String> deleteLabel(@RequestHeader String token, @RequestParam String labelId) {
		String str = labelService.deleteLabel(token, labelId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@PutMapping("/updateLabel")
	public ResponseEntity<String> updateLabel(@RequestHeader String token, @RequestParam String labelId,
			@RequestParam String labelName) {
		String str = labelService.updateLabel(token, labelId, labelName);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
}
