package com.bridgelabz.fundoo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bridgelabz.fundoo.note.controller.NoteController;
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
	Note note;

	@Mock
	NoteController notecontroller;

	@Mock
	NoteRepository noteRepository;

	@Test
	public void createNote() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "Junit");
		jsonObject.put("description", "Junit5");
		String json = jsonObject.toString();
		System.out.println("json string " + json);

		mockmvc.perform(post("/note/createNote").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title", Matchers.is("Junit")))
				.andExpect(jsonPath("$.description", Matchers.is("Junit5")));
	}
}
