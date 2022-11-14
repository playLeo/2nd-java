package com.midtermExam.controller;

import com.midtermExam.domain.ApplicationDto;
import com.midtermExam.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public void modifyApplication(Long applicationId, ApplicationDto applicationDto) throws Exception {
        applicationService.modifyApplication(applicationId, applicationDto);
    }
}
