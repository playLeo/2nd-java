package com.midtermExam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Lesson {

    @Id
    @Column(name = "lesson_id")
    private Long id;

    private String name;

    @OneToOne
    private RecruitmentPeriod recruitmentPeriod;

    @OneToOne
    private CoursePeriod coursePeriod;
}
