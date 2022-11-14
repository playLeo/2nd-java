package com.starters.starters_midterm.controller;

import com.starters.starters_midterm.model.Application.Application;
import com.starters.starters_midterm.model.Application.ApplicationWriteDto;
import com.starters.starters_midterm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ApplicationController {

    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /*
    code가 1이면 반영완료
    code가 2이면 지원서 초과
    3은 모집기간 안맞음
    4는 지원완료이거나 패스한게 존재
     */
    @PostMapping("/user/application")
    public ResponseEntity<String> createApplication(@RequestBody ApplicationWriteDto applicationWriteDto, HttpSession session) {

        int code = applicationService.applyLesson((Long) session.getAttribute("userId"),applicationWriteDto);

        if(code == 1) {
            return new ResponseEntity<String>("지원서 작성", HttpStatus.OK);
        } else if (code == 2) {
            return new ResponseEntity<String>("지원서 작성 실패 지원서 갯수 초과", HttpStatus.BAD_REQUEST);
        } else if (code == 3) {
            return new ResponseEntity<String>("지원서 작성 실패 모집기간이 아님", HttpStatus.BAD_REQUEST);
        } else if (code == 4) {
            return new ResponseEntity<String>("지원서 작성 실패 지원 완료이거나 패스한게 존재", HttpStatus.BAD_REQUEST);
        } else if (code == 5) {
            return new ResponseEntity<String>("지원서 작성 실패 글자수 초과", HttpStatus.BAD_REQUEST);
        } else if (code == 6) {
            return new ResponseEntity<String>("지원서 작성 실패 잘못된 status (pass이거나 fail인 경우)", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PutMapping("/user/application")
//    public ResponseEntity<String> editApplication()
    @ResponseBody
    @GetMapping("/admin/application/{lessonId}")
    public ResponseEntity<List<Application>> getApplications(@PathVariable(name = "lessonId") Long lessonId) {

        return new ResponseEntity<List<Application>>(applicationService.getApplicationList(lessonId), HttpStatus.OK);
    }

}
