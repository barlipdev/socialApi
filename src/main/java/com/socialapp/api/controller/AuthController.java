package com.socialapp.api.controller;

import com.socialapp.api.domain.User;
import com.socialapp.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.login(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return authService.register(user);
    }

}
