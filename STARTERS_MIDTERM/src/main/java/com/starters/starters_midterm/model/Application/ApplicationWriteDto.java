package com.starters.starters_midterm.model.Application;

import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import lombok.Builder;
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

    @Builder
    public ApplicationWriteDto(String motivation, Long lessonId, String futureCareer, ApplicationStatus status) {

        this.motivation = motivation;
        this.lessonId = lessonId;
        this.futureCareer = futureCareer;
        this.status = status;

    }

}
