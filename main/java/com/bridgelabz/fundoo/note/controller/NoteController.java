package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.NoteService;

/**
 * Purpose:Note controller to do CRUD operation of a note
 * 
 * @author Pratiksha Tamadalge
 *
 */
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	Environment environment;

	/**
	 * Purpose:To create note
	 * 
	 * @param noteDTO
	 * @return Response( http status,error code,data )
	 */
	@PostMapping("/createNote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO, @RequestHeader String token) {
		Response str = noteService.createNote(noteDTO, token);
		return new ResponseEntity<Response>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:To fetch all the note of particular user
	 * 
	 * @param userId
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/getNote")
	public ResponseEntity<List> getAllNote(@RequestHeader String token) {
		List<Note> note = noteService.getAllNote(token);
		return new ResponseEntity<List>(note, HttpStatus.OK);
	}

	/**
	 * Purpose:To update a note of particular user
	 * 
	 * @param noteDTO
	 * @param noteId
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/updateNote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDTO, @RequestParam String noteId,
			@RequestHeader String token) {
		System.out.println("in controller :" + noteId + ", " + noteDTO);
		Response str = null;
		try {
			str = noteService.updateNote(noteId, noteDTO, token);
		} catch (NoteServiceException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:To delete a particular note of particular note
	 * 
	 * @param id
	 * @return Response( http status,error code,data )
	 */
	@DeleteMapping("/deleteNote")
	public ResponseEntity<Response> deleteNote(@RequestParam String noteId, @RequestHeader String token) {
		System.out.println("in controller id :" + noteId);
		Response str = null;
		try {
			str = noteService.deleteNote(noteId, token);
		} catch (NoteServiceException e) {

			e.printStackTrace();
		}
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:Pin UnPin operation of note
	 * 
	 * @param noteId
	 * @return Response( http status,error code,data )
	 * @throws NoteServiceException
	 */
	@PutMapping("/pin")
	public ResponseEntity<Response> pinNote(@RequestParam String noteId, @RequestHeader String token)
			throws NoteServiceException {
		Response status = noteService.isPinned(noteId, token);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Archive Unarchive feature of Note
	 * 
	 * @param noteId
	 * @return Response( http status,error code,data )
	 * @throws NoteServiceException
	 */
	@PutMapping("/archive")
	public ResponseEntity<Response> archiveNote(@RequestParam String noteId, @RequestHeader String token)
			throws NoteServiceException {
		Response status = noteService.isArcheived(noteId, token);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Trash And Restore feature of note
	 * 
	 * @param noteId
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestParam String noteId, @RequestHeader String token) {
		Response status = noteService.isTrashed(noteId, token);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Add a List of label to a note
	 * 
	 * @param noteId
	 * @param labelId
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam String noteId, @RequestParam String labelId,
			@RequestHeader String token) {
		Response status = noteService.addLabel(noteId, labelId, token);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Sort the note by title
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/sortByTitle")
	public ResponseEntity<List> sortByTitle(@RequestHeader String token) {
		List<Note> note = noteService.sortByTitle(token);
		return new ResponseEntity<List>(note, HttpStatus.OK);
	}

	/**
	 * Purpose:Sort the note by Edited time
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/sortByDate")
	public ResponseEntity<List> sortByDate(@RequestHeader String token) {
		List<Note> note = noteService.sortByDate(token);
		return new ResponseEntity<List>(note, HttpStatus.OK);
	}
}
