package com.example.graduationcheck.repository;

import com.example.graduationcheck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
