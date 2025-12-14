package com.springsecurity.spring_boot_security_jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDetailRequest {

    @NotBlank(message = "User Id is mandatory")
    private String userId;
}