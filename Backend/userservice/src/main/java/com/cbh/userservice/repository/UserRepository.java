package com.cbh.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cbh.userservice.models.User;

@Repository
public interface UserRepository  extends MongoRepository<User, String>{

	Optional<User> findByEmail(String email);

}
