package com.springsecurity.spring_boot_security_jwt.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank( message = "email must not be blank" )
    @Email( message = "email must be a valid email address" )
    private String email;

    @Pattern( regexp = "^\\+?[0-9]{7,15}$", message = "mobileNumber must be a valid phone number" )
    private String mobileNumber;

    @NotBlank( message = "password must not be blank" )
    @Size( min = 6, message = "password must be at least 6 characters" )
    private String password;
}
