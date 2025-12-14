package com.springsecurity.spring_boot_security_jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springsecurity.spring_boot_security_jwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user where email=?1", nativeQuery = true)
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);
}
