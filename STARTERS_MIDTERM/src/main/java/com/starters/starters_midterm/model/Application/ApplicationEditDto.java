package com.starters.starters_midterm.model.Application;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ApplicationEditDto {

    private String motivation;

    private String futureCareer;

    private ApplicationStatus status;

    private Long applicationId;

    public Application toEntity(Application application) {

        application.setMotivation(motivation);
        application.setFutureCareer(futureCareer);
        application.setStatus(status);

        return application;
    }
}
