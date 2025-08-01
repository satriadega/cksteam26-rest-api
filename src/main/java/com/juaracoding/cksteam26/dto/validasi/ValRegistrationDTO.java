package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 19.11
@Last Modified 21/07/25 19.11
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ValRegistrationDTO {

    @NotBlank(message = "Username must not be empty")
    @Pattern(
            regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Only lowercase letters, digits, and dots allowed. Length 8-16 characters. Example: dominic.123"
    )
    private String username;

    @NotBlank(message = "Name must not be empty")
    @Pattern(
            regexp = "^[a-zA-Z\\s]{4,70}$",
            message = "Only letters and spaces allowed. Minimum 4 and maximum 70 characters"
    )
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Pattern(
            regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Invalid email format. Example: user_name123@sub.domain.com"
    )
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[A-Za-z0-9@_#\\-$]{9,16}$",
            message = "At least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character (@, _, #, -, $). Length 9-16 characters"
    )
    private String password;

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
}
