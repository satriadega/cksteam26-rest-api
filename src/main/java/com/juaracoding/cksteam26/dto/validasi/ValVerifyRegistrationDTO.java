package com.juaracoding.cksteam26.dto.validasi;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 30/07/25 21.19
@Last Modified 30/07/25 21.19
Version 1.0
*/

import jakarta.validation.constraints.Pattern;

public class ValVerifyRegistrationDTO {

    @Pattern(
            regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Format Tidak Valid...!!")
    private String email;

    @Pattern(regexp = "^[0-9]{6}$", message = "Format OTP Salah !!")
    private String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return otp;
    }

    public void setToken(String otp) {
        this.otp = otp;
    }
}

