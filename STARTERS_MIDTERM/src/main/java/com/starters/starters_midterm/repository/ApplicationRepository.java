package com.starters.starters_midterm.repository;

import com.starters.starters_midterm.model.Application.Application;
import com.starters.starters_midterm.model.Application.ApplicationStatus;
import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    public int countApplicationByStatusIsNot(ApplicationStatus status);

    public boolean existsApplicationByLessonAndStatusIsNot(Lesson lesson, ApplicationStatus status);

    public List<Application> findApplicationsByLesson(Lesson lesson);

    public List<Application> findApplicationsByUser(User user);

    public int countApplicationsByLessonAndStatusIs(Lesson lesson, ApplicationStatus status);

    public List<Application> findApplicationsByStatusIn(List<ApplicationStatus> statusList);

    public Application findApplicationByUserAndLesson(User user, Lesson lesson);

}
