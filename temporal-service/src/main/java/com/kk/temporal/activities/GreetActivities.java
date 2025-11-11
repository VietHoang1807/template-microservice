package com.kk.temporal.activities;


import com.kk.temporal.request.TravelReq;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GreetActivities {

    @ActivityMethod
    String greet(String name);
    @ActivityMethod
    void travel(TravelReq req);
    @ActivityMethod
    void cancelTravel(TravelReq req);
    @ActivityMethod
    void bookHotel(TravelReq req);
    @ActivityMethod
    void cancelBookHotel(TravelReq req);
    @ActivityMethod
    void cancelBook(String user);
    @ActivityMethod
    void confirmBook(String user);
}
