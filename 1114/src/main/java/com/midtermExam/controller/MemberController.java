package com.midtermExam.controller;

import com.midtermExam.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/application")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PutMapping
    public void addApplication(String email, Long lessonId) throws Exception {
        memberService.makeApplication(email, lessonId);
    }

    @PostMapping
    public void apply(String email, Long applicationId) throws Exception {
        memberService.apply(email, applicationId);
    }
}
