package com.midtermExam.controller;

import com.midtermExam.domain.ApplicationInfo;
import com.midtermExam.domain.Member;
import com.midtermExam.domain.Status;
import com.midtermExam.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final MemberService memberService;


    @GetMapping("/admin/{memberId}")
    public Map<Long, Status> memberInfo(@PathVariable("memberId") String email) {
        ApplicationInfo applicationInfo = memberService.findStatus(email);
        return applicationInfo.getApplicationMap();
    }

}
