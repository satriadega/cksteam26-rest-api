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
import com.juaracoding.cksteam26.handler.ResponseHandler;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.BcryptImpl;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.RequestCapture;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
//@Transactional
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
                    "TRN00FV001",
                    request);
        }

        Map<String, Object> mapResponse = new HashMap<>();
        try {
            int otp = random.nextInt(100000, 999999);
            user.setToken(BcryptImpl.hash(String.valueOf(otp)));
            user.setPassword(BcryptImpl.hash(user.getUsername() + user.getPassword()));

            // akses block removed since User entity has no akses

            userRepo.save(user);

            if ("y".equalsIgnoreCase(OtherConfig.getEnableAutomationTesting())) {
                mapResponse.put("otp", otp);
            }

//            SendMailOTP.verifyRegisOTP(
//                    "OTP UNTUK REGISTRASI",
//                    user.getName(),
//                    user.getEmail(),
//                    String.valueOf(otp),
//                    "ver_regis.html"
//            );

            mapResponse.put("email", user.getEmail());
        } catch (Exception e) {
            LoggingFile.logException("AuthService",
                    "registration(User, HttpServletRequest) " + RequestCapture.allRequest(request), e);
            return new ResponseHandler().handleResponse(
                    "Server Tidak Dapat Memproses !!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "TRN00FE001",
                    request);
        }

        return new ResponseHandler().handleResponse(
                "OTP Terkirim, Cek Email !!",
                HttpStatus.OK,
                mapResponse,
                null,
                request);
    }

    public User mapToUser(ValRegistrationDTO registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setUsername(registrationDTO.getUsername());
        user.setName(registrationDTO.getName());
        user.setPassword(registrationDTO.getPassword());
        return user;
    }


//    public User mapToUser(VerifyRegisDTO verifyRegisDTO) {
//        User user = new User();
//        user.setEmail(verifyRegisDTO.getEmail());
//        user.setOtp(verifyRegisDTO.getOtp());
//
//        return user;
//    }
//
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
}
