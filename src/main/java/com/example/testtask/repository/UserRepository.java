package com.example.testtask.repository;

import com.example.testtask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}
