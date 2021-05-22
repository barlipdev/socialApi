package com.socialapp.api.repository;

import com.socialapp.api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findById(String id);
    Optional<User> findUserByEmailAndPassword(String email,String password);
    void deleteById(String id);
}
