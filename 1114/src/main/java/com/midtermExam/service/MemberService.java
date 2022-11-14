package com.midtermExam.service;

import com.midtermExam.domain.*;
import com.midtermExam.repository.ApplicationRepository;
import com.midtermExam.repository.LessonRepository;
import com.midtermExam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApplicationRepository applicationRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public void makeApplication(String email, Long lessonId) throws Exception {
        Optional<Member> optMember = memberRepository.findById(email);
        Optional<Lesson> optLesson = lessonRepository.findById(lessonId);

        Member member = optMember.orElseThrow(() -> new Exception("존재하지 않는 이메일입니다."));
        Lesson lesson = optLesson.orElseThrow(() -> new Exception("존재하지 않는 수업입니다."));

        List<Application> applicationList = member.getApplicationList();

        LocalDate nowDate = LocalDate.now();
        RecruitmentPeriod recruitmentPeriod = lesson.getRecruitmentPeriod();

        if (nowDate.isAfter(recruitmentPeriod.getStartDate()) && nowDate.isBefore(recruitmentPeriod.getEndDate())) {
            int notFailApplicationCnt = 0;
            for (Application application : applicationList) {
                if (!application.getStatus().equals(Status.FAIL)) {
                    notFailApplicationCnt++;
                }
            }
            if (notFailApplicationCnt < 5) {
                Application application = new Application(member, lesson);
                if (!applicationList.contains(application)) {
                    applicationList.add(application);
                    member.setApplicationList(applicationList);
                    memberRepository.save(member);
                    return;
                }
                new Exception("해당 수업 중복지원 불가");
            }
            new Exception("지원은 최대 5개");
        }
        new Exception("수업 모집 기간에만 지원서 작성이 가능");
    }


    @Transactional
    public void apply(String email, Long applicationId) throws Exception {
        Optional<Member> optMember = memberRepository.findById(email);
        Optional<Application> optApplication = applicationRepository.findById(applicationId);

        Member member = optMember.orElseThrow(() -> new Exception("존재하지 않는 이메일입니다."));
        Application application = optApplication.orElseThrow(() -> new Exception("존재하지 않는 지원서입니다."));

        List<Application> applicationList = member.getApplicationList();

        if (!member.isApply()) {
            if(applicationList.contains(application)) {
                member.setApply(true);
                memberRepository.save(member);
                application.setStatus(Status.COMPLETE);
                applicationRepository.save(application);
            }

        }

    }

    @Transactional
    public ApplicationInfo findStatus(String id) {
        List<Application> applicationList = memberRepository.findById(id).get().getApplicationList();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        for (Application application : applicationList) {
            if (application.getStatus().equals(Status.HOLD)) {
                applicationInfo.getApplicationMap().put(application.getId(), Status.HOLD);
            }
            if (application.getStatus().equals(Status.COMPLETE)) {
                applicationInfo.getApplicationMap().put(application.getId(), Status.COMPLETE);
            }
        }
        return applicationInfo;
    }


}
