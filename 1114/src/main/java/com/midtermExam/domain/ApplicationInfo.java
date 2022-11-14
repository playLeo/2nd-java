package com.midtermExam.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApplicationInfo {
    Map<Long, Status> applicationMap = new HashMap<>();
}
