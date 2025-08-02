package com.juaracoding.cksteam26.service;

import com.juaracoding.cksteam26.config.JwtConfig;
import com.juaracoding.cksteam26.core.IService;
import com.juaracoding.cksteam26.dto.response.RespOrganizationDTO;
import com.juaracoding.cksteam26.dto.response.RespUserOrganizationDTO;
import com.juaracoding.cksteam26.dto.validasi.ValCreateOrganizationDTO;
import com.juaracoding.cksteam26.model.Organization;
import com.juaracoding.cksteam26.model.User;
import com.juaracoding.cksteam26.model.UserOrganization;
import com.juaracoding.cksteam26.repo.OrganizationRepo;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserOrganizationService implements IService<Organization> {

    private String className = "OrganizationService";

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserOrganizationRepo userOrganizationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

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


    public ResponseEntity<Object> saveOrganizationWithMembers(ValCreateOrganizationDTO valCreateOrganizationDTO, HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return GlobalResponse.dataIsNotFound("DOC02FV011", request);
            }

            String token = bearerToken.substring(7);
            if ("y".equalsIgnoreCase(JwtConfig.getTokenEncryptEnable())) {
                token = Crypto.performDecrypt(token);
            }

            Map<String, Object> claims = jwtUtility.mappingBodyToken(token);
            String username = String.valueOf(claims.get("username"));
            if (username == null || username.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV012", request);
            }

            Optional<User> ownerOpt = userRepo.findByUsername(username);
            if (ownerOpt.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV013", request);
            }

            User ownerUser = ownerOpt.get();

            // Validasi semua anggota dulu
            List<User> memberUsers = new ArrayList<>();
            if (valCreateOrganizationDTO.getMembers() != null) {
                for (String email : valCreateOrganizationDTO.getMembers()) {
                    Optional<User> memberOpt = userRepo.findByEmail(email);
                    if (memberOpt.isEmpty()) {
                        return GlobalResponse.dataIsNotFound("DOC02FV014", request);
                    }
                    memberUsers.add(memberOpt.get());
                }
            }

            Organization org = new Organization();
            org.setOrganizationName(valCreateOrganizationDTO.getOrganizationName());
            org.setPublicVisibility(valCreateOrganizationDTO.getPublicVisibility());

            Organization savedOrg = saveOrganization(org);

            UserOrganization ownerRel = new UserOrganization();
            ownerRel.setOrganization(savedOrg);
            ownerRel.setUser(ownerUser);
            ownerRel.setOrganizationOwner(true);
            userOrganizationRepo.save(ownerRel);

            for (User memberUser : memberUsers) {
                UserOrganization userOrg = new UserOrganization();
                userOrg.setOrganization(savedOrg);
                userOrg.setUser(memberUser);
                userOrg.setOrganizationOwner(false);
                userOrganizationRepo.save(userOrg);
            }

            return GlobalResponse.dataSavedSuccessfully(request);

        } catch (Exception e) {
            LoggingFile.logException(getClass().getSimpleName(), "saveOrganizationWithMembers", e);
            return GlobalResponse.serverError("DOC02FE012", request);
        }
    }


    public Organization saveOrganization(Organization organization) {
        return organizationRepo.save(organization);
    }

    @Override
    public ResponseEntity<Object> save(Organization organization, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Organization organization, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        return null;
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
