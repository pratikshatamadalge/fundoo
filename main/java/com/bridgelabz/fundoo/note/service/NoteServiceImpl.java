package com.bridgelabz.fundoo.note.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteServiceException;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.model.collabl;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

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

	@Override
	public String createNote(NoteDTO noteDTO) {
		System.out.println("from controller :" + noteDTO);
		Note note = modelMapper.map(noteDTO, Note.class);
		note.setCreatedTime(new Date());
		note.setEditedTime(new Date());
		note.setIsPinned(false);
		note.setIsArcheived(false);
		note.setIsTrashed(false);
		noteRepository.save(note);
		return environment.getProperty("success");
	}

	@Override
	public String updateNote(String id, NoteDTO noteDTO) throws NoteServiceException {
		Note note = noteRepository.findById(id).get();
		if (note == null) {
			throw new NoteServiceException("note is not present");
		}
		if (noteDTO.getTitle() != null && noteDTO.getDescription() != null) {
			note.setTitle(noteDTO.getTitle());
			note.setDescription(noteDTO.getDescription());
			note.setEditedTime(new Date());
			noteRepository.save(note);
		} else if (noteDTO.getTitle() != null) {
			note.setTitle(noteDTO.getTitle());
			note.setEditedTime(new Date());
			noteRepository.save(note);
		} else if (noteDTO.getDescription() != null) {
			note.setDescription(noteDTO.getDescription());
			note.setEditedTime(new Date());
			noteRepository.save(note);
		}
		return environment.getProperty("update");
	}

	@Override
	public String deleteNote(String Id) throws NoteServiceException {
		System.out.println("in deleteNote :" + Id);
		Note note = noteRepository.findById(Id).get();
		if (note == null) {
			throw new NoteServiceException("note is not present");
		}
		noteRepository.deleteById(Id);
		return environment.getProperty("delete");
	}

	@Override
	public List<Note> getAllNote() {
		List<Note> note = noteRepository.findAll();
		return note;
	}

	@Override
	public String isPinned(String id) throws NoteServiceException {
		Note note = noteRepository.findById(id).get();
		if (note == null) {
			throw new NoteServiceException(environment.getProperty("invalid"));
		}
		if (note.getIsPinned() == true) {
			note.setIsPinned(false);
		} else {
			note.setIsPinned(true);
			note.setIsArcheived(false);
			note.setIsTrashed(false);
		}

		noteRepository.save(note);
		return environment.getProperty("update");
	}

	@Override
	public String isTrashed(String id) {
		Note note = noteRepository.findById(id).get();
		if (note.getIsTrashed() == true) {
			note.setIsTrashed(false);
		} else {
			note.setIsTrashed(true);
			note.setIsArcheived(false);
			note.setIsPinned(false);
		}
		noteRepository.save(note);
		return environment.getProperty("update");
	}

	@Override
	public String isArcheived(String id) throws NoteServiceException {
		Note note = noteRepository.findById(id).get();
		if (note == null) {
			throw new NoteServiceException(environment.getProperty("invalid"));
		}
		if (note.getIsArcheived() == true) {
			note.setIsArcheived(false);
		} else {
			note.setIsArcheived(true);
			note.setIsPinned(false);
			note.setIsTrashed(false);
		}
		noteRepository.save(note);
		return environment.getProperty("update");
	}

	@Override
	public String addLabel(String noteId, String labelId) {
		Note note = noteRepository.findById(noteId).get();

		System.out.println("in service :" + note);
		if (note == null) {
			return environment.getProperty("notExist");
		}
		Label label = labelRepository.findById(labelId).get();
		if (label == null) {
			return environment.getProperty("notExist");
		}
		System.out.println("in service :" + label);
		note.getLabels().add(label);
		label.getNote().add(note);
		labelRepository.save(label);
		noteRepository.save(note);
		return environment.getProperty("update");
	}

	@Override
	public List<collabl> addCollable(String emailId, String noteId) {
		return null;
	}
}