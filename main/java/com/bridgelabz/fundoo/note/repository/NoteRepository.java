package com.bridgelabz.fundoo.note.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;

/**
 * Purpose: Repository to pass a query through mongorepository with mapping of
 * Note model
 * 
 * @author Pratiksha Tamadalge
 *
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	Note findByEmailId(String emailId);
}
