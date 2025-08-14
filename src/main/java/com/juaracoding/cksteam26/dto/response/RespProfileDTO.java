package com.juaracoding.cksteam26.dto.response;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.35
@Last Modified 03/08/25 01.35
Version 1.0
*/

public class RespProfileDTO {

    private String username;
    private String name;
    private String email;
    private Boolean statusNotification;
    private Boolean hasNotification;
    private Integer notificationCounter;
    private Integer notificationType;
    private String pathFoto;

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
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
}
