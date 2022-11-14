package com.starters.starters_midterm.repository;


import com.starters.starters_midterm.model.Lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
