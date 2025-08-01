package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 01/08/25 07.17
@Last Modified 01/08/25 07.17
Version 1.0
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ValForgotPasswordDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is not valid")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

