package com.bridgelabz.fundoo.note.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.bridgelabz.fundoo.note.util.ENUM;
import com.bridgelabz.fundoo.utility.StaticReference;
import com.bridgelabz.fundoo.utility.TokenUtil;

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
	private NoteService noteService;

	@Autowired
	private TokenUtil tokenUtil;

	private static final Logger LOG = LoggerFactory.getLogger(NoteController.class);

	/**
	 * Purpose:To create note
	 * 
	 * @param noteDTO
	 * @return Response( http status,error code,data )
	 */
	@PostMapping("/createNote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_CREATE_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response str = noteService.createNote(noteDTO, emailId);
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	/**
	 * Purpose:To fetch all the note of particular user
	 * 
	 * @param userId
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/getNote")
	public ResponseEntity<List<Note>> getAllNote(@RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_GET_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteService.getAllNote(emailId);
		return new ResponseEntity<>(note, HttpStatus.OK);
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
		LOG.info(StaticReference.CONTROLLER_UPDATE_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response str = null;
		try {
			str = noteService.updateNote(noteId, noteDTO, emailId);
		} catch (NoteServiceException e) {
			LOG.info("updation failed {}", e.getMessage());
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
		LOG.info(StaticReference.CONTROLLER_DELETE_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response str = null;
		try {
			str = noteService.deleteNote(noteId, emailId);
		} catch (NoteServiceException e) {
			LOG.info("delete operation failed {}", e.getMessage());
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
	public ResponseEntity<Response> pinNote(@RequestParam String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_PIN_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.isPinned(noteId, emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Archive Unarchive feature of Note
	 * 
	 * @param noteId
	 * @return Response( http status,error code,data )
	 * @throws NoteServiceException
	 */
	@PutMapping("/archive")
	public ResponseEntity<Response> archiveNote(@RequestParam String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_ARCHIVE_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.isArcheived(noteId, emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose: Trash And Restore feature of note
	 * 
	 * @param noteId
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestParam String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_TRASH_NOTE);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.isTrashed(noteId, emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * addRemainder Purpose:Add a List of label to a note
	 * 
	 * @param noteId
	 * @param labelId
	 * @return Response( http status,error code,data )
	 */
	@PutMapping("/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam String noteId, @RequestParam String labelId,
			@RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_ADD_LABEL);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.addLabel(noteId, labelId, emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * Purpose:Sort the note by title
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/sortByTitle")
	public ResponseEntity<List<Note>> sortByTitle(@RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_SORT_BY_TITLE);
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteService.sortByTitle(emailId);
		return new ResponseEntity<>(note, HttpStatus.OK);
	}

	/**
	 * Purpose:Sort the note by Edited time
	 * 
	 * @return Response( http status,error code,data )
	 */
	@GetMapping("/sortByDate")
	public ResponseEntity<List<Note>> sortByDate(@RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_SORT_BY_DATE);
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteService.sortByDate(emailId);
		return new ResponseEntity<>(note, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @return
	 */
	@GetMapping("/getAllCollabaratorsOfNote")
	public ResponseEntity<Response> getAllCollabarators(@RequestParam String noteId) {
		LOG.info(StaticReference.CONTROLLER_GET_COLLABRATOR);
		Response status = noteService.getAllCollabarators(noteId);
		return new ResponseEntity<>(status, status.getStatusCode());
	}

	/**
	 * @param noteId
	 * @param collabaratorEmail
	 * @return
	 */
	@PutMapping("/addCollabrator")
	public ResponseEntity<Response> addCollabaratorToNote(@RequestParam String noteId,
			@RequestParam String collabaratorEmail) {
		LOG.info(StaticReference.CONTROLLER_ADD_COLLABRATOR);
		Response status = noteService.addCollabarator(noteId, collabaratorEmail);
		return new ResponseEntity<>(status, status.getStatusCode());
	}

	/**
	 * @param reminder
	 * @param repeat
	 * @param noteId
	 * @param token
	 * @return
	 */
	@PutMapping("/addRemainder")
	public ResponseEntity<Response> addRemainder(
			@RequestParam("remainder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminder,
			@RequestParam("repeat") ENUM repeat, @RequestHeader String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_ADD_REMAINDER);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.addRemainder(reminder, noteId, emailId, repeat);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * @param reminder
	 * @param repeat
	 * @param noteId
	 * @param token
	 * @return
	 */
	@PutMapping("/updateRemainder")
	public ResponseEntity<Response> updateRemainder(
			@RequestParam("remainder") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminder,
			@RequestParam("repeat") ENUM repeat, @RequestParam String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_UPDATE_REMAINDER);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.updateRemainder(reminder, noteId, emailId, repeat);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return
	 */
	@DeleteMapping("/deleteRemainder")
	public ResponseEntity<Response> deleteRemainder(@RequestParam String noteId, @RequestHeader String token) {
		LOG.info(StaticReference.CONTROLLER_DELETE_REMAINDER);
		String emailId = tokenUtil.decodeToken(token);
		Response status = noteService.deleteRemainder(noteId, emailId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}