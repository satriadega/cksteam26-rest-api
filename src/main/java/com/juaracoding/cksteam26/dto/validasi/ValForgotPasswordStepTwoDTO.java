package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 01/08/25 07.22
@Last Modified 01/08/25 07.22
Version 1.0
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ValForgotPasswordStepTwoDTO {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "OTP must not be blank")
    private String otp;

    @NotBlank(message = "Estafet token must not be blank")
    private String tokenEstafet;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getTokenEstafet() {
        return tokenEstafet;
    }

    public void setTokenEstafet(String tokenEstafet) {
        this.tokenEstafet = tokenEstafet;
    }
}

