package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "UserAccount")
public class User implements UserDetails {

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
    private Boolean statusNotification = true;

    @Column(name = "HasNotification", nullable = false)
    private Boolean hasNotification = false;

    @Column(name = "NotificationCounter", nullable = false)
    private Integer notificationCounter = 0;

    @Column(name = "NotificationType", nullable = false)
    private Integer notificationType = 0;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "Token", length = 255)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedAt")
    private Date updatedAt;

    /**
     * disini letak role dari user nya yang akan di baca di API nanti
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatusNotification() {
        return statusNotification;
    }

    public void setStatusNotification(Boolean statusNotification) {
        this.statusNotification = statusNotification;
    }

    public Boolean getHasNotification() {
        return hasNotification;
    }

    public void setHasNotification(Boolean hasNotification) {
        this.hasNotification = hasNotification;
    }

    public Integer getNotificationCounter() {
        return notificationCounter;
    }

    public void setNotificationCounter(Integer notificationCounter) {
        this.notificationCounter = notificationCounter;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
