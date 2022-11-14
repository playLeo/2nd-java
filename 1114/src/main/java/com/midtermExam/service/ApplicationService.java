package com.midtermExam.service;

import com.midtermExam.domain.Application;
import com.midtermExam.domain.ApplicationDto;
import com.midtermExam.domain.Status;
import com.midtermExam.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Transactional
    public void modifyApplication(Long applicationId, ApplicationDto applicationDto) throws Exception {
        Optional<Application> optApplication = applicationRepository.findById(applicationId);
        Application application = optApplication.orElseThrow(() -> new Exception("존재하지 않는 지원서입니다."));

        if (application.getStatus().equals(Status.HOLD)) {
            application.setMotivation(applicationDto.getMotivation());
            application.setCourse(applicationDto.getCourse());
            applicationRepository.save(application);
            return;
        }
        new Exception("지원중인 지원서만 수정 가능");
    }

    @Transactional(readOnly = true)
    public List<Application> findAll() {
        List<Application> applicationList = applicationRepository.findAll();
        return applicationList;
    }

    @Transactional(readOnly = true)
    public List<Application> closed(Long closedLessonId) {
        List<Application> closedApplicationList = applicationRepository.findByLesson_id(closedLessonId);
        return closedApplicationList;
    }

    @Transactional
    public void save(Application application) {
        applicationRepository.save(application);
    }

    @Transactional
    public void fail(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).get();
        application.setStatus(Status.FAIL);
    }

//    @Transactional(readOnly = true)
//    public List<Application> notFailList() {
//        applicationRepository.find
//    }
}
