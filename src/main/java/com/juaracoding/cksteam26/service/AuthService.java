package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 21/07/25 19.27
@Last Modified 21/07/25 19.27
Version 1.0
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.config.OtherConfig;
import com.juaracoding.cksteam26.dto.validasi.ValForgotPasswordDTO;
import com.juaracoding.cksteam26.dto.validasi.ValForgotPasswordStepThreeDTO;
import com.juaracoding.cksteam26.dto.validasi.ValForgotPasswordStepTwoDTO;
import com.juaracoding.cksteam26.dto.validasi.ValLoginDTO;
import com.juaracoding.cksteam26.dto.validasi.ValRegistrationDTO;
import com.juaracoding.cksteam26.dto.validasi.ValVerifyRegistrationDTO;
import com.juaracoding.cksteam26.handler.ResponseHandler;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.repo.UserOrganizationRepo;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.BcryptImpl;
import com.juaracoding.cksteam26.security.Crypto;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.util.LoggingFile;
import com.juaracoding.cksteam26.util.RequestCapture;
import com.juaracoding.cksteam26.util.SendMailOTP;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AuthService implements UserDetailsService {

    private final Random random = new Random();

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserOrganizationRepo userOrganizationRepo;

    /**
     * 001-010
     */
    public ResponseEntity<Object> registration(User user, HttpServletRequest request) {
        if (user == null || user.getEmail() == null) {
            return new ResponseHandler().handleResponse(
                    "Email not found!",
                    HttpStatus.BAD_REQUEST,
                    null,
                    "DOC00FV001",
                    request);
        }

        Map<String, Object> mapResponse = new HashMap<>();

        try {
            int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
            boolean isAutomation = "y".equalsIgnoreCase(OtherConfig.getEnableAutomationTesting());

            Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());

            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                if (Boolean.TRUE.equals(existingUser.getVerified())) {
                    return new ResponseHandler().handleResponse(
                            "Email is already registered!",
                            HttpStatus.CONFLICT,
                            null,
                            "DOC00FV002",
                            request);
                }

                existingUser.setUsername(user.getUsername());
                existingUser.setName(user.getName());

                String combined = user.getUsername() + user.getPassword();
                existingUser.setPassword(BcryptImpl.hash(combined));

                existingUser.setToken(BcryptImpl.hash(String.valueOf(otp)));

                userRepo.save(existingUser);

                user = existingUser;

            } else {
                String combined = user.getUsername() + user.getPassword();
                user.setPassword(BcryptImpl.hash(combined));
                user.setToken(BcryptImpl.hash(String.valueOf(otp)));

                userRepo.save(user);
            }

            if (isAutomation) {
                mapResponse.put("otp", otp);
            } else {
                SendMailOTP.verifyRegisOTP(
                        "OTP FOR REGISTRATION",
                        user.getName(),
                        user.getEmail(),
                        String.valueOf(otp),
                        "ver_regis.html");
            }

            mapResponse.put("email", user.getEmail());

            return new ResponseHandler().handleResponse(
                    isAutomation ? "Automation Testing: OTP successfully generated!"
                            : "OTP sent, please check your email!",
                    HttpStatus.OK,
                    mapResponse,
                    null,
                    request);

        } catch (Exception e) {
            LoggingFile.logException(
                    "AuthService",
                    "registration(User, HttpServletRequest) " + RequestCapture.allRequest(request),
                    e);
            return new ResponseHandler().handleResponse(
                    "Server error occurred!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "DOC00FE001",
                    request);
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

    public User mapToUser(ValLoginDTO loginDTO) {
        User user = new User();
        user.setUsername(loginDTO.getUsername());
        user.setPassword(loginDTO.getPassword());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findByUsernameAndIsVerified(username, true);
        if (!opUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password!");
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    /**
     * 011-020
     */
    public ResponseEntity<Object> verifyRegistration(User user, HttpServletRequest request) {
        try {
            int otp = random.nextInt(100000, 999999);
            Optional<User> opUser = userRepo.findByEmail(user.getEmail());
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("Email not found!", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV011", request);
            }

            User userNext = opUser.get();
            String incomingToken = user.getToken();
            String savedToken = userNext.getToken();

            if (incomingToken == null || savedToken == null || !BcryptImpl.verifyHash(incomingToken, savedToken)) {
                return new ResponseHandler().handleResponse("Invalid OTP!", HttpStatus.BAD_REQUEST, null, "DOC00FV012",
                        request);
            }

            userNext.setVerified(true);
            userNext.setToken(null);
            userRepo.save(userNext);

            return new ResponseHandler().handleResponse("Registration successful!", HttpStatus.OK, null, null, request);

        } catch (Exception e) {
            LoggingFile.logException("AuthService",
                    "verifyRegistration(User user, HttpServletRequest request)" + RequestCapture.allRequest(request),
                    e);
            return new ResponseHandler().handleResponse("Server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "DOC00FE011", request);
        }
    }

    /**
     * 021-030
     */
    public ResponseEntity<Object> login(User dto, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        User userFromDb = null;

        try {
            String username = dto.getUsername();
            Optional<User> opUser = userRepo.findByUsernameAndIsVerified(username, true);
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User not found", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV021", request);
            }

            userFromDb = opUser.get();

            String combined = userFromDb.getUsername() + dto.getPassword();
            if (!BcryptImpl.verifyHash(combined, userFromDb.getPassword())) {
                return new ResponseHandler().handleResponse("Invalid username or password", HttpStatus.BAD_REQUEST,
                        null, "DOC00FV022", request);
            }
        } catch (Exception e) {
            LoggingFile.logException("AuthService",
                    "login(ValLoginDTO dto, HttpServletRequest request) " + RequestCapture.allRequest(request), e);
            return new ResponseHandler().handleResponse("Server error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "DOC00FE021", request);
        }

        Map<String, Object> mapData = new HashMap<>();
        mapData.put("email", userFromDb.getEmail());

        String token = jwtUtility.doGenerateToken(mapData, userFromDb.getUsername());

        if (JwtConfig.getTokenEncryptEnable().equals("y")) {
            token = Crypto.performEncrypt(token);
        }

        responseMap.put("token", token);

        return new ResponseHandler().handleResponse("Login successful", HttpStatus.OK, responseMap, null, request);
    }

    /**
     * 031-040
     */
    public ResponseEntity<Object> refreshToken(User user, HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        User userFromDb;

        try {
            String identifier = user.getUsername();
            Optional<User> opUser = userRepo.findByEmailAndIsVerified(identifier, true);

            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse(
                        "User not found",
                        HttpStatus.BAD_REQUEST,
                        null,
                        "DOC00FV031",
                        request);
            }

            userFromDb = opUser.get();
            String combined = userFromDb.getUsername() + user.getPassword();

            if (!BcryptImpl.verifyHash(combined, userFromDb.getPassword())) {
                return new ResponseHandler().handleResponse(
                        "Invalid username or password",
                        HttpStatus.BAD_REQUEST,
                        null,
                        "DOC00FV032",
                        request);
            }

        } catch (Exception e) {
            LoggingFile.logException("AuthService",
                    "refreshToken(User, HttpServletRequest)" + RequestCapture.allRequest(request), e);
            return new ResponseHandler().handleResponse(
                    "Server encountered an error",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "DOC00FE031",
                    request);
        }

        Map<String, Object> jwtPayload = new HashMap<>();
        jwtPayload.put("email", userFromDb.getEmail());

        String token = jwtUtility.doGenerateToken(jwtPayload, userFromDb.getUsername());
        if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
            token = Crypto.performEncrypt(token);
        }

        responseMap.put("token", token);

        return new ResponseHandler().handleResponse(
                "Login successful",
                HttpStatus.OK,
                responseMap,
                null,
                request);
    }

    /**
     * 041-050
     */
    public ResponseEntity<Object> lupaPasswordStepOne(User user, HttpServletRequest request) {
        int intOtp = 0;
        String strTokenEstafet = "";
        Map<String, Object> mapResponse = new HashMap<>();
        try {
            Optional<User> opUser = userRepo.findByEmailAndIsVerified(user.getEmail(), true);
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User is not registered", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV041", request);
            }
            intOtp = random.nextInt(100000, 999999);
            strTokenEstafet = BcryptImpl.hash(String.valueOf(intOtp));

            User userNext = opUser.get();
            userNext.setTokenEstafet(strTokenEstafet);
            userNext.setToken(BcryptImpl.hash(String.valueOf(intOtp)));

            if ("y".equalsIgnoreCase(OtherConfig.getEnableAutomationTesting())) {
                mapResponse.put("otp", intOtp);
            }
            mapResponse.put("estafet", strTokenEstafet);
        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "DOC00FE041", request);
        }

        return new ResponseHandler().handleResponse("OTP has been sent to email", HttpStatus.OK, mapResponse, null,
                request);
    }

    public User mapToUser(ValForgotPasswordDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * 051-060
     */
    public ResponseEntity<Object> lupaPasswordStepTwo(User user, HttpServletRequest request) {
        int intOtp = 0;
        String strTokenEstafet = "";
        Map<String, Object> mapResponse = new HashMap<>();
        try {
            Optional<User> opUser = userRepo.findByEmailAndIsVerified(user.getEmail(), true);
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User is not registered", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV051", request);
            }

            User userNext = opUser.get();

            boolean isValid = user.getTokenEstafet().equals(userNext.getTokenEstafet());
            if (!isValid) {
                return new ResponseHandler().handleResponse("Invalid request", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV052", request);
            }

            isValid = BcryptImpl.verifyHash(user.getToken(), userNext.getToken());
            if (!isValid) {
                return new ResponseHandler().handleResponse("Incorrect OTP", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV053", request);
            }

            intOtp = random.nextInt(100000, 999999);
            strTokenEstafet = BcryptImpl.hash(String.valueOf(random.nextInt(99)));

            userNext.setTokenEstafet(strTokenEstafet);
            userNext.setToken(BcryptImpl.hash(String.valueOf(intOtp)));
            mapResponse.put("estafet", strTokenEstafet);

        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "DOC00FE051", request);
        }

        return new ResponseHandler().handleResponse("Verification successful", HttpStatus.OK, mapResponse, null,
                request);
    }

    public User mapToUser(ValForgotPasswordStepTwoDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setToken(dto.getOtp());
        user.setTokenEstafet(dto.getTokenEstafet());
        return user;
    }

    /**
     * 061-070
     */
    public ResponseEntity<Object> lupaPasswordStepThree(ValForgotPasswordStepThreeDTO dto, HttpServletRequest request) {
        String strTokenEstafet = "";
        try {
            if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
                return new ResponseHandler().handleResponse("Passwords do not match", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV061", request);
            }

            Optional<User> opUser = userRepo.findByEmailAndIsVerified(dto.getEmail(), true);
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User not registered", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV062", request);
            }

            User userNext = opUser.get();
            if (!dto.getTokenEstafet().equals(userNext.getTokenEstafet())) {
                return new ResponseHandler().handleResponse("Invalid request", HttpStatus.BAD_REQUEST, null,
                        "DOC00FV063", request);
            }

            userNext.setTokenEstafet(null);
            userNext.setToken(null);
            userNext.setPassword(BcryptImpl.hash(userNext.getUsername() + dto.getPassword()));
            userRepo.save(userNext);

        } catch (Exception e) {
            return new ResponseHandler().handleResponse("Server error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                    "DOC00FE061", request);
        }

        return new ResponseHandler().handleResponse("Password successfully changed", HttpStatus.OK, null, null,
                request);
    }

    /**
     * 071-080
     */
    public ResponseEntity<Object> deleteUser(User user, HttpServletRequest request) {
        try {
            Optional<User> opUser = userRepo.findByUsernameAndIsVerified(user.getUsername(), true);
            if (opUser.isEmpty()) {
                return new ResponseHandler().handleResponse("User not found or not verified!", HttpStatus.BAD_REQUEST,
                        null, "DOC00FV071", request);
            }

            User userToDelete = opUser.get();

            // Set userId to null in UserOrganization table for all entries associated with
            // this user
            userOrganizationRepo.updateUserIdToNullByUserId(userToDelete.getId());

            // Based on user feedback, performing a hard delete of the user record.
            userRepo.delete(userToDelete);

            return new ResponseHandler().handleResponse("User account successfully deleted!", HttpStatus.OK, null, null,
                    request);

        } catch (Exception e) {
            LoggingFile.logException("AuthService",
                    "deleteUser(User user, HttpServletRequest request)" + RequestCapture.allRequest(request), e);
            return new ResponseHandler().handleResponse("Server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "DOC00FE071", request);
        }
    }
}
