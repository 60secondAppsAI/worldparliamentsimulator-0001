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
import com.worldparliamentsimulator.dao.EspionageDAO;
import com.worldparliamentsimulator.domain.Espionage;
import com.worldparliamentsimulator.dto.EspionageDTO;
import com.worldparliamentsimulator.dto.EspionageSearchDTO;
import com.worldparliamentsimulator.dto.EspionagePageDTO;
import com.worldparliamentsimulator.dto.EspionageConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.EspionageService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class EspionageServiceImpl extends GenericServiceImpl<Espionage, Integer> implements EspionageService {

    private final static Logger logger = LoggerFactory.getLogger(EspionageServiceImpl.class);

	@Autowired
	EspionageDAO espionageDao;

	

	@Override
	public GenericDAO<Espionage, Integer> getDAO() {
		return (GenericDAO<Espionage, Integer>) espionageDao;
	}
	
	public List<Espionage> findAll () {
		List<Espionage> espionages = espionageDao.findAll();
		
		return espionages;	
		
	}

	public ResultDTO addEspionage(EspionageDTO espionageDTO, RequestDTO requestDTO) {

		Espionage espionage = new Espionage();

		espionage.setEspionageId(espionageDTO.getEspionageId());

		espionage.setOperationsConducted(espionageDTO.getOperationsConducted());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		espionage = espionageDao.save(espionage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Espionage> getAllEspionages(Pageable pageable) {
		return espionageDao.findAll(pageable);
	}

	public Page<Espionage> getAllEspionages(Specification<Espionage> spec, Pageable pageable) {
		return espionageDao.findAll(spec, pageable);
	}

	public ResponseEntity<EspionagePageDTO> getEspionages(EspionageSearchDTO espionageSearchDTO) {
	
			Integer espionageId = espionageSearchDTO.getEspionageId(); 
  			String sortBy = espionageSearchDTO.getSortBy();
			String sortOrder = espionageSearchDTO.getSortOrder();
			String searchQuery = espionageSearchDTO.getSearchQuery();
			Integer page = espionageSearchDTO.getPage();
			Integer size = espionageSearchDTO.getSize();

	        Specification<Espionage> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, espionageId, "espionageId"); 
			
			

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

		Page<Espionage> espionages = this.getAllEspionages(spec, pageable);
		
		//System.out.println(String.valueOf(espionages.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(espionages.getTotalPages()));
		
		List<Espionage> espionagesList = espionages.getContent();
		
		EspionageConvertCriteriaDTO convertCriteria = new EspionageConvertCriteriaDTO();
		List<EspionageDTO> espionageDTOs = this.convertEspionagesToEspionageDTOs(espionagesList,convertCriteria);
		
		EspionagePageDTO espionagePageDTO = new EspionagePageDTO();
		espionagePageDTO.setEspionages(espionageDTOs);
		espionagePageDTO.setTotalElements(espionages.getTotalElements());
		return ResponseEntity.ok(espionagePageDTO);
	}

	public List<EspionageDTO> convertEspionagesToEspionageDTOs(List<Espionage> espionages, EspionageConvertCriteriaDTO convertCriteria) {
		
		List<EspionageDTO> espionageDTOs = new ArrayList<EspionageDTO>();
		
		for (Espionage espionage : espionages) {
			espionageDTOs.add(convertEspionageToEspionageDTO(espionage,convertCriteria));
		}
		
		return espionageDTOs;

	}
	
	public EspionageDTO convertEspionageToEspionageDTO(Espionage espionage, EspionageConvertCriteriaDTO convertCriteria) {
		
		EspionageDTO espionageDTO = new EspionageDTO();

		espionageDTO.setEspionageId(espionage.getEspionageId());

		espionageDTO.setOperationsConducted(espionage.getOperationsConducted());
		
		return espionageDTO;
	}

	public ResultDTO updateEspionage(EspionageDTO espionageDTO, RequestDTO requestDTO) {
		
		Espionage espionage = espionageDao.getById(espionageDTO.getEspionageId());
		
		espionage.setEspionageId(ControllerUtils.setValue(espionage.getEspionageId(), espionageDTO.getEspionageId()));
		
		espionage.setOperationsConducted(ControllerUtils.setValue(espionage.getOperationsConducted(), espionageDTO.getOperationsConducted()));

        espionage = espionageDao.save(espionage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EspionageDTO getEspionageDTOById(Integer espionageId) {
	
		Espionage espionage = espionageDao.getById(espionageId);
		
		EspionageConvertCriteriaDTO convertCriteria = new EspionageConvertCriteriaDTO();
		return(this.convertEspionageToEspionageDTO(espionage,convertCriteria));
	}

}
