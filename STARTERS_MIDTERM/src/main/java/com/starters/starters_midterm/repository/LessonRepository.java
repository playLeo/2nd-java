package com.starters.starters_midterm.repository;


import com.starters.starters_midterm.model.Lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    public int countAllByRecruitmentStartAndRecruitmentEnd(LocalDate recruitmentStart, LocalDate recruitmentEnd);

    public int countAllByLessonStartAndLessonEnd(LocalDate lessonStart, LocalDate lessonEnd);

}
