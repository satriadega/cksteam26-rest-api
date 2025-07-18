package com.juaracoding.cksteam26.dto;

import jakarta.validation.constraints.*;

public class ValUserDTO {

    @NotBlank(message = "Username wajib diisi")
    @Size(max = 50, message = "Username maksimal 50 karakter")
    private String username;

    @NotBlank(message = "Name wajib diisi")
    @Size(max = 50, message = "Name maksimal 50 karakter")
    private String name;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    @Size(max = 150, message = "Email maksimal 150 karakter")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    @Size(min = 6, max = 64, message = "Password harus antara 6 sampai 64 karakter")
    private String password;

    @NotNull(message = "StatusNotification wajib diisi")
    private Boolean statusNotification;

    @NotNull(message = "HasNotification wajib diisi")
    private Boolean hasNotification;

    @NotNull(message = "IsVerified wajib diisi")
    private Boolean isVerified;

    @Size(max = 6, message = "Token maksimal 6 karakter")
    private String token;

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

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
