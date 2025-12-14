package com.springsecurity.spring_boot_security_jwt.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private String message;

    private boolean success;

    private Integer code;

    private List<T> data;

    public ApiResponse(String message, boolean success, Integer code, List<T> data) {
        this.message = message;
        this.success = success;
        this.code = code;
        this.data = data;
    }

}
