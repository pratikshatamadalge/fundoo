package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;

public interface LabelService {

	public String createLabel(String token, LabelDTO labelDTO);

	public List<Label> getLabel(String token);

	public String updateLabel(String token, String labelId, String labelName);

	public String deleteLabel(String token, String labelId);
}
