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
import com.worldparliamentsimulator.dao.AllianceDAO;
import com.worldparliamentsimulator.domain.Alliance;
import com.worldparliamentsimulator.dto.AllianceDTO;
import com.worldparliamentsimulator.dto.AllianceSearchDTO;
import com.worldparliamentsimulator.dto.AlliancePageDTO;
import com.worldparliamentsimulator.dto.AllianceConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.AllianceService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class AllianceServiceImpl extends GenericServiceImpl<Alliance, Integer> implements AllianceService {

    private final static Logger logger = LoggerFactory.getLogger(AllianceServiceImpl.class);

	@Autowired
	AllianceDAO allianceDao;

	

	@Override
	public GenericDAO<Alliance, Integer> getDAO() {
		return (GenericDAO<Alliance, Integer>) allianceDao;
	}
	
	public List<Alliance> findAll () {
		List<Alliance> alliances = allianceDao.findAll();
		
		return alliances;	
		
	}

	public ResultDTO addAlliance(AllianceDTO allianceDTO, RequestDTO requestDTO) {

		Alliance alliance = new Alliance();

		alliance.setAllianceId(allianceDTO.getAllianceId());

		alliance.setName(allianceDTO.getName());

		alliance.setMemberCountries(allianceDTO.getMemberCountries());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		alliance = allianceDao.save(alliance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Alliance> getAllAlliances(Pageable pageable) {
		return allianceDao.findAll(pageable);
	}

	public Page<Alliance> getAllAlliances(Specification<Alliance> spec, Pageable pageable) {
		return allianceDao.findAll(spec, pageable);
	}

	public ResponseEntity<AlliancePageDTO> getAlliances(AllianceSearchDTO allianceSearchDTO) {
	
			Integer allianceId = allianceSearchDTO.getAllianceId(); 
 			String name = allianceSearchDTO.getName(); 
 			String memberCountries = allianceSearchDTO.getMemberCountries(); 
 			String sortBy = allianceSearchDTO.getSortBy();
			String sortOrder = allianceSearchDTO.getSortOrder();
			String searchQuery = allianceSearchDTO.getSearchQuery();
			Integer page = allianceSearchDTO.getPage();
			Integer size = allianceSearchDTO.getSize();

	        Specification<Alliance> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, allianceId, "allianceId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, memberCountries, "memberCountries"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("memberCountries")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Alliance> alliances = this.getAllAlliances(spec, pageable);
		
		//System.out.println(String.valueOf(alliances.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(alliances.getTotalPages()));
		
		List<Alliance> alliancesList = alliances.getContent();
		
		AllianceConvertCriteriaDTO convertCriteria = new AllianceConvertCriteriaDTO();
		List<AllianceDTO> allianceDTOs = this.convertAlliancesToAllianceDTOs(alliancesList,convertCriteria);
		
		AlliancePageDTO alliancePageDTO = new AlliancePageDTO();
		alliancePageDTO.setAlliances(allianceDTOs);
		alliancePageDTO.setTotalElements(alliances.getTotalElements());
		return ResponseEntity.ok(alliancePageDTO);
	}

	public List<AllianceDTO> convertAlliancesToAllianceDTOs(List<Alliance> alliances, AllianceConvertCriteriaDTO convertCriteria) {
		
		List<AllianceDTO> allianceDTOs = new ArrayList<AllianceDTO>();
		
		for (Alliance alliance : alliances) {
			allianceDTOs.add(convertAllianceToAllianceDTO(alliance,convertCriteria));
		}
		
		return allianceDTOs;

	}
	
	public AllianceDTO convertAllianceToAllianceDTO(Alliance alliance, AllianceConvertCriteriaDTO convertCriteria) {
		
		AllianceDTO allianceDTO = new AllianceDTO();

		allianceDTO.setAllianceId(alliance.getAllianceId());

		allianceDTO.setName(alliance.getName());

		allianceDTO.setMemberCountries(alliance.getMemberCountries());
		
		return allianceDTO;
	}

	public ResultDTO updateAlliance(AllianceDTO allianceDTO, RequestDTO requestDTO) {
		
		Alliance alliance = allianceDao.getById(allianceDTO.getAllianceId());
		
		alliance.setAllianceId(ControllerUtils.setValue(alliance.getAllianceId(), allianceDTO.getAllianceId()));
		
		alliance.setName(ControllerUtils.setValue(alliance.getName(), allianceDTO.getName()));
		
		alliance.setMemberCountries(ControllerUtils.setValue(alliance.getMemberCountries(), allianceDTO.getMemberCountries()));

        alliance = allianceDao.save(alliance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public AllianceDTO getAllianceDTOById(Integer allianceId) {
	
		Alliance alliance = allianceDao.getById(allianceId);
		
		AllianceConvertCriteriaDTO convertCriteria = new AllianceConvertCriteriaDTO();
		return(this.convertAllianceToAllianceDTO(alliance,convertCriteria));
	}

}
