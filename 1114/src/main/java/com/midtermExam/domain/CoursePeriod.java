package com.midtermExam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class CoursePeriod {

    @Id
    @Column(name ="coursePeriod_id")
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
}
