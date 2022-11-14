package com.starters.starters_midterm;

import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import com.starters.starters_midterm.model.User.UserRole;
import com.starters.starters_midterm.repository.LessonRepository;
import com.starters.starters_midterm.repository.UserRepository;
import com.starters.starters_midterm.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void 수업_만들기() {
        Lesson lesson = Lesson.builder()
                .maxStudent(2)
                .lessonStart(LocalDate.parse("2022-12-01"))
                .lessonEnd(LocalDate.parse("2022-12-31"))
                .recruitmentStart(LocalDate.parse("2022-11-13"))
                .recruitmentEnd(LocalDate.parse("2022-11-20"))
                .name("class")
                .build();
        lessonRepository.save(lesson);
    }



    @Test
    public void 유저_생성하기() {

        User normal = User.builder()
                .email("benny1020@naver.com")
                .role(UserRole.NORMAL)
                .phoneNum("010-7138-2184")
                .password("12341234")
                .name("유경민")
                .build();

        userRepository.save(normal);

        // admin
        User admin = User.builder()
                .email("201724512@pusan.ac.kr")
                .role(UserRole.ADMIN)
                .phoneNum("010-3138-2184")
                .password("12341234")
                .name("어드민")
                .build();

        userRepository.save(admin);
    }
}
