package com.starters.starters_midterm.model.Application;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApplicationDto {

    private String motivation;

    private String futureCareer;

    private String userName;

    private String userEmail;

    private String userPhoneNum;

    public ApplicationDto(Application application){

        this.motivation = application.getMotivation();
        this.futureCareer = application.getFutureCareer();
        this.userName = application.getUser().getName();
        this.userEmail = application.getUser().getEmail();
        this.userPhoneNum = application.getUser().getPhoneNum();

    }
}
