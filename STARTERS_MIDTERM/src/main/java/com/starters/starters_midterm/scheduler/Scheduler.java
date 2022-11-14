package com.starters.starters_midterm.scheduler;

import com.starters.starters_midterm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {

    private ApplicationService applicationService;

    @Autowired
    public Scheduler(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Scheduled(cron = "0 0 24 * * *")
    public void manageApplication() {

        applicationService.setFailApplicationSchedule();
    }

}
