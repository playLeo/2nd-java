package com.starters.starters_midterm.service;

import com.starters.starters_midterm.model.Application.Application;
import com.starters.starters_midterm.model.Application.ApplicationEditDto;
import com.starters.starters_midterm.model.Application.ApplicationStatus;
import com.starters.starters_midterm.model.Application.ApplicationWriteDto;
import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.repository.ApplicationRepository;
import com.starters.starters_midterm.repository.LessonRepository;
import com.starters.starters_midterm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    public static int MAX_COUNT = 5;

    public ApplicationRepository applicationRepository;

    public LessonRepository lessonRepository;

    public UserRepository userRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, LessonRepository lessonRepository, UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;

    }

    /*
    예외처리
    status 지원완료 아니면 지원 지원중 처리
    글자 수 처과
    */
    public int editApplication(ApplicationEditDto applicationEditDto) {

        Application application = applicationRepository.findById(applicationEditDto.getApplicationId()).get();
        application = applicationEditDto.toEntity(application);

        applicationRepository.save(application);

        return 0;
    }

    /*
    예외처리
     */
    public int applyLesson(Long userId, ApplicationWriteDto applicationWriteDto) {

        // 글자수 예외처리
        if (applicationWriteDto.getFutureCareer().length() > 253 || applicationWriteDto.getMotivation().length() > 253) {
            return 5;
        }

        if (applicationWriteDto.getStatus() == ApplicationStatus.PASS || applicationWriteDto.getStatus() == ApplicationStatus.FAIL) {
            return 6;
        }

        // 불합격이 아닌 지원서 5개 초과할경우
        if (applicationRepository.countApplicationByStatusIsNot(ApplicationStatus.FAIL) >= MAX_COUNT) {
            return 2;
        }
        // 모집기간이 현재랑 안맞으면 코드 3
        // 시작 시간보다는 크거나 같고 끝나는 시간보다는 작거나 같아야함
        Lesson lesson = lessonRepository.findById(applicationWriteDto.getLessonId()).get();
        if (LocalDate.now().compareTo(lesson.getLessonStart())>=0 && LocalDate.now().compareTo(lesson.getLessonEnd())<=0) {
            return 3;
        }

        // 지원 완료이거나 이미 붙은게 존재함
        if (applicationRepository.existsApplicationByLessonAndStatusIsNot(lesson, ApplicationStatus.APPLICATION_COMPLETED) && applicationRepository.existsApplicationByLessonAndStatusIsNot(lesson, ApplicationStatus.PASS)) {
            return 4;
        }
        Application application = applicationWriteDto.toEntity(lessonRepository.findById(applicationWriteDto.getLessonId()).get(), userRepository.findById(userId).get());
        applicationRepository.save(application);

        return 1;
    }

    public void editApplication(String motivation, String futureCareer, ApplicationStatus applicationStatus, Long applicationId) {

        Application application = applicationRepository.findById(applicationId).get();
        application.setStatus(applicationStatus);
        application.setMotivation(motivation);
        application.setFutureCareer(futureCareer);

        applicationRepository.save(application);

    }

    public List<Application> getApplicationList(Long lessonId) {

        return applicationRepository.findAllByLesson(lessonRepository.findById(lessonId).get());
    }


}
