package com.worldparliamentsimulator.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.worldparliamentsimulator.dao.GenericDAO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.service.impl.GenericServiceImpl;
import com.worldparliamentsimulator.dao.OrganizationDAO;
import com.worldparliamentsimulator.domain.Organization;
import com.worldparliamentsimulator.dto.OrganizationDTO;
import com.worldparliamentsimulator.dto.OrganizationSearchDTO;
import com.worldparliamentsimulator.dto.OrganizationPageDTO;
import com.worldparliamentsimulator.dto.OrganizationConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.OrganizationService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class OrganizationServiceImpl extends GenericServiceImpl<Organization, Integer> implements OrganizationService {

    private final static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	OrganizationDAO organizationDao;

	

	@Override
	public GenericDAO<Organization, Integer> getDAO() {
		return (GenericDAO<Organization, Integer>) organizationDao;
	}
	
	public List<Organization> findAll () {
		List<Organization> organizations = organizationDao.findAll();
		
		return organizations;	
		
	}

	public ResultDTO addOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO) {

		Organization organization = new Organization();

		organization.setOrganizationId(organizationDTO.getOrganizationId());

		organization.setName(organizationDTO.getName());

		organization.setPolicies(organizationDTO.getPolicies());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		organization = organizationDao.save(organization);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Organization> getAllOrganizations(Pageable pageable) {
		return organizationDao.findAll(pageable);
	}

	public Page<Organization> getAllOrganizations(Specification<Organization> spec, Pageable pageable) {
		return organizationDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrganizationPageDTO> getOrganizations(OrganizationSearchDTO organizationSearchDTO) {
	
			Integer organizationId = organizationSearchDTO.getOrganizationId(); 
 			String name = organizationSearchDTO.getName(); 
 			String policies = organizationSearchDTO.getPolicies(); 
 			String sortBy = organizationSearchDTO.getSortBy();
			String sortOrder = organizationSearchDTO.getSortOrder();
			String searchQuery = organizationSearchDTO.getSearchQuery();
			Integer page = organizationSearchDTO.getPage();
			Integer size = organizationSearchDTO.getSize();

	        Specification<Organization> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, organizationId, "organizationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, policies, "policies"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("policies")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Organization> organizations = this.getAllOrganizations(spec, pageable);
		
		//System.out.println(String.valueOf(organizations.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(organizations.getTotalPages()));
		
		List<Organization> organizationsList = organizations.getContent();
		
		OrganizationConvertCriteriaDTO convertCriteria = new OrganizationConvertCriteriaDTO();
		List<OrganizationDTO> organizationDTOs = this.convertOrganizationsToOrganizationDTOs(organizationsList,convertCriteria);
		
		OrganizationPageDTO organizationPageDTO = new OrganizationPageDTO();
		organizationPageDTO.setOrganizations(organizationDTOs);
		organizationPageDTO.setTotalElements(organizations.getTotalElements());
		return ResponseEntity.ok(organizationPageDTO);
	}

	public List<OrganizationDTO> convertOrganizationsToOrganizationDTOs(List<Organization> organizations, OrganizationConvertCriteriaDTO convertCriteria) {
		
		List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
		
		for (Organization organization : organizations) {
			organizationDTOs.add(convertOrganizationToOrganizationDTO(organization,convertCriteria));
		}
		
		return organizationDTOs;

	}
	
	public OrganizationDTO convertOrganizationToOrganizationDTO(Organization organization, OrganizationConvertCriteriaDTO convertCriteria) {
		
		OrganizationDTO organizationDTO = new OrganizationDTO();

		organizationDTO.setOrganizationId(organization.getOrganizationId());

		organizationDTO.setName(organization.getName());

		organizationDTO.setPolicies(organization.getPolicies());
		
		return organizationDTO;
	}

	public ResultDTO updateOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO) {
		
		Organization organization = organizationDao.getById(organizationDTO.getOrganizationId());
		
		organization.setOrganizationId(ControllerUtils.setValue(organization.getOrganizationId(), organizationDTO.getOrganizationId()));
		
		organization.setName(ControllerUtils.setValue(organization.getName(), organizationDTO.getName()));
		
		organization.setPolicies(ControllerUtils.setValue(organization.getPolicies(), organizationDTO.getPolicies()));

        organization = organizationDao.save(organization);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrganizationDTO getOrganizationDTOById(Integer organizationId) {
	
		Organization organization = organizationDao.getById(organizationId);
		
		OrganizationConvertCriteriaDTO convertCriteria = new OrganizationConvertCriteriaDTO();
		return(this.convertOrganizationToOrganizationDTO(organization,convertCriteria));
	}

}
