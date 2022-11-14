package com.starters.starters_midterm.model.Lesson;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int maxStudent;

    private LocalDate recruitmentStart;

    private LocalDate recruitmentEnd;

    private LocalDate lessonStart;

    private LocalDate lessonEnd;

    @Builder
    public Lesson(String name, LocalDate recruitmentStart,
                       LocalDate recruitmentEnd,
                       LocalDate lessonStart,
                       LocalDate lessonEnd, int maxStudent
                       ) {
        this.name = name;
        this.recruitmentStart = recruitmentStart;
        this.recruitmentEnd = recruitmentEnd;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.maxStudent = maxStudent;

    }

}
