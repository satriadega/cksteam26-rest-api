package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 19.27
@Last Modified 21/07/25 19.27
Version 1.0
*/

import com.juaracoding.cksteam26.config.OtherConfig;
import com.juaracoding.cksteam26.dto.validasi.ValRegistrationDTO;
import com.juaracoding.cksteam26.dto.validasi.ValVerifyRegistrationDTO;
import com.juaracoding.cksteam26.handler.ResponseHandler;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.BcryptImpl;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.RequestCapture;
import com.juaracoding.cksteam26.util.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class AuthService implements UserDetailsService {

    private final Random random = new Random();

    @Autowired
    private UserRepo userRepo;

    /**
     * 001-010
     */
    public ResponseEntity<Object> registration(User user, HttpServletRequest request) {
        if (user == null || user.getEmail() == null) {
            return new ResponseHandler().handleResponse(
                    "Email Tidak Ditemukan !!",
                    HttpStatus.BAD_REQUEST,
                    null,
                    "DOC00FV001",
                    request
            );
        }

        Map<String, Object> mapResponse = new HashMap<>();

        try {
            int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
            boolean isAutomation = "y".equalsIgnoreCase(OtherConfig.getEnableAutomationTesting());

            Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());

            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                if (Boolean.TRUE.equals(existingUser.getIsVerified())) {
                    return new ResponseHandler().handleResponse(
                            "Email Sudah Terdaftar !!",
                            HttpStatus.CONFLICT,
                            null,
                            "DOC00FV002",
                            request
                    );
                }

                // Update only the necessary fields (username, name, password)
                existingUser.setUsername(user.getUsername());
                existingUser.setName(user.getName());
                existingUser.setPassword(BcryptImpl.hash(user.getPassword())); // Make sure to hash password
                existingUser.setToken(BcryptImpl.hash(String.valueOf(otp)));  // Generate new token for OTP

                // Save the updated user
                userRepo.save(existingUser);

                user = existingUser;  // Set the updated user back

            } else {
                // If user doesn't exist, create new user
                user.setToken(BcryptImpl.hash(String.valueOf(otp)));
                user.setPassword(BcryptImpl.hash(user.getPassword()));
                userRepo.save(user);
            }

            // Automation or regular email sending
            if (isAutomation) {
                mapResponse.put("otp", otp);
            } else {
                SendMailOTP.verifyRegisOTP(
                        "OTP UNTUK REGISTRASI",
                        user.getName(),
                        user.getEmail(),
                        String.valueOf(otp),
                        "ver_regis.html"
                );
            }

            mapResponse.put("email", user.getEmail());

            return new ResponseHandler().handleResponse(
                    isAutomation ? "Automation Testing: OTP Berhasil Dibuat !!" : "OTP Terkirim, Cek Email !!",
                    HttpStatus.OK,
                    mapResponse,
                    null,
                    request
            );

        } catch (Exception e) {
            LoggingFile.logException(
                    "AuthService",
                    "registration(User, HttpServletRequest) " + RequestCapture.allRequest(request),
                    e
            );
            return new ResponseHandler().handleResponse(
                    "Server Tidak Dapat Memproses !!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "DOC00FE001",
                    request
            );
        }
    }


    public User mapToUser(ValRegistrationDTO registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setUsername(registrationDTO.getUsername());
        user.setName(registrationDTO.getName());
        user.setPassword(registrationDTO.getPassword());
        return user;
    }


    public User mapToUser(ValVerifyRegistrationDTO verifyRegistrationDTO) {
        User user = new User();
        user.setEmail(verifyRegistrationDTO.getEmail());
        user.setToken(verifyRegistrationDTO.getToken());

        return user;
    }

//    public User mapToUser(LoginDTO loginDTO) {
//        User user = new User();
//        user.setUsername(loginDTO.getUsername());
//        user.setPassword(loginDTO.getPassword());
//
//        return user;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsernameAndIsVerified(username, true);
        if (!opUser.isPresent()) {
            throw new UsernameNotFoundException("Username atau Password Salah !!!");
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    /**
     * 011-020
     */
    public ResponseEntity<Object> verifyRegistration(User user, HttpServletRequest request) {
        try {
            int otp = random.nextInt(100000, 999999);
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if (!opUser.isPresent()) {
                return new ResponseHandler().handleResponse("Email Tidak Ditemukan !!", HttpStatus.BAD_REQUEST, null, "DOC00FV011", request);
            }

            User userNext = opUser.get();
            System.out.println("=== DEBUG OTP VERIFICATION ===");
            System.out.println("Input OTP (plaintext): " + user.getToken());
            System.out.println("Hashed OTP from DB: " + userNext.getToken());
            System.out.println("Match result: " + BcryptImpl.verifyHash(user.getToken(), userNext.getToken()));
            System.out.println("================================");

            if (!BcryptImpl.verifyHash(user.getToken(), userNext.getToken())) {
                return new ResponseHandler().handleResponse("OTP Salah !!", HttpStatus.BAD_REQUEST, null, "DOC00FV012", request);
            }

            userNext.setIsVerified(true);
            userNext.setToken(BcryptImpl.hash(String.valueOf(otp)));
            userRepo.save(userNext);

            return new ResponseHandler().handleResponse("Registrasi Berhasil !!", HttpStatus.OK, null, null, request);

        } catch (Exception e) {
            LoggingFile.logException("AuthService", "verifyRegis(User user, HttpServletRequest request)" + RequestCapture.allRequest(request), e);

            return new ResponseHandler().handleResponse("Terjadi Kesalahan Pada Server", HttpStatus.INTERNAL_SERVER_ERROR, null, "DOC00FE011", request);
        }
    }
}
