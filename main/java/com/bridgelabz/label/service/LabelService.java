package com.bridgelabz.label.service;

import java.util.List;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.label.dto.LabelDTO;
import com.bridgelabz.label.model.Label;

/**
 * Purpose:Label service interface to for label service implementation
 * 
 * @author Pratiksha Tamadalge
 *
 */
public interface LabelService {

	public Response createLabel(String token, LabelDTO labelDTO);

	public List<Label> getLabel(String token);

	public Response updateLabel(String token, String labelId, String labelName);

	public Response deleteLabel(String token, String labelId);
}
