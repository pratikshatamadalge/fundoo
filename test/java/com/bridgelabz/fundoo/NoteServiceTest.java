package com.bridgelabz.fundoo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.note.controller.NoteController;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.note.service.NoteServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {
	private MockMvc mockmvc;

	@InjectMocks
	NoteServiceImpl noteService;

	@Mock
	NoteController notecontroller;

	@Mock
	NoteRepository noteRepository;

	@Mock
	NoteDTO noteDTO;

	@Mock
	ModelMapper modelMapper;

	Note note = new Note("7 wonders in world", "taj mahal", null, null, null, null, null, null, null, null, null, null,
			null);

	@Before
	public void Setup() throws Exception {
		mockmvc = MockMvcBuilders.standaloneSetup(noteService).build();
	}

	@Test
	public void createNoteTest() throws Exception {
		noteDTO.setDescription("7 wonders in world");
		noteDTO.setTitle("naygara falls");
		Optional<Note> already = Optional.of(note);
		when(modelMapper.map(noteDTO, Note.class)).thenReturn(note);
		when(noteRepository.save(note)).thenReturn(note);
		assertEquals(noteDTO.getTitle(), already.get().getEmailId());
	}

	@Test
	public void updateNoteTest() {
		Optional<Note> already = Optional.of(note);
		note.setTitle("7 wonders in world....");
		note.setTitle("taj mahal");
		when(noteRepository.save(note)).thenReturn(note);
		assertEquals(noteDTO.getTitle(), already.get().getEmailId());
	}

	@Test
	public void deleteNoteTest() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "pratikshatamadalge21@gmail.com";
		Optional<Note> already = Optional.of(note);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		noteRepository.deleteById(noteId);
		assertEquals(note.getId(), already.get().getId());
	}

	@Test
	public void getAllNoteTest() {
		List<Note> note1 = null;
		String emailId = "pratikshatamadalge21@gmail.com";
		Optional<Note> already = Optional.of(note);
		when(noteRepository.findByEmailId(emailId)).thenReturn(note1);
		assertEquals(note.getEmailId(), already.get().getEmailId());
	}

	@Test
	public void isPinnedTest() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsPinned(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsPinned())
			note.setIsPinned(false);
		else
			note.setIsPinned(true);
	}

	@Test
	public void isTrashedTest() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsTrashed(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsTrashed())
			note.setIsTrashed(false);
		else
			note.setIsTrashed(false);
	}

	@Test
	public void isAechievedTest() {
		String noteId = "5dba69b03f43761e31622cbe";
		String emailId = "shelkeva@gmail.com";
		note.setIsArcheived(false);
		when(noteRepository.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		if (note.getIsArcheived())
			note.setIsArcheived(false);
		else
			note.setIsArcheived(false);
	}
}
