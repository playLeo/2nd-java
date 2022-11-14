package com.starters.starters_midterm.controller;

import com.starters.starters_midterm.config.StatusConverter;
import com.starters.starters_midterm.model.Application.ApplicationDto;
import com.starters.starters_midterm.model.Application.ApplicationEditDto;
import com.starters.starters_midterm.model.Application.ApplicationStatus;
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

    @DeleteMapping("/user/application/{applicationId}")
    public ResponseEntity<String> deleteApplication(@PathVariable(name = "applicationId") Long applicationId, HttpSession session) {

        int code = applicationService.deleteApplication(applicationId, (Long) session.getAttribute("userId"));
        if (code == 0) {
            return new ResponseEntity<String>("본인 소유의 지원서가 아니거나 지원중인 지원서가 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("삭제하였습니다.", HttpStatus.OK);
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
            return new ResponseEntity<String>("지원서 작성 완료", HttpStatus.OK);
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
        } else if (code == 7) {
            return new ResponseEntity<String>("지원서 작성 실패 잘못된 이미 초과된 교육입니다.", HttpStatus.BAD_REQUEST);
        } else if (code == 8) {
            return new ResponseEntity<String>("해당수업과 겹치는 지원완료 수업이 3개이상입니다.", HttpStatus.BAD_REQUEST);
        } else if (code == 9) {
            return new ResponseEntity<String>("해당수업과 모집기간이 겹치는 지원완료 수업이 3개이상입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    code -> 1 : 이미 지원완료이거나 결정된 지원입니다.
    code -> 2 : 잘못된 status입니다.
    code -> 3 : 글자수가 초과되었습니다.
     */
    @PutMapping("/user/application")
    public ResponseEntity<String> editApplication(@RequestBody ApplicationEditDto applicationEditDto) {

        int code = applicationService.editApplication(applicationEditDto);
        if (code == 0) { // 성공
            return new ResponseEntity<String>("수정완료!", HttpStatus.OK);
        }
        else if (code == 1) {
            return new ResponseEntity<String>("이미 지원완료이거나 결정된 지원입니다.", HttpStatus.BAD_REQUEST);
        }
        else if (code == 2) {
            return new ResponseEntity<String>("잘못된 status 입니다.", HttpStatus.BAD_REQUEST);
        }
        else if (code == 3) {
            return new ResponseEntity<String>("글자 수가 초과되었습니다.", HttpStatus.BAD_REQUEST);
        }
        else if (code == 8) {
            return new ResponseEntity<String>("해당수업과 겹치는 지원완료 수업이 3개이상입니다.", HttpStatus.BAD_REQUEST);
        } else if (code == 9) {
            return new ResponseEntity<String>("해당수업과 모집기간이 겹치는 지원완료 수업이 3개이상입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @GetMapping("/admin/application/{lessonId}")
    public ResponseEntity<List<ApplicationDto>> getApplications(@PathVariable(name = "lessonId") Long lessonId) {

        return new ResponseEntity<List<ApplicationDto>> (applicationService.getApplicationList(lessonId), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/user/application")
    public ResponseEntity<List<ApplicationDto>> getMyApplication(HttpSession session) {

        return new ResponseEntity<List<ApplicationDto>>(applicationService.getMyApplicationList((Long) session.getAttribute("userId")),HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/application/{applicationId}")
    public ResponseEntity<String> editStatusApplication(@RequestParam int status, @PathVariable(name = "applicationId") Long applicationId) {

        ApplicationStatus applicationStatus = StatusConverter.convert(status);
        if (applicationService.editApplicationStatus(applicationId, applicationStatus) == false) {
            return new ResponseEntity<String>("status 수정 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("더이상 학생을 받아들일 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping("/admin/application/search")
    public ResponseEntity<ApplicationDto> getApplications(@RequestParam String userName, @RequestParam Long lessonId) {

        return new ResponseEntity<ApplicationDto> (applicationService.getApplicationsByUser(userName,lessonId), HttpStatus.OK);
    }

}
