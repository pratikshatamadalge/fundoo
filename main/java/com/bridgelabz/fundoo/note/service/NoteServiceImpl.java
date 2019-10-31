package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.mail.search.NotTerm;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:message.properties")
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment environment;

	@Autowired
	private LabelRepository labelRepository;

	/**
	 * Purpose:To create a Note
	 * 
	 * @param noteDTO
	 * @return Environment variable as a string
	 */
	@Override
	public Response createNote(NoteDTO noteDTO, String emailId) {

		Note note = modelMapper.map(noteDTO, Note.class);
		note.setCreatedTime(new Date());
		note.setEditedTime(new Date());
		note.setEmailId(emailId);
		note.setIsPinned(false);
		note.setIsArcheived(false);
		note.setIsTrashed(false);
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
	public Response updateNote(String noteId, NoteDTO noteDTO, String emailId) throws NoteServiceException {
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note == null) {
			throw new NoteServiceException(environment.getProperty("notExist"));
		}
		if (noteDTO.getTitle() != null && noteDTO.getDescription() != null) {
			note.setTitle(noteDTO.getTitle());
			note.setDescription(noteDTO.getDescription());
		} else if (noteDTO.getTitle() != null) {
			note.setTitle(noteDTO.getTitle());
		} else if (noteDTO.getDescription() != null) {
			note.setDescription(noteDTO.getDescription());
		}
		note.setEditedTime(new Date());
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
	public Response deleteNote(String noteId, String emailId) throws NoteServiceException {
		Note noteUser = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (noteUser == null) {
			throw new NoteServiceException(environment.getProperty("notExist"));
		}
		if (noteUser.getEmailId().contentEquals(emailId) && noteUser.getIsTrashed()) {
			noteRepository.deleteById(noteId);

//		List<Label> label = note.getLabels();
//			for (Label label1 : label) {
//				label1.getNote().remove(note);
			return new Response(200, environment.getProperty("delete"), null);
		}
		return new Response(400, "note is not present or it is not present in trash", null);

	}

	/**
	 * Purpose:To fetch all the note
	 * 
	 * @return
	 */
	@Override
	public List<Note> getAllNote(String emailId) {
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
	public Response isPinned(String noteId, String emailId) throws NoteServiceException {
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);

		if (note == null) {
			System.out.println("in exception ");
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
	public Response isTrashed(String noteId, String emailId) {
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
	public Response isArcheived(String noteId, String emailId) throws NoteServiceException {
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
	public Response addLabel(String noteId, String labelId, String emailId) {
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
	public List<Note> sortByTitle(String emailId) {
		List<Note> note = noteRepository.findByEmailId(emailId);
		note.sort((n1, n2) -> n1.getTitle().compareTo(n2.getTitle()));
		return note;
	}

	/**
	 * Purpose:Sort the note by date
	 * 
	 * @return Note list
	 */
	public List<Note> sortByDate(String emailId) {
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

	/**
	 * @param dateTime
	 * @param noteId
	 * @param email
	 * @param repeat
	 * @return
	 */
	public Response addRemainder(LocalDateTime dateTime, String noteId, String email, Enum repeat) {
		Note note = noteRepository.findByIdAndEmailId(noteId, email);
		if (note == null)
			throw new NoteServiceException(environment.getProperty("notExist"));
		if (dateTime.compareTo(LocalDateTime.now()) > 0) {
			note.setRemainder(dateTime);
			note.setRepeat(repeat);
			noteRepository.save(note);
			return new Response(200, "Remainder", null);
		}
		return new Response(400, "Failed to add remainder date time expired", null);
	}

	/**
	 * @param dateTime
	 * @param noteId
	 * @param email
	 * @param repeat
	 * @return
	 */
	public Response updateRemainder(LocalDateTime dateTime, String noteId, String email, Enum repeat) {
		Note note = noteRepository.findByIdAndEmailId(noteId, email);
		if (note == null)
			throw new NoteServiceException(environment.getProperty("notExist"));
		if (dateTime.compareTo(LocalDateTime.now()) > 0) {
			note.setRemainder(dateTime);
			note.setRepeat(repeat);
			noteRepository.save(note);
			return new Response(200, "Remainder", null);
		}
		return new Response(400, "Failed to add remainder date time expired", null);
	}

	/**
	 * @param noteId
	 * @param token
	 * @return
	 */
	@Override
	public Response deleteRemainder(String noteId, String emailId) {
		Note note = noteRepository.findByIdAndEmailId(noteId, emailId);
		if (note == null)
			throw new NoteServiceException(environment.getProperty("notExist"));
		note.setRemainder(null);
		noteRepository.save(note);
		return null;
	}
}