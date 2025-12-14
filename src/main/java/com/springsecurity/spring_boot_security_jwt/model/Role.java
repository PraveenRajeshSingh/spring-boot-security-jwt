package com.springsecurity.spring_boot_security_jwt.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createBy;

    @CreatedDate
    @Column(name = "created_at_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_by", nullable = true)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = true)
    private LocalDateTime modifiedDate;
}
