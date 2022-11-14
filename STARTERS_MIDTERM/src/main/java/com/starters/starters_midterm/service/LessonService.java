package com.starters.starters_midterm.service;

import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class LessonService {

    private LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public void createLesson(String name, LocalDate recruitmentStart, LocalDate recruitmentEnd,
                             LocalDate lessonStart, LocalDate lessonEnd, int maxStudent
                             ) {
        Lesson lesson = Lesson.builder()
                .name(name)
                .recruitmentStart(recruitmentStart)
                .recruitmentEnd(recruitmentEnd)
                .lessonStart(lessonStart)
                .lessonEnd(lessonEnd)
                .maxStudent(maxStudent)
                .build();

        lessonRepository.save(lesson);

    }

}
