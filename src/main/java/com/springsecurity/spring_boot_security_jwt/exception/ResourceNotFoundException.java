package com.springsecurity.spring_boot_security_jwt.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fileName;
    long fileValue;
    String name;

    public ResourceNotFoundException (String resourceName, String fileName, Integer userId) {
        super(String.format("%s not found with %s : %s", resourceName, fileName, userId));

        this.resourceName = resourceName;
        this.fileName = fileName;
        this.fileValue = userId;
    }
}
