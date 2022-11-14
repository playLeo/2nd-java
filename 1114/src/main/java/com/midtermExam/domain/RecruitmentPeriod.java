package com.midtermExam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class RecruitmentPeriod {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
}
