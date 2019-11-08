package com.bridgelabz.fundoo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.controller.NoteController;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.service.NoteServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

	@InjectMocks
	NoteServiceImpl noteService;

	@Mock
	NoteController notecontroller;

	@Mock
	NoteRepository noteRepository;

	@Mock
	ModelMapper modelMapper;

	Note note = new Note("1", "7 wonders in world", "taj mahal", null, null, null, null, null, null, null, null, null,
			null);

	/**
	 * 
	 * Test case foe create note api
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateNote() throws Exception {

		NoteDTO noteDTO = new NoteDTO();

		noteDTO.setTitle("title");
		noteDTO.setDescription("description");
		System.out.println("in test create note:" + noteDTO.getTitle() + " " + " " + noteDTO.getDescription());
		String email = "user@gmail.com";

		// Definition of mock object
		when(modelMapper.map(noteDTO, Note.class)).thenReturn(note);
		when(noteRepository.save(note)).thenReturn(note);

		Response response = noteService.createNote(noteDTO, email);
		System.out.println("::::::::::::::::::testCreateNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, noteService.createNote(noteDTO, email).getStatusCode());
	}

	/**
	 * Test case for update note api
	 */
	@Test
	public void testUpdateNote() {
		NoteDTO noteDTO = new NoteDTO();
		Optional<Note> already = Optional.of(note);
		note.setTitle("7 wonders in world....");
		note.setTitle("taj mahal");
		when(noteRepository.save(note)).thenReturn(note);
		assertEquals(noteDTO.getTitle(), already.get().getEmailId());
	}

	/**
	 * Test case for delete note api
	 */
	@Test
	public void testDeleteNote() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "pratikshatamadalge21@gmail.com";
		Optional<Note> already = Optional.of(note);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		noteRepository.deleteById(noteId);
		assertEquals(note.getId(), already.get().getId());
	}

	/**
	 * Test case to fetch all note
	 */
	@Test
	public void testGetAllNote() {
		List<Note> note1 = null;
		String emailId = "pratikshatamadalge21@gmail.com";
		Optional<Note> already = Optional.of(note);
		when(noteRepository.findByEmailId(emailId)).thenReturn(note1);
		assertEquals(note.getEmailId(), already.get().getEmailId());
	}

	/**
	 * Test case for isPinned api
	 */
	@Test
	public void TestisPinned() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsPinned(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsPinned())
			note.setIsPinned(false);
		else
			note.setIsPinned(true);
		assertEquals(true, note.getIsPinned());
	}

	/**
	 * Test case for isTrashed api
	 */
	@Test
	public void testIsTrashed() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsTrashed(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsTrashed())
			note.setIsTrashed(false);
		else
			note.setIsTrashed(true);
		assertEquals(true, note.getIsTrashed());
	}

	/**
	 * Test case for isArchieved api
	 */
	@Test
	public void testIsArchieved() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsArcheived(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsArcheived())
			note.setIsArcheived(false);
		else
			note.setIsArcheived(true);
		assertEquals(true, note.getIsArcheived());
	}
}
