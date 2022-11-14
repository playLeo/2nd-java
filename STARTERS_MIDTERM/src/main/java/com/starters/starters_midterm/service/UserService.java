package com.starters.starters_midterm.service;

import com.starters.starters_midterm.model.User.User;
import com.starters.starters_midterm.model.User.UserRole;
import com.starters.starters_midterm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    이메일 중복확인 추가
    패스워드 암호화 추가
     */
    public void createUser(String email, String password, String name, String phoneNum, UserRole role) {

        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNum(phoneNum)
                .role(role)
                .build();

        userRepository.save(user);

    }
}
