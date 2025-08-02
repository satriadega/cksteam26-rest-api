package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.56
@Last Modified 03/08/25 01.56
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValUpdateProfileDTO {

    @NotBlank(message = "Name must not be empty")
    @Pattern(
            regexp = "^[a-zA-Z\\s]{4,70}$",
            message = "Only letters and spaces allowed. Minimum 4 and maximum 70 characters"
    )
    private String name;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[A-Za-z0-9@_#\\-$]{9,16}$",
            message = "Invalid password format"
    )
    private String password;

    @NotNull(message = "StatusNotification wajib diisi")
    private Boolean statusNotification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
