package com.starters.starters_midterm.repository;

import com.starters.starters_midterm.model.Application.Application;
import com.starters.starters_midterm.model.Application.ApplicationStatus;
import com.starters.starters_midterm.model.Lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    public int countApplicationByStatusIsNot(ApplicationStatus status);

    public boolean existsApplicationByLessonAndStatusIsNot(Lesson lesson, ApplicationStatus status);

    public List<Application> findAllByLesson(Lesson lesson);

}
