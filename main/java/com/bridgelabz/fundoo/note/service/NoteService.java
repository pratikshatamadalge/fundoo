package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.model.collabl;

public interface NoteService {

	public String createNote(NoteDTO noteDTO);

	public String updateNote(String id, NoteDTO noteDTO) throws NoteServiceException;

	public String deleteNote(String id) throws NoteServiceException;

	public List<Note> getAllNote();

	public String isPinned(String id) throws NoteServiceException;

	public String isTrashed(String id);

	public String isArcheived(String id) throws NoteServiceException;

	public String addLabel(String noteId, String labelId);

	public List<collabl> addCollable(String emailId, String noteId);
	
	
}
