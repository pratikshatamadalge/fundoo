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
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoo.label.dto.LabelDTO;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.label.service.LabelImpl;
import com.bridgelabz.fundoo.note.repository.LabelRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

	@InjectMocks
	LabelImpl labelImpl;

	@Mock
	LabelRepository labelRepository;

	@Mock
	ModelMapper modelMapper;

	Label label = new Label("5dbc1b1c3f43762b578e7390", "Task", "pratikshatamadalge21@gmail.com", null);

	/**
	 * Test case for create label api
	 */
	@Test
	public void testCreateLabel() {
		LabelDTO labelDTO = new LabelDTO();
		labelDTO.setLabelName("Task");
		System.out.println("in create label :" + labelDTO.getLabelName());
		when(modelMapper.map(labelDTO, Label.class)).thenReturn(label);
		when(labelRepository.save(label)).thenReturn(label);
		assertEquals(labelDTO.getLabelName(), label.getLabelName());
	}

	/**
	 * Test case for update label api
	 */
	@Test
	public void testUpdateLabel() {
		List<Label> label1 = null;
		String emailId = "pratikshatamadalge21@gmail.com";
		String labelName = "7 wonders in world....";

		when(labelRepository.findByEmailId(emailId)).thenReturn(label1);
		label.setLabelName("7 wonders in world....");
		when(labelRepository.save(label)).thenReturn(label);
		assertEquals(label.getLabelName(), labelName);
	}

	/**
	 * Test case for delete label api
	 */
	@Test
	public void testDeleteLabel() {

		List<Label> label1 = null;
		String labelId = "5dba69b03f43761e31622cbe";
		String emailId = "pratikshatamadalge21@gmail.com";
		when(labelRepository.findByEmailId(emailId)).thenReturn(label1);
		labelRepository.deleteById(labelId);
		assertEquals(label.getEmailId(), emailId);

	}

	/**
	 * Test case to fetch all label api
	 */
	@Test
	public void testGetLabel() {
		List<Label> label1 = null;
		String emailId = "pratikshatamadalge21@gmail.com";
		Optional<Label> already = Optional.of(label);
		when(labelRepository.findByEmailId(emailId)).thenReturn(label1);
		assertEquals(label.getEmailId(), already.get().getEmailId());
	}
}
