package com.starters.starters_midterm.service;

import com.starters.starters_midterm.model.Application.*;
import com.starters.starters_midterm.model.Lesson.Lesson;
import com.starters.starters_midterm.model.User.User;
import com.starters.starters_midterm.repository.ApplicationRepository;
import com.starters.starters_midterm.repository.LessonRepository;
import com.starters.starters_midterm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void setFailApplicationSchedule() {

        ArrayList<ApplicationStatus> statusList = new ArrayList<>();
        statusList.add(ApplicationStatus.APPLICATION_COMPLETED);
        statusList.add(ApplicationStatus.APPLYING);

        List<Application> applicationList = applicationRepository.findApplicationsByStatusIn(statusList);
        for (int i=0;i<applicationList.size();i++) {
            Application application = applicationList.get(i);
            if(application.getLesson().getLessonEnd().compareTo(LocalDate.now()) < 0) {
                application.setStatus(ApplicationStatus.FAIL);
                applicationList.set(i, application);
            }

        }

        applicationRepository.saveAll(applicationList);
    }

    @Transactional
    public int deleteApplication(Long applicationId, Long userId) {

        Application application = applicationRepository.findById(applicationId).get();
        User user = userRepository.findById(userId).get();

        if (application.getUser().equals(user) == false || application.getStatus() != ApplicationStatus.APPLYING) {
            return 0;
        }
        else {
            applicationRepository.delete(application);
        }
        return 1;
    }

    /*
    예외처리
    1 -> status 잘못됨
    글자 수 처과
    */
    public synchronized int editApplication(ApplicationEditDto applicationEditDto) {

        Application application = applicationRepository.findById(applicationEditDto.getApplicationId()).get();
        if (application.getStatus() != ApplicationStatus.APPLYING) {
            return 1;
        }
        else if (applicationEditDto.getStatus() != ApplicationStatus.APPLICATION_COMPLETED || applicationEditDto.getStatus() != ApplicationStatus.APPLYING) {
            return 2;
        }
        // 글자수 예외처리
        if (applicationEditDto.getFutureCareer().length() > 253 || applicationEditDto.getMotivation().length() > 253) {
            return 3;
        }
        // 지원 완료 불가 시간 겹쳐서
        if (applicationEditDto.getStatus() == ApplicationStatus.APPLICATION_COMPLETED) {
            Lesson lesson = application.getLesson();
            if (lessonRepository.countAllByLessonStartAndLessonEnd(lesson.getLessonStart(),lesson.getLessonEnd()) >= 3) {
                return 8;
            }

            if (lessonRepository.countAllByRecruitmentStartAndRecruitmentEnd(lesson.getRecruitmentStart(),lesson.getRecruitmentEnd()) >= 3) {
                return 9;
            }
        }

        application = applicationEditDto.toEntity(application);
        applicationRepository.save(application);

        return 0;
    }

    /*
    예외처리
     */
    public synchronized int applyLesson(Long userId, ApplicationWriteDto applicationWriteDto) {

        // 초과이면 신청 불가
        Lesson lesson = lessonRepository.findById(applicationWriteDto.getLessonId()).get();
        int curCount = applicationRepository.countApplicationsByLessonAndStatusIs(lesson, ApplicationStatus.PASS);
        if (lesson.getMaxStudent() >= curCount) {
            return 7;
        }
        // 글자수 예외처리
        if (applicationWriteDto.getFutureCareer().length() > 253 || applicationWriteDto.getMotivation().length() > 253) {
            return 5;
        }

        if (applicationWriteDto.getStatus() == ApplicationStatus.PASS || applicationWriteDto.getStatus() == ApplicationStatus.FAIL) {
            return 6;
        }

        if (lessonRepository.countAllByLessonStartAndLessonEnd(lesson.getLessonStart(),lesson.getLessonEnd()) >= 3) {
            return 8;
        }

        if (lessonRepository.countAllByRecruitmentStartAndRecruitmentEnd(lesson.getRecruitmentStart(),lesson.getRecruitmentEnd()) >= 3) {
            return 9;
        }

        // 불합격이 아닌 지원서 5개 초과할경우
        if (applicationRepository.countApplicationByStatusIsNot(ApplicationStatus.FAIL) >= MAX_COUNT) {
            return 2;
        }
        // 모집기간이 현재랑 안맞으면 코드 3
        // 시작 시간보다는 크거나 같고 끝나는 시간보다는 작거나 같아야함
        if (LocalDate.now().compareTo(lesson.getLessonStart())>=0 && LocalDate.now().compareTo(lesson.getLessonEnd())<=0) {
            return 3;
        }

        // 지원 완료이거나 이미 붙은게 존재함
        if (applicationRepository.existsApplicationByLessonAndStatusIsNot(lesson, ApplicationStatus.APPLYING) && applicationRepository.existsApplicationByLessonAndStatusIsNot(lesson, ApplicationStatus.FAIL)) {
            return 4;
        }
        Application application = applicationWriteDto.toEntity(lessonRepository.findById(applicationWriteDto.getLessonId()).get(), userRepository.findById(userId).get());
        applicationRepository.save(application);

        return 1;
    }

    @Transactional
    public List<ApplicationDto> getApplicationList(Long lessonId) {

        List<Application> applicationList = applicationRepository.findApplicationsByLesson(lessonRepository.findById(lessonId).get());
        List<ApplicationDto> dtoList = applicationList.stream().map(ApplicationDto::new).collect(Collectors.toList());

        return dtoList;
    }

    @Transactional
    public List<ApplicationDto> getMyApplicationList(Long userId) {

        List<Application> applicationList = applicationRepository.findApplicationsByUser(userRepository.findById(userId).get());
        List<ApplicationDto> dtoList = applicationList.stream().map(ApplicationDto::new).collect(Collectors.toList());

        return dtoList;
    }

    public synchronized boolean editApplicationStatus(Long applicationId, ApplicationStatus status) {

        if (status == ApplicationStatus.APPLYING || status == ApplicationStatus.APPLICATION_COMPLETED) {
            return false;
        }
        Application application = applicationRepository.findById(applicationId).get();
        if (application.getLesson().getMaxStudent() <= applicationRepository.countApplicationsByLessonAndStatusIs(application.getLesson(), ApplicationStatus.PASS)) {
            return false;
        }
        application.setStatus(status);
        applicationRepository.save(application);

        return true;
    }

    public ApplicationDto getApplicationsByUser(String userName, Long lessonId) {

        User user = userRepository.findUserByName(userName);
        Lesson lesson = lessonRepository.findById(lessonId).get();
        return new ApplicationDto(applicationRepository.findApplicationByUserAndLesson(user, lesson));

    }

}
