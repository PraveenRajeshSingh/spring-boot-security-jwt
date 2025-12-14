package com.springsecurity.spring_boot_security_jwt.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.spring_boot_security_jwt.dto.request.AuthRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.GetUserDetailRequest;
import com.springsecurity.spring_boot_security_jwt.dto.request.RegisterUserRequest;
import com.springsecurity.spring_boot_security_jwt.dto.response.ApiResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.AuthResponse;
import com.springsecurity.spring_boot_security_jwt.dto.response.RoleDto;
import com.springsecurity.spring_boot_security_jwt.dto.response.UserDto;
import com.springsecurity.spring_boot_security_jwt.jwt.JwtTokenHelper;
import com.springsecurity.spring_boot_security_jwt.model.Role;
import com.springsecurity.spring_boot_security_jwt.model.User;
import com.springsecurity.spring_boot_security_jwt.repository.RoleRepository;
import com.springsecurity.spring_boot_security_jwt.repository.UserRepository;
import com.springsecurity.spring_boot_security_jwt.service.AuthService;
import com.springsecurity.spring_boot_security_jwt.util.AppConstants;

import jakarta.validation.Valid;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public ApiResponse<UserDto> registerUser(RegisterUserRequest request) {

        User user;
        // update user
        if (request.getUserId() != null && !request.getUserId().isBlank()) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User Id not found..!"));
            // If user changes email
            if (!user.getEmail().equals(request.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email is already registered!");
            }

            // If user changes mobile number
            if (!user.getMobileNumber().equals(request.getMobileNumber())
                    && userRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new RuntimeException("Mobile number is already registered!");
            }
        }
        // REGISTER
        else {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email ID is already registered..!");
            }
            if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new RuntimeException("Mobile Number is already registered..!");
            }
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setAbout(request.getAbout());
        user.setStatus(true);
        user.setIsEmailVerified(false);
        user.setIsMobileVerified(false);

        if (request.getUserId() == null) {
            Role role = roleRepository.findById(AppConstants.NORMAL_USER)
                    .orElseThrow(() -> new RuntimeException("Role Not Found..!"));
            user.setRoleId(role);
        }

        user = userRepository.saveAndFlush(user);

        UserDto userDto = entityToPojo(user);

        return new ApiResponse<UserDto>("Success", true, 200, List.of(userDto));
    }

    public UserDto entityToPojo(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setAbout(user.getAbout());
        dto.setModifiedDate(user.getModifiedDate());
        dto.setStatus(user.getStatus());
        dto.setTokenStartDate(user.getTokenStartDate());
        dto.setTokenExpireDate(user.getTokenExpireDate());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsMobileVerified(user.getIsMobileVerified());

        RoleDto role = null;
        // Convert roles
        if (user.getRoleId() != null) {
            role = new RoleDto(user.getRoleId().getRoleId(),
                    user.getRoleId().getRoleName());

        }
        dto.setRole(role);

        return dto;
    }

    @Override
    public ApiResponse<AuthResponse> loginUser(AuthRequest request) {

        // Authenticate
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Load user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Load UserDetails (required for JWT)
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Generate JWT token
        String token = jwtTokenHelper.generateToken(userDetails);

        // Set token details into User entity
        LocalDateTime now = LocalDateTime.now();

        user.setToken(token);
        user.setTokenStartDate(now);
        long validityInSeconds = jwtTokenHelper.getJwtTokenValidity() / 1000;
        // token validity (example: seconds → convert to LocalDateTime)
        user.setTokenExpireDate(now.plusSeconds(validityInSeconds));

        user = userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(entityToPojo(user));

        return new ApiResponse<>("Login successful", true, 200, List.of(authResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<UserDto> getUserDetails(@Valid GetUserDetailRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Id not found..!"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());

        UserDto userDto = entityToPojo(user);

        return new ApiResponse<>("Get User Details successful", true, 200, List.of(userDto));
    }

}
