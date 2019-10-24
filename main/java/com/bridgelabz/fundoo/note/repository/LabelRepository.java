package com.bridgelabz.fundoo.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Label;

/**
 * Purpose: Repository to pass a query through mongorepository with mapping of
 * Label model
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Repository
public interface LabelRepository extends MongoRepository<Label, String> {
	List<Label> findByEmailId(String emailId);
}
