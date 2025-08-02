package com.juaracoding.cksteam26.service;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 03/08/25 01.28
@Last Modified 03/08/25 01.28
Version 1.0
*/

import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.dto.response.RespProfileDTO;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.Crypto;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    private String className = "ProfileService";

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
                return GlobalResponse.dataIsNotFound("DOC02FV003", request);
            }

            List<User> userList = List.of(userOpt.get());
            List<RespProfileDTO> mapResponse = mapToModelMapper(userList);
            return GlobalResponse.dataIsFound(mapResponse, request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "findAllWithoutPagination", e);
            return GlobalResponse.serverError("DOC02FE001", request);
        }
    }

    public List<RespProfileDTO> mapToModelMapper(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<RespProfileDTO>>() {
        }.getType());
    }
}
