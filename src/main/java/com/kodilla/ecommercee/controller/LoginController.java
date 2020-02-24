package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.login.LoginDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public LoginDto login() {
        return new LoginDto(true, 12345L);
    }
}
