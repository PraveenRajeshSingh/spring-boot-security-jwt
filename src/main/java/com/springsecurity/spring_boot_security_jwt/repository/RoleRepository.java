package com.springsecurity.spring_boot_security_jwt.repository;

import com.springsecurity.spring_boot_security_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository< Role, Integer > {
}
