package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false,
            foreignKey = @ForeignKey(name = "fk-notification-user"))
    private User user;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "isRead", nullable = false, columnDefinition = "bit default 0")
    private Boolean isRead = false;

    @Column(name = "CreatedAt", nullable = false, updatable = false, columnDefinition = "datetime default getdate()")
    private Date createdAt = new Date();

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
