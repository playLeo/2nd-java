package com.starters.starters_midterm.repository;

import com.starters.starters_midterm.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
