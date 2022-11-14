package com.midtermExam.domain;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ApplicationDto {
    private String motivation;
    private String course;

    public ApplicationDto(String motivation, String course) {
        this.motivation = motivation;
        this.course = course;
    }
}
