package com.juaracoding.cksteam26.model;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 14/07/25 16.37
@Last Modified 14/07/25 16.37
Version 1.0
*/

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UserAccount")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "Password", nullable = false, length = 64)
    private String password;

    @Column(name = "StatusNotification", nullable = false)
    private Boolean statusNotification;

    @Column(name = "HasNotification", nullable = false)
    private Boolean hasNotification;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified;

    @Column(name = "Token", length = 6)
    private String token;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "UpdatedAt", insertable = false)
    private Date updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

