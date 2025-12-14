package com.springsecurity.spring_boot_security_jwt.service;

import com.springsecurity.spring_boot_security_jwt.dto.request.AuthRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.GetUserDetailRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.RegisterUserRequest;
import com.springsecurity.spring_boot_security_jwt.dto.response.ApiResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.AuthResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.UserDto;

import jakarta.validation.Valid;

public interface AuthService {

    ApiResponse<UserDto> registerUser(RegisterUserRequest request);

    ApiResponse<AuthResponse> loginUser(AuthRequest request);

    ApiResponse<UserDto> getUserDetails(@Valid GetUserDetailRequest request);
}
