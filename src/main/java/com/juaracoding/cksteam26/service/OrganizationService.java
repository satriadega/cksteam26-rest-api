package com.juaracoding.cksteam26.service;

import com.juaracoding.cksteam26.dto.response.RespOrganizationDTO;
import com.juaracoding.cksteam26.model.Organization;
import com.juaracoding.cksteam26.repo.OrganizationRepo;
import com.juaracoding.cksteam26.util.GlobalResponse;
import com.juaracoding.cksteam26.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrganizationService {

    private String className = "OrganizationService";

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> findAllWithoutPagination(HttpServletRequest request) {
        List<Organization> organizationList;
        try {
            organizationList = organizationRepo.findAll();
            if (organizationList.isEmpty()) {
                return GlobalResponse.dataIsNotFound("DOC02FV002", request);
            }
            List<RespOrganizationDTO> dtoList = mapToModelMapper(organizationList);
            return GlobalResponse.dataIsFound(dtoList, request);
        } catch (Exception e) {
            LoggingFile.logException(className, "findAllWithoutPagination", e);
            return GlobalResponse.serverError("DOC02FE002", request);
        }
    }

    private List<RespOrganizationDTO> mapToModelMapper(List<Organization> organizationList) {
        return modelMapper.map(organizationList, new TypeToken<List<RespOrganizationDTO>>() {
        }.getType());
    }
}
