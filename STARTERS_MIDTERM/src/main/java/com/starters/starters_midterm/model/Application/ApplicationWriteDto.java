package com.starters.starters_midterm.model.Application;

import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ApplicationWriteDto {

    private String motivation;

    private Long lessonId;

    private String futureCareer;

    private ApplicationStatus status;

    public Application toEntity(Lesson lesson, User user) {
        return Application.builder()
                .lesson(lesson)
                .status(status)
                .futureCareer(futureCareer)
                .motivation(motivation)
                .user(user)
                .build();
    }

}
