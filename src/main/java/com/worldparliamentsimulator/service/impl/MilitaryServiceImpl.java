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
import com.worldparliamentsimulator.dao.MilitaryDAO;
import com.worldparliamentsimulator.domain.Military;
import com.worldparliamentsimulator.dto.MilitaryDTO;
import com.worldparliamentsimulator.dto.MilitarySearchDTO;
import com.worldparliamentsimulator.dto.MilitaryPageDTO;
import com.worldparliamentsimulator.dto.MilitaryConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.MilitaryService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class MilitaryServiceImpl extends GenericServiceImpl<Military, Integer> implements MilitaryService {

    private final static Logger logger = LoggerFactory.getLogger(MilitaryServiceImpl.class);

	@Autowired
	MilitaryDAO militaryDao;

	

	@Override
	public GenericDAO<Military, Integer> getDAO() {
		return (GenericDAO<Military, Integer>) militaryDao;
	}
	
	public List<Military> findAll () {
		List<Military> militarys = militaryDao.findAll();
		
		return militarys;	
		
	}

	public ResultDTO addMilitary(MilitaryDTO militaryDTO, RequestDTO requestDTO) {

		Military military = new Military();

		military.setMilitaryId(militaryDTO.getMilitaryId());

		military.setSoldiers(militaryDTO.getSoldiers());

		military.setTanks(militaryDTO.getTanks());

		military.setAircraft(militaryDTO.getAircraft());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		military = militaryDao.save(military);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Military> getAllMilitarys(Pageable pageable) {
		return militaryDao.findAll(pageable);
	}

	public Page<Military> getAllMilitarys(Specification<Military> spec, Pageable pageable) {
		return militaryDao.findAll(spec, pageable);
	}

	public ResponseEntity<MilitaryPageDTO> getMilitarys(MilitarySearchDTO militarySearchDTO) {
	
			Integer militaryId = militarySearchDTO.getMilitaryId(); 
    			String sortBy = militarySearchDTO.getSortBy();
			String sortOrder = militarySearchDTO.getSortOrder();
			String searchQuery = militarySearchDTO.getSearchQuery();
			Integer page = militarySearchDTO.getPage();
			Integer size = militarySearchDTO.getSize();

	        Specification<Military> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, militaryId, "militaryId"); 
			
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Military> militarys = this.getAllMilitarys(spec, pageable);
		
		//System.out.println(String.valueOf(militarys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(militarys.getTotalPages()));
		
		List<Military> militarysList = militarys.getContent();
		
		MilitaryConvertCriteriaDTO convertCriteria = new MilitaryConvertCriteriaDTO();
		List<MilitaryDTO> militaryDTOs = this.convertMilitarysToMilitaryDTOs(militarysList,convertCriteria);
		
		MilitaryPageDTO militaryPageDTO = new MilitaryPageDTO();
		militaryPageDTO.setMilitarys(militaryDTOs);
		militaryPageDTO.setTotalElements(militarys.getTotalElements());
		return ResponseEntity.ok(militaryPageDTO);
	}

	public List<MilitaryDTO> convertMilitarysToMilitaryDTOs(List<Military> militarys, MilitaryConvertCriteriaDTO convertCriteria) {
		
		List<MilitaryDTO> militaryDTOs = new ArrayList<MilitaryDTO>();
		
		for (Military military : militarys) {
			militaryDTOs.add(convertMilitaryToMilitaryDTO(military,convertCriteria));
		}
		
		return militaryDTOs;

	}
	
	public MilitaryDTO convertMilitaryToMilitaryDTO(Military military, MilitaryConvertCriteriaDTO convertCriteria) {
		
		MilitaryDTO militaryDTO = new MilitaryDTO();

		militaryDTO.setMilitaryId(military.getMilitaryId());

		militaryDTO.setSoldiers(military.getSoldiers());

		militaryDTO.setTanks(military.getTanks());

		militaryDTO.setAircraft(military.getAircraft());
		
		return militaryDTO;
	}

	public ResultDTO updateMilitary(MilitaryDTO militaryDTO, RequestDTO requestDTO) {
		
		Military military = militaryDao.getById(militaryDTO.getMilitaryId());
		
		military.setMilitaryId(ControllerUtils.setValue(military.getMilitaryId(), militaryDTO.getMilitaryId()));
		
		military.setSoldiers(ControllerUtils.setValue(military.getSoldiers(), militaryDTO.getSoldiers()));
		
		military.setTanks(ControllerUtils.setValue(military.getTanks(), militaryDTO.getTanks()));
		
		military.setAircraft(ControllerUtils.setValue(military.getAircraft(), militaryDTO.getAircraft()));

        military = militaryDao.save(military);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MilitaryDTO getMilitaryDTOById(Integer militaryId) {
	
		Military military = militaryDao.getById(militaryId);
		
		MilitaryConvertCriteriaDTO convertCriteria = new MilitaryConvertCriteriaDTO();
		return(this.convertMilitaryToMilitaryDTO(military,convertCriteria));
	}

}
