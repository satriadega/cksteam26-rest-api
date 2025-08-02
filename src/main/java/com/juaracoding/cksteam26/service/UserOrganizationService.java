package com.juaracoding.cksteam26.service;

import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.dto.response.RespOrganizationDTO;
import com.juaracoding.cksteam26.dto.response.RespUserOrganizationDTO;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.model.UserOrganization;
import com.juaracoding.cksteam26.repo.UserOrganizationRepo;
import com.juaracoding.cksteam26.repo.UserRepo;
import com.juaracoding.cksteam26.security.Crypto;
import com.juaracoding.cksteam26.security.JwtUtility;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserOrganizationService {

    private String className = "OrganizationService";

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserOrganizationRepo userOrganizationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<Object> findAllWithoutPagination(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return GlobalResponse.dataIsNotFound("DOC02FV001", request);
            }
            String token = bearerToken.substring(7);
            if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
                token = Crypto.performDecrypt(token);
            }
            Map<String, Object> claims = jwtUtility.mappingBodyToken(token);
            String username = String.valueOf(claims.get("username"));
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV002", request);
            }

            Optional<User> userOpt = userRepo.findByUsername(username);
            if (userOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV003", request);
            }
            Long userId = userOpt.get().getId();

            List<UserOrganization> userOrganizationList = userOrganizationRepo.findByUserId(userId);
            if (userOrganizationList == null || userOrganizationList.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV004", request);
            }
            List<RespUserOrganizationDTO> dtoList = mapToModelMapper(userOrganizationList);
            return GlobalResponse.dataIsFound(dtoList, request);
        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "findAllWithoutPagination", e);
            return GlobalResponse.serverError("DOC02FE001", request);
        }
    }

    private List<RespUserOrganizationDTO> mapToModelMapper(List<UserOrganization> list) {
        return list.stream().map(uo -> {
            RespUserOrganizationDTO dto = new RespUserOrganizationDTO();
            dto.setUserId(uo.getUser() != null ? uo.getUser().getId() : uo.getUserId());
            dto.setOrganizationOwner(uo.getOrganizationOwner());

            if (uo.getOrganization() != null) {
                RespOrganizationDTO orgDTO = new RespOrganizationDTO();
                orgDTO.setId(uo.getOrganization().getId());
                orgDTO.setOrganizationName(uo.getOrganization().getOrganizationName());
                dto.setOrganization(orgDTO);
            }

            return dto;
        }).toList();
    }

}
