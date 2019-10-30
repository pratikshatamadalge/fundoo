package com.bridgelabz.fundoo.note.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

/**
 * Purpose:To implement all the service for the note controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	Environment environment;

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	TokenUtil tokenUtil;

	/**
	 * Purpose:To create a Note
	 * 
	 * @param noteDTO
	 * @return Environment variable as a string
	 */
	@Override
	public Response createNote(NoteDTO noteDTO, String token) {
		String emailId = tokenUtil.decodeToken(token);

		Note note = modelMapper.map(noteDTO, Note.class);
		note.setCreatedTime(new Date());
		note.setEditedTime(new Date());
		note.setEmailId(emailId);
		noteRepository.save(note);
		return new Response(200, environment.getProperty("success"), null);
	}

	/**
	 * Purpose: To update Note
	 * 
	 * @param id
	 * @param noteDTO
	 * @return Environment variable as a string
	 * @throws NoteServiceException
	 */
	@Override
	public Response updateNote(String noteId, NoteDTO noteDTO, String token) throws NoteServiceException {
		String emailId = tokenUtil.decodeToken(token);
		// Note note = noteRepository.findByEmailId(emailId);
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note == null) {
			throw new NoteServiceException("note is not present");
		}
		if (noteDTO.getTitle() != null && noteDTO.getDescription() != null) {
			note.setTitle(noteDTO.getTitle());
			note.setDescription(noteDTO.getDescription());
			note.setEditedTime(new Date());
		} else if (noteDTO.getTitle() != null) {
			note.setTitle(noteDTO.getTitle());
			note.setEditedTime(new Date());
		} else if (noteDTO.getDescription() != null) {
			note.setDescription(noteDTO.getDescription());
			note.setEditedTime(new Date());
		}
		noteRepository.save(note);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose: To delete a note
	 * 
	 * @param id
	 * @return Environment variable as a string
	 * @throws NoteServiceException
	 */
	@Override
	public Response deleteNote(String noteId, String token) throws NoteServiceException {
		String emailId = tokenUtil.decodeToken(token);
		Note noteUser = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (noteUser.getEmailId().contentEquals(emailId)) {
			noteRepository.deleteById(noteId);
			return new Response(200, environment.getProperty("delete"), null);
		}
		throw new NoteServiceException("note is not present");
//		List<Label> label = note.getLabels();
//		for (Label label1 : label) {
//			label1.getNote().remove(note);
	}

	/**
	 * Purpose:To fetch all the note
	 * 
	 * @return
	 */
	@Override
	public List<Note> getAllNote(String token) {
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteRepository.findAll();

		// List<Label> label = note.getLabels();
		for (Note note1 : note) {
			note1.getEmailId().matches(emailId);
		}
		return note;
	}

	/**
	 * Purpose:To pin and unpin the particular note
	 * 
	 * @param noteId
	 * @return Environment variable as a string
	 * @throws NoteServiceException
	 */
	@Override
	public Response isPinned(String noteId, String token) throws NoteServiceException {
		String emailId = tokenUtil.decodeToken(token);

		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note == null) {
			throw new NoteServiceException(environment.getProperty("invalid"));
		}
		if (note.getIsPinned()) {
			note.setIsPinned(false);
		} else {
			note.setIsPinned(true);
		}
		noteRepository.save(note);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose: To move note to trash or restore
	 * 
	 * @param id
	 * @return Environment variable as a string
	 */
	@Override
	public Response isTrashed(String noteId, String token) {
		String emailId = tokenUtil.decodeToken(token);
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note.getIsTrashed() == true) {
			note.setIsTrashed(false);
		} else {
			note.setIsTrashed(true);
		}
		noteRepository.save(note);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose: To Archive and Unarcheive a particular note
	 * 
	 * @param id
	 * @return Environment variable as a string
	 * @throws NoteServiceException
	 */
	@Override
	public Response isArcheived(String noteId, String token) throws NoteServiceException {
		String emailId = tokenUtil.decodeToken(token);
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note == null) {
			throw new NoteServiceException(environment.getProperty("invalid"));
		}
		if (note.getIsArcheived() == true) {
			note.setIsArcheived(false);
		} else {
			note.setIsArcheived(true);
		}
		noteRepository.save(note);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:To add label to a particular note
	 * 
	 * @param noteId
	 * @param labelId
	 * @return Environment variable as a string
	 */
	@Override
	public Response addLabel(String noteId, String labelId, String token) {
		String emailId = tokenUtil.decodeToken(token);
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);

		System.out.println("in service :" + note);
		if (note == null) {
			return new Response(400, environment.getProperty("notExist"), null);
		}
		Label label = labelRepository.findById(labelId).get();
		if (label == null) {
			return new Response(400, environment.getProperty("notExist"), null);
		}
		System.out.println("in service :" + label);
		note.getLabels().add(label);
		label.getNote().add(note);
		labelRepository.save(label);
		noteRepository.save(note);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:Sort the note by title
	 * 
	 * @return Note list
	 */
	public List<Note> sortByTitle(String token) {
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteRepository.findByEmailId(emailId);
		note.sort((n1, n2) -> n1.getTitle().compareTo(n2.getTitle()));
		return note;
	}

	/**
	 * Purpose:Sort the note by date
	 * 
	 * @return Note list
	 */
	public List<Note> sortByDate(String token) {
		String emailId = tokenUtil.decodeToken(token);
		List<Note> note = noteRepository.findByEmailId(emailId);
		note.sort((n1, n2) -> n1.getCreatedTime().compareTo(n2.getCreatedTime()));
		return note;
	}

	/**
	 * @param noteId
	 * @param collabaratorEmail
	 * @return
	 */
	@Override
	public boolean addCollabarator(String noteId, String collabaratorEmail) {
		Note note = noteRepository.findById(noteId).get();
		if (note == null)
			return false;
		else {
			note.getCollab().add(collabaratorEmail);
			noteRepository.save(note);
		}
		return true;
	}

	/**
	 * @param noteId
	 * @return
	 */
	@Override
	public List<String> getAllCollabarators(String noteId) {
		Note note = noteRepository.findById(noteId).get();
		if (note != null)
			return noteRepository.findById(noteId).get().getCollab();
		else
			return null;
	}
}