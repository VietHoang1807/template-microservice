package com.kk.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ExternalActivities {

    @ActivityMethod(name = "myActivityFunc")
    void myActivityFunc(String user);
}
