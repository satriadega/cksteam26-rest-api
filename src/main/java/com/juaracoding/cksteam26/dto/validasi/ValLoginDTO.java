package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 01/08/25 01.26
@Last Modified 01/08/25 01.26
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValLoginDTO {

    @NotNull(message = "Username must not be null")
    @NotBlank(message = "Username must not be blank")
    @NotEmpty(message = "Username must not be empty")
    @Pattern(regexp = "^[\\w\\.]{5,50}$", message = "Invalid username format, e.g.: paul.123")
    private String username;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[A-Za-z0-9@_#\\-$]{9,16}$",
            message = "Invalid password format"
    )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
