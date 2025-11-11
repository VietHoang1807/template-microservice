package com.kk.sso.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kk.sso.model.RecordToken;
import com.kk.sso.request.LoginReq;
import com.kk.sso.request.RegisterReq;
import com.kk.sso.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class Authentication {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginReq login) {
        RecordToken recordToken = userService.login(login);
        log.info("handle login");
        return ResponseEntity.ok().body(recordToken);
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReq req) {
        log.info("handle register");
        RecordToken recordToken = userService.register(req);
        return ResponseEntity.ok().body(recordToken);
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
