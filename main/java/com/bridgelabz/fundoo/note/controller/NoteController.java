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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.Response;
//import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.NoteService;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	Environment environment;

	@PostMapping("/createNote")
	public Response createNote(@RequestBody NoteDTO noteDTO) {

		String str = noteService.createNote(noteDTO);
		return new Response(200, str, null);
	}

	@GetMapping("/getNote")
	public Response getAllNote(String userId) {
		List<Note> note = noteService.getAllNote();
		return new Response(200, environment.getProperty("fetched"), note);
	}

	@PutMapping("/updateNote")
	public Response updateNote(@RequestBody NoteDTO noteDTO, @RequestParam String id) {
		System.out.println("in controller :" + id + ", " + noteDTO);
		String str = null;
		try {
			str = noteService.updateNote(id, noteDTO);
		} catch (NoteServiceException e) {

			e.printStackTrace();
		}
		return new Response(200, str, null);
	}

	@DeleteMapping("/deleteNote")
	public Response deleteNote(@RequestParam String id) {
		System.out.println("in controller id :" + id);
		String str = null;
		try {
			str = noteService.deleteNote(id);
		} catch (NoteServiceException e) {

			e.printStackTrace();
		}
		return new Response(200, str, null);
	}

	@PutMapping("/pin")
	public Response pinNote(@RequestParam String id) throws NoteServiceException {
		String status = noteService.isPinned(id);
		return new Response(200, status, null);
	}

	@PutMapping("/archive")
	public Response archiveNote(@RequestParam String id) throws NoteServiceException {
		String status = noteService.isArcheived(id);
		return new Response(200, status, null);
	}

	@PutMapping("/trash")
	public Response trashNote(@RequestParam String id) {
		String status = noteService.isTrashed(id);
		return new Response(200, status, null);
	}

	@PutMapping("/addLabel")
	public Response addLabel(@RequestParam String noteId, @RequestParam String labelId) {
		noteService.addLabel(noteId,labelId);
		return new Response(400, "label added to the note", null);
	}
}
