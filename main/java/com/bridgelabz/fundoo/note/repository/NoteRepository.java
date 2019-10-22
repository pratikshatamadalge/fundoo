package com.bridgelabz.fundoo.note.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

}
