package com.springsecurity.spring_boot_security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.spring_boot_security_jwt.dto.request.AuthRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.GetUserDetailRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.RegisterUserRequest;
import com.springsecurity.spring_boot_security_jwt.dto.response.ApiResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.AuthResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.UserDto;
import com.springsecurity.spring_boot_security_jwt.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String home() {
        return "Application is running!";
    }

    @PostMapping("/signUp")
    private ApiResponse<UserDto> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/signIn")
    private ApiResponse<AuthResponse> loginUser(@RequestBody @Valid AuthRequest request) {
        return authService.loginUser(request);
    }

    @PostMapping("/getUserDetails")
    private ApiResponse<UserDto> getUserDetails(@RequestBody @Valid GetUserDetailRequest request) {
        return authService.getUserDetails(request);
    }
}
