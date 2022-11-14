package com.starters.starters_midterm.model.User;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNum;

    private UserRole role;

    @Builder
    public User(String email, String password, String name, String phoneNum, UserRole role) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.role = role;

    }

}
