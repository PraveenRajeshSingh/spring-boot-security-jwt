package com.springsecurity.spring_boot_security_jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Integer roleId;

    private String roleName;
}
