package com.bridgelabz.fundoo.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Label;

@Repository
public interface LabelRepository extends MongoRepository<Label, String> {
	List<Label> findByEmailId(String emailId);
}
