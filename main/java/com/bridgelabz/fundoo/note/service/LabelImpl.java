package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

/**
 * Purpose: Label service implementation to provode service to the label
 * controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Service
public class LabelImpl implements LabelService {
	@Autowired
	LabelRepository labelRepository;

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	Environment environment;

	/**
	 * Purpose:To create label
	 * 
	 * @param token
	 * @param labelDTO
	 * @return
	 */
	@Override
	public Response createLabel(String token, LabelDTO labelDTO) {
		String emailId = tokenUtil.decodeToken(token);
		Label label = new ModelMapper().map(labelDTO, Label.class);
		label.setEmailId(emailId);
		label.setLabelName(labelDTO.getLabelName());
		labelRepository.save(label);

		return new Response(200, environment.getProperty("success"), null);
	}

	/**
	 * Purpose:To fetch all the label of a particular user
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<Label> getLabel(String token) {
		String emailId = tokenUtil.decodeToken(token);
		List<Label> label = labelRepository.findByEmailId(emailId);
		return label;
	}

	/**
	 * Purpose:To update a label of particular user
	 * 
	 * @param token
	 * @param labelId
	 * @param labelName
	 * @return
	 */
	@Override
	public Response updateLabel(String token, String labelId, String labelName) {
		String emailId = tokenUtil.decodeToken(token);

		Label label = labelRepository.findById(labelId).get();

		label.setEmailId(emailId);
		label.setLabelName(labelName);

		labelRepository.save(label);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Purpose:To delete a label of particular user
	 * 
	 * @param token
	 * @param labelId
	 * @return
	 */
	@Override
	public Response deleteLabel(String token, String labelId) {
		// String emailId = tokenUtil.decodeToken(token);
		labelRepository.deleteById(labelId);
		return new Response(200, environment.getProperty("delete"), null);
	}
}
