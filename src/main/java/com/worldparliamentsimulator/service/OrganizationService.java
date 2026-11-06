package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Organization;
import com.worldparliamentsimulator.dto.OrganizationDTO;
import com.worldparliamentsimulator.dto.OrganizationSearchDTO;
import com.worldparliamentsimulator.dto.OrganizationPageDTO;
import com.worldparliamentsimulator.dto.OrganizationConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OrganizationService extends GenericService<Organization, Integer> {

	List<Organization> findAll();

	ResultDTO addOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO);

	ResultDTO updateOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO);

    Page<Organization> getAllOrganizations(Pageable pageable);

    Page<Organization> getAllOrganizations(Specification<Organization> spec, Pageable pageable);

	ResponseEntity<OrganizationPageDTO> getOrganizations(OrganizationSearchDTO organizationSearchDTO);
	
	List<OrganizationDTO> convertOrganizationsToOrganizationDTOs(List<Organization> organizations, OrganizationConvertCriteriaDTO convertCriteria);

	OrganizationDTO getOrganizationDTOById(Integer organizationId);



	
}
