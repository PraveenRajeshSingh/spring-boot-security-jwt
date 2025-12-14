package com.springsecurity.spring_boot_security_jwt.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "is_email_verified", nullable = true)
    private Boolean isEmailVerified;

    @Column(name = "is_mobile_verified", nullable = true)
    private Boolean isMobileVerified;

    @Column(name = "about", nullable = true)
    private String about;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "token_start_date", nullable = false)
    private LocalDateTime tokenStartDate;

    @Column(name = "token_expire_date", nullable = false)
    private LocalDateTime tokenExpireDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role roleId;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createBy;

    @CreatedDate
    @Column(name = "created_at_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = true)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = true)
    private LocalDateTime modifiedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleId == null || roleId.getRoleName() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority(roleId.getRoleName()));
    }

    @Override
    public String getUsername() {
        return (email != null && !email.isBlank()) ? email : mobileNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(status);
    }
}
