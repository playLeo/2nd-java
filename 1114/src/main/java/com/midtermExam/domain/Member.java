package com.midtermExam.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @Column(name = "member_id")
    private String email;

    private String password;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private Classification classification; // ENUM 타입이 좋을듯
    private String phoneNumber;
    private boolean apply;

    @OneToMany(mappedBy = "member")
    private List<Application> applicationList = new ArrayList<>();

}

