package com.kk.temporal.activities.impl;

import com.kk.temporal.activities.ExternalActivities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalActivitiesImpl implements ExternalActivities {
    @Override
    public void myActivityFunc(String user) {
        log.info("External Activities user {}",user);
    }
}
