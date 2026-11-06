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
import com.worldparliamentsimulator.dao.ContinentProblemDAO;
import com.worldparliamentsimulator.domain.ContinentProblem;
import com.worldparliamentsimulator.dto.ContinentProblemDTO;
import com.worldparliamentsimulator.dto.ContinentProblemSearchDTO;
import com.worldparliamentsimulator.dto.ContinentProblemPageDTO;
import com.worldparliamentsimulator.dto.ContinentProblemConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.ContinentProblemService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class ContinentProblemServiceImpl extends GenericServiceImpl<ContinentProblem, Integer> implements ContinentProblemService {

    private final static Logger logger = LoggerFactory.getLogger(ContinentProblemServiceImpl.class);

	@Autowired
	ContinentProblemDAO continentProblemDao;

	

	@Override
	public GenericDAO<ContinentProblem, Integer> getDAO() {
		return (GenericDAO<ContinentProblem, Integer>) continentProblemDao;
	}
	
	public List<ContinentProblem> findAll () {
		List<ContinentProblem> continentProblems = continentProblemDao.findAll();
		
		return continentProblems;	
		
	}

	public ResultDTO addContinentProblem(ContinentProblemDTO continentProblemDTO, RequestDTO requestDTO) {

		ContinentProblem continentProblem = new ContinentProblem();

		continentProblem.setContinentProblemId(continentProblemDTO.getContinentProblemId());

		continentProblem.setContinent(continentProblemDTO.getContinent());

		continentProblem.setIssues(continentProblemDTO.getIssues());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		continentProblem = continentProblemDao.save(continentProblem);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ContinentProblem> getAllContinentProblems(Pageable pageable) {
		return continentProblemDao.findAll(pageable);
	}

	public Page<ContinentProblem> getAllContinentProblems(Specification<ContinentProblem> spec, Pageable pageable) {
		return continentProblemDao.findAll(spec, pageable);
	}

	public ResponseEntity<ContinentProblemPageDTO> getContinentProblems(ContinentProblemSearchDTO continentProblemSearchDTO) {
	
			Integer continentProblemId = continentProblemSearchDTO.getContinentProblemId(); 
 			String continent = continentProblemSearchDTO.getContinent(); 
 			String issues = continentProblemSearchDTO.getIssues(); 
 			String sortBy = continentProblemSearchDTO.getSortBy();
			String sortOrder = continentProblemSearchDTO.getSortOrder();
			String searchQuery = continentProblemSearchDTO.getSearchQuery();
			Integer page = continentProblemSearchDTO.getPage();
			Integer size = continentProblemSearchDTO.getSize();

	        Specification<ContinentProblem> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, continentProblemId, "continentProblemId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, continent, "continent"); 
			
			spec = ControllerUtils.andIfNecessary(spec, issues, "issues"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("continent")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("issues")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ContinentProblem> continentProblems = this.getAllContinentProblems(spec, pageable);
		
		//System.out.println(String.valueOf(continentProblems.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(continentProblems.getTotalPages()));
		
		List<ContinentProblem> continentProblemsList = continentProblems.getContent();
		
		ContinentProblemConvertCriteriaDTO convertCriteria = new ContinentProblemConvertCriteriaDTO();
		List<ContinentProblemDTO> continentProblemDTOs = this.convertContinentProblemsToContinentProblemDTOs(continentProblemsList,convertCriteria);
		
		ContinentProblemPageDTO continentProblemPageDTO = new ContinentProblemPageDTO();
		continentProblemPageDTO.setContinentProblems(continentProblemDTOs);
		continentProblemPageDTO.setTotalElements(continentProblems.getTotalElements());
		return ResponseEntity.ok(continentProblemPageDTO);
	}

	public List<ContinentProblemDTO> convertContinentProblemsToContinentProblemDTOs(List<ContinentProblem> continentProblems, ContinentProblemConvertCriteriaDTO convertCriteria) {
		
		List<ContinentProblemDTO> continentProblemDTOs = new ArrayList<ContinentProblemDTO>();
		
		for (ContinentProblem continentProblem : continentProblems) {
			continentProblemDTOs.add(convertContinentProblemToContinentProblemDTO(continentProblem,convertCriteria));
		}
		
		return continentProblemDTOs;

	}
	
	public ContinentProblemDTO convertContinentProblemToContinentProblemDTO(ContinentProblem continentProblem, ContinentProblemConvertCriteriaDTO convertCriteria) {
		
		ContinentProblemDTO continentProblemDTO = new ContinentProblemDTO();

		continentProblemDTO.setContinentProblemId(continentProblem.getContinentProblemId());

		continentProblemDTO.setContinent(continentProblem.getContinent());

		continentProblemDTO.setIssues(continentProblem.getIssues());
		
		return continentProblemDTO;
	}

	public ResultDTO updateContinentProblem(ContinentProblemDTO continentProblemDTO, RequestDTO requestDTO) {
		
		ContinentProblem continentProblem = continentProblemDao.getById(continentProblemDTO.getContinentProblemId());
		
		continentProblem.setContinentProblemId(ControllerUtils.setValue(continentProblem.getContinentProblemId(), continentProblemDTO.getContinentProblemId()));
		
		continentProblem.setContinent(ControllerUtils.setValue(continentProblem.getContinent(), continentProblemDTO.getContinent()));
		
		continentProblem.setIssues(ControllerUtils.setValue(continentProblem.getIssues(), continentProblemDTO.getIssues()));

        continentProblem = continentProblemDao.save(continentProblem);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ContinentProblemDTO getContinentProblemDTOById(Integer continentProblemId) {
	
		ContinentProblem continentProblem = continentProblemDao.getById(continentProblemId);
		
		ContinentProblemConvertCriteriaDTO convertCriteria = new ContinentProblemConvertCriteriaDTO();
		return(this.convertContinentProblemToContinentProblemDTO(continentProblem,convertCriteria));
	}

}
