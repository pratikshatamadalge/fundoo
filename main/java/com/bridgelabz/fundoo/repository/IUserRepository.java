package com.bridgelabz.fundoo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

	List<User> findUserByEmailIdAndPassword(String emailId, String password);

	void deleteByEmailId(String emailId);

	User findByEmailId(String oldEmailId);
}
