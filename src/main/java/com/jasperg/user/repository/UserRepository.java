package com.jasperg.user.repository;

import com.jasperg.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByCode(String code);
    List<User> findAll();
    List<User> findUsersByEmailContaining(String email);
    List<User> findUsersByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
