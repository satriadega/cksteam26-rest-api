package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.28
@Last Modified 03/08/25 01.28
Version 1.0
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.dto.response.RespProfileDTO;
import com.juaracoding.cksteam26.dto.validasi.ValUpdateProfileDTO;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.BcryptImpl;
import com.juaracoding.cksteam26.security.Crypto;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class ProfileService {

    private String className = "ProfileService";

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> findByAccount(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return GlobalResponse.dataIsNotFound("DOC03FV001", request);
            }
            String token = bearerToken.substring(7);
            if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
                token = Crypto.performDecrypt(token);
            }
            Map<String, Object> claims = jwtUtility.mappingBodyToken(token);
            String username = String.valueOf(claims.get("username"));
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC03FV002", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC03FV003", request);
            }

            List<User> userList = List.of(userOpt.get());
            List<RespProfileDTO> mapResponse = mapToModelMapper(userList);
            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "findAllWithoutPagination", e);
            return GlobalResponse.serverError("DOC03FE001", request);
        }
    }

    public ResponseEntity<Object> update(String strUser, MultipartFile file, HttpServletRequest request) {
        try {
            ValUpdateProfileDTO user = new ObjectMapper().readValue(strUser, ValUpdateProfileDTO.class);

            if (user == null) {
                return GlobalResponse.objectNull("DOC03FV011", request);
            }

            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return GlobalResponse.dataIsNotFound("DOC03FV012", request);
            }

            String token = bearerToken.substring(7);
            if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
                token = Crypto.performDecrypt(token);
            }

            Map<String, Object> claims = jwtUtility.mappingBodyToken(token);
            String username = String.valueOf(claims.get("username"));
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC03FV013", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC03FV014", request);
            }

            User existingUser = userOpt.get();

            if (file != null && !file.isEmpty()) {
                if (file.getSize() > 2 * 1024 * 1024) { // 2MB
                    return GlobalResponse.customError("DOC03FV016", "Ukuran file tidak boleh melebihi 2MB", request);
                }

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return GlobalResponse.customError("DOC03FV017", "Format file harus berupa gambar", request);
                }

                String originalFilename = StringUtils.cleanPath(file.getOriginalFilename().replace(" ", "_"));
                String fileName = System.currentTimeMillis() + "_" + originalFilename;
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    try {
                        Files.createDirectories(uploadDir);
                    } catch (IOException e) {
                        LoggingFile.logException(getClass().getSimpleName(), "update", e);
                        return GlobalResponse.serverError("DOC03FE013", request);
                    }
                }
                Path path = Paths.get(uploadPath + fileName);
                try {
                    Files.copy(file.getInputStream(), path);
                    existingUser.setPathFoto("/images/" + fileName);
                } catch (IOException e) {
                    LoggingFile.logException(getClass().getSimpleName(), "update", e);
                    return GlobalResponse.serverError("DOC03FE012", request);
                }
            }

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String rawPasswordWithUsername = username + user.getPassword();
                if (BcryptImpl.verifyHash(rawPasswordWithUsername, existingUser.getPassword())) {
                    return GlobalResponse.customError("DOC03FV015", "Password baru tidak boleh sama dengan password sebelumnya", request);
                }
                existingUser.setPassword(BcryptImpl.hash(rawPasswordWithUsername));
            }

            existingUser.setName(user.getName());
            existingUser.setStatusNotification(user.getStatusNotification());
            existingUser.setUpdatedAt(new Date());

            userRepo.save(existingUser);
            return GlobalResponse.dataUpdatedSuccessfully(request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "update", e);
            return GlobalResponse.serverError("DOC03FE011", request);
        }
    }


    public List<RespProfileDTO> mapToModelMapper(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<RespProfileDTO>>() {
        }.getType());
    }

    public User mapToModelMapper(ValUpdateProfileDTO valUpdateProfileDTO) {
        return modelMapper.map(valUpdateProfileDTO, User.class);
    }
}
