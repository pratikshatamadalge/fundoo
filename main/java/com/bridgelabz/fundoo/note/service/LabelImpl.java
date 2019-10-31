package com.bridgelabz.fundoo.note.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.repository.LabelRepository;

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

	@Autowired
	private Environment environment;

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

		return new Response(200, environment.getProperty("success"), null);
	}

	/**
	 * Purpose:To fetch all the label of a particular user
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<Label> getLabel(String emailId) {
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
	public Response updateLabel(String emailId, String labelId, String labelName) {

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
	public Response deleteLabel(String emailId, String labelId) {
		// String emailId = tokenUtil.decodeToken(token);
		labelRepository.deleteById(labelId);
		return new Response(200, environment.getProperty("delete"), null);
	}
}
