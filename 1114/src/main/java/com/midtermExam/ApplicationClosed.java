package com.midtermExam;

import com.midtermExam.domain.Application;
import com.midtermExam.domain.Lesson;
import com.midtermExam.domain.Status;
import com.midtermExam.service.ApplicationService;
import com.midtermExam.service.LessonService;
import com.midtermExam.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
public class ApplicationClosed {

    private final ApplicationService applicationService;
    private final LessonService lessonService;

    @Scheduled(cron = "1 0 0 * * ?")
    public void scheduling(){

        LocalDate yesterday = LocalDate.now().minusDays(1L);

        List<Long> closedLessonIdList = new ArrayList<>();

        List<Lesson> lessonList = lessonService.findAll();
        for (Lesson lesson : lessonList) {
            LocalDate endDate = lesson.getRecruitmentPeriod().getEndDate();
            if (yesterday.isEqual(endDate)) {
                closedLessonIdList.add(lesson.getId());
            }
        }

        for (Long closedLessonId : closedLessonIdList) {
            List<Application> failApplicationList = applicationService.closed(closedLessonId);
            for (Application failApplication : failApplicationList) {
                if (failApplication.getStatus().equals(Status.HOLD)) {
                    applicationService.fail(failApplication.getId());
                }
            }

        }
    }
}



