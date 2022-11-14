package com.midtermExam.repository;

import com.midtermExam.domain.Application;
import com.midtermExam.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    public List<Application> findByLesson_id(Long lesson_id);
}
