package com.starters.starters_midterm.model.Application;

import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motivation;

    private String futureCareer;

    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Builder
    public Application(String motivation, String futureCareer, ApplicationStatus status, Lesson lesson, User user) {

        this.motivation = motivation;
        this.futureCareer = futureCareer;
        this.status = status;
        this.lesson = lesson;
        this.user = user;

    }

}
