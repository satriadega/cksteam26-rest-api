package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 01/08/25 08.00
@Last Modified 01/08/25 08.00
Version 1.0
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValForgotPasswordStepThreeDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Password confirmation must not be blank")
    private String passwordConfirmation;

    @NotBlank(message = "Estafet token must not be blank")
    private String tokenEstafet;

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getTokenEstafet() {
        return tokenEstafet;
    }

    public void setTokenEstafet(String tokenEstafet) {
        this.tokenEstafet = tokenEstafet;
    }
}
