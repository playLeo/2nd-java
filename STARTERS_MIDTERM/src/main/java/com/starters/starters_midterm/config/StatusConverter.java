package com.starters.starters_midterm.config;

import com.starters.starters_midterm.model.Application.ApplicationStatus;

public class StatusConverter {

    public static ApplicationStatus convert(int status) {

        if (status == 0) {
            return ApplicationStatus.APPLYING;
        } else if (status == 1) {
            return ApplicationStatus.APPLICATION_COMPLETED;
        } else if (status == 2) {
            return ApplicationStatus.PASS;
        }
        return ApplicationStatus.FAIL;
    }
}
