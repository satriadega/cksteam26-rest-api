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

    @NotBlank(message = "Username tidak boleh kosong")
    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$", message = "Format huruf kecil, angka, dan titik saja. Panjang 8-16 karakter. Contoh: paul.123")
    private String username;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Pattern(regexp = "^[a-zA-Z\\s]{4,70}$", message = "Hanya alfabet dan spasi. Minimal 4, maksimal 70 karakter")
    private String name;

    @NotBlank(message = "Email tidak boleh kosong")
    @Pattern(
            regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Format email tidak valid. Contoh: user_name123@sub.domain.com"
    )
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[A-Za-z0-9@_#\\-$]{9,16}$",
            message = "Minimal 1 huruf besar, 1 huruf kecil, 1 angka, dan 1 karakter spesial (@, _, #, -, $). Panjang 9-16 karakter"
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

