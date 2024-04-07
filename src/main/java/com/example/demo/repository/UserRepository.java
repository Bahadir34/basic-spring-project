package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameEquals(String userName);

    @Query(value = "SELECT email FROM User")
    List<String> getAllEmails();

    @Query(value = "SELECT userName FROM User")
    List<String> getAllUserNames();
}
