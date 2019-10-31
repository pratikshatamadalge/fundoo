package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Note;

/**
 * Purpose:note service interface to implement all the services to the note
 * controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
public interface NoteService {

	public Response createNote(NoteDTO noteDTO, String token);

	public Response updateNote(String id, NoteDTO noteDTO, String token) throws NoteServiceException;

	public Response deleteNote(String id, String token) throws NoteServiceException;

	public List<Note> getAllNote(String token);

	public Response isPinned(String id, String token) throws NoteServiceException;

	public Response isTrashed(String id, String token);

	public Response isArcheived(String id, String token) throws NoteServiceException;

	public Response addLabel(String noteId, String labelId, String token);

	public List<Note> sortByTitle(String token);

	public List<Note> sortByDate(String token);

	boolean addCollabarator(String noteId, String collabaratorEmail);

	List<String> getAllCollabarators(String noteId);

	Response addRemainder(LocalDateTime dateTime, String noteId, String email, Enum repeat);

	Response updateRemainder(LocalDateTime dateTime, String noteId, String email, Enum repeat);

	public Response deleteRemainder(String noteId, String token);
}
