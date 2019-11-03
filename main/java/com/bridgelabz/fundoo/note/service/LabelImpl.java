package com.bridgelabz.fundoo.note.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.utility.StaticReference;

/**
 * Purpose: Label service implementation to provode service to the label
 * controller
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Service
@PropertySource("classpath:message.properties")
public class LabelImpl implements LabelService {

	@Autowired
	private LabelRepository labelRepository;

	/**
	 * Purpose:To create label
	 * 
	 * @param token
	 * @param labelDTO
	 * @return
	 */
	@Override
	public Response createLabel(String emailId, LabelDTO labelDTO) {
		Label label = new ModelMapper().map(labelDTO, Label.class);
		label.setEmailId(emailId);
		label.setLabelName(labelDTO.getLabelName());
		labelRepository.save(label);

		return new Response(HttpStatus.OK, StaticReference.SUCCESSFULL, null);
	}

	/**
	 * Purpose:To fetch all the label of a particular user
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<Label> getLabel(String emailId) {
		return labelRepository.findByEmailId(emailId);
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
	public Response updateLabel(String emailId, String labelId, String labelName) {
		Optional<Label> label = labelRepository.findById(labelId);
		Label label1;
		if (label.isPresent()) {
			label1 = label.get();
			label1.setEmailId(emailId);
			label1.setLabelName(labelName);
			labelRepository.save(label1);
			return new Response(HttpStatus.OK, StaticReference.UPDATE, null);
		}
		return new Response(HttpStatus.BAD_REQUEST, StaticReference.UPDATE, null);
	}

	/**
	 * Purpose:To delete a label of particular user
	 * 
	 * @param token
	 * @param labelId
	 * @return
	 */
	@Override
	public Response deleteLabel(String emailId, String labelId) {
		labelRepository.deleteById(labelId);
		return new Response(HttpStatus.OK, StaticReference.DELETE, null);
	}
}
