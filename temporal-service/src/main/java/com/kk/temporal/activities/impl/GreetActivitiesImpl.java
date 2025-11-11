package com.kk.temporal.activities.impl;

import com.kk.temporal.activities.GreetActivities;
import com.kk.temporal.request.TravelReq;
import io.nexusrpc.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GreetActivitiesImpl implements GreetActivities {
    @Override
    public String greet(String name) {
        return "Hello " + name + "!";
    }

    @Override
    public void travel(TravelReq req) {
        log.info("travel Users {} Desination {}", req.user(), req.destination());
        throw new RuntimeException("travel");
    }

    @Override
    public void cancelTravel(TravelReq req) {
        log.info("🛑  cancel travel Users {} Desination {}", req.user(), req.destination());
    }

    @Override
    public void bookHotel(TravelReq req) {
        log.info("bookHotel Users {} Desination {}", req.user(), req.destination());
//        throw new RuntimeException("bookHotel GreetActivitiesImpl");
    }

    @Override
    public void cancelBookHotel(TravelReq req) {
        log.info("🛑 cancelBookHotel Users {} Desination {}", req.user(), req.destination());
    }

    @Override
    public void cancelBook(String user) {
        log.info("🛑 cancelBook Users {}", user);
    }

    @Override
    public void confirmBook(String user) {
        log.info("confirmBook Users {}", user);
    }
}
