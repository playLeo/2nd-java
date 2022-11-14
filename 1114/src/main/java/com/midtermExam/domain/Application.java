package com.midtermExam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Application {

    public Application() {
    }

    public Application(Member member, Lesson lesson) {
        this.member = member;
        this.lesson = lesson;
    }

    @Id
    @Column(name = "application_id")
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String motivation;
    private String course;
    //상태값 4개

    @Enumerated
    private Status status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}
