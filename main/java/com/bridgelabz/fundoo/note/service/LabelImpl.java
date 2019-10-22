package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.utility.TokenUtil;

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

	@Override
	public String createLabel(String token, LabelDTO labelDTO) {
		String emailId = tokenUtil.decodeToken(token);
		Label label = new ModelMapper().map(labelDTO, Label.class);
		label.setEmailId(emailId);
		label.setLabelName(labelDTO.getLabelName());
		labelRepository.save(label);

		return "label Created Successfully";
	}

	@Override
	public List<Label> getLabel(String token) {
		String emailId = tokenUtil.decodeToken(token);

		List<Label> label = labelRepository.findByEmailId(emailId);
		return label;
	}

	@Override
	public String updateLabel(String token, String labelId, String labelName) {
		String emailId = tokenUtil.decodeToken(token);

		Label label = labelRepository.findById(labelId).get();

		label.setLabelName(labelName);

		labelRepository.save(label);
		return "updated";
	}

	@Override
	public String deleteLabel(String token, String labelId) {
		// String emailId = tokenUtil.decodeToken(token);

		labelRepository.deleteById(labelId);

		return "deleted";
	}

	
}
