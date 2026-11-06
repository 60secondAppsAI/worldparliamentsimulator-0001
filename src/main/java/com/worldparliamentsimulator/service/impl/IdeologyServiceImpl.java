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
import com.worldparliamentsimulator.dao.IdeologyDAO;
import com.worldparliamentsimulator.domain.Ideology;
import com.worldparliamentsimulator.dto.IdeologyDTO;
import com.worldparliamentsimulator.dto.IdeologySearchDTO;
import com.worldparliamentsimulator.dto.IdeologyPageDTO;
import com.worldparliamentsimulator.dto.IdeologyConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.IdeologyService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class IdeologyServiceImpl extends GenericServiceImpl<Ideology, Integer> implements IdeologyService {

    private final static Logger logger = LoggerFactory.getLogger(IdeologyServiceImpl.class);

	@Autowired
	IdeologyDAO ideologyDao;

	

	@Override
	public GenericDAO<Ideology, Integer> getDAO() {
		return (GenericDAO<Ideology, Integer>) ideologyDao;
	}
	
	public List<Ideology> findAll () {
		List<Ideology> ideologys = ideologyDao.findAll();
		
		return ideologys;	
		
	}

	public ResultDTO addIdeology(IdeologyDTO ideologyDTO, RequestDTO requestDTO) {

		Ideology ideology = new Ideology();

		ideology.setIdeologyId(ideologyDTO.getIdeologyId());

		ideology.setName(ideologyDTO.getName());

		ideology.setTaxRate(ideologyDTO.getTaxRate());

		ideology.setPublicReaction(ideologyDTO.getPublicReaction());

		ideology.setArmySupport(ideologyDTO.getArmySupport());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		ideology = ideologyDao.save(ideology);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Ideology> getAllIdeologys(Pageable pageable) {
		return ideologyDao.findAll(pageable);
	}

	public Page<Ideology> getAllIdeologys(Specification<Ideology> spec, Pageable pageable) {
		return ideologyDao.findAll(spec, pageable);
	}

	public ResponseEntity<IdeologyPageDTO> getIdeologys(IdeologySearchDTO ideologySearchDTO) {
	
			Integer ideologyId = ideologySearchDTO.getIdeologyId(); 
 			String name = ideologySearchDTO.getName(); 
  			String publicReaction = ideologySearchDTO.getPublicReaction(); 
 			String armySupport = ideologySearchDTO.getArmySupport(); 
 			String sortBy = ideologySearchDTO.getSortBy();
			String sortOrder = ideologySearchDTO.getSortOrder();
			String searchQuery = ideologySearchDTO.getSearchQuery();
			Integer page = ideologySearchDTO.getPage();
			Integer size = ideologySearchDTO.getSize();

	        Specification<Ideology> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, ideologyId, "ideologyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, publicReaction, "publicReaction"); 
			
			spec = ControllerUtils.andIfNecessary(spec, armySupport, "armySupport"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("publicReaction")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("armySupport")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Ideology> ideologys = this.getAllIdeologys(spec, pageable);
		
		//System.out.println(String.valueOf(ideologys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(ideologys.getTotalPages()));
		
		List<Ideology> ideologysList = ideologys.getContent();
		
		IdeologyConvertCriteriaDTO convertCriteria = new IdeologyConvertCriteriaDTO();
		List<IdeologyDTO> ideologyDTOs = this.convertIdeologysToIdeologyDTOs(ideologysList,convertCriteria);
		
		IdeologyPageDTO ideologyPageDTO = new IdeologyPageDTO();
		ideologyPageDTO.setIdeologys(ideologyDTOs);
		ideologyPageDTO.setTotalElements(ideologys.getTotalElements());
		return ResponseEntity.ok(ideologyPageDTO);
	}

	public List<IdeologyDTO> convertIdeologysToIdeologyDTOs(List<Ideology> ideologys, IdeologyConvertCriteriaDTO convertCriteria) {
		
		List<IdeologyDTO> ideologyDTOs = new ArrayList<IdeologyDTO>();
		
		for (Ideology ideology : ideologys) {
			ideologyDTOs.add(convertIdeologyToIdeologyDTO(ideology,convertCriteria));
		}
		
		return ideologyDTOs;

	}
	
	public IdeologyDTO convertIdeologyToIdeologyDTO(Ideology ideology, IdeologyConvertCriteriaDTO convertCriteria) {
		
		IdeologyDTO ideologyDTO = new IdeologyDTO();

		ideologyDTO.setIdeologyId(ideology.getIdeologyId());

		ideologyDTO.setName(ideology.getName());

		ideologyDTO.setTaxRate(ideology.getTaxRate());

		ideologyDTO.setPublicReaction(ideology.getPublicReaction());

		ideologyDTO.setArmySupport(ideology.getArmySupport());
		
		return ideologyDTO;
	}

	public ResultDTO updateIdeology(IdeologyDTO ideologyDTO, RequestDTO requestDTO) {
		
		Ideology ideology = ideologyDao.getById(ideologyDTO.getIdeologyId());
		
		ideology.setIdeologyId(ControllerUtils.setValue(ideology.getIdeologyId(), ideologyDTO.getIdeologyId()));
		
		ideology.setName(ControllerUtils.setValue(ideology.getName(), ideologyDTO.getName()));
		
		ideology.setTaxRate(ControllerUtils.setValue(ideology.getTaxRate(), ideologyDTO.getTaxRate()));
		
		ideology.setPublicReaction(ControllerUtils.setValue(ideology.getPublicReaction(), ideologyDTO.getPublicReaction()));
		
		ideology.setArmySupport(ControllerUtils.setValue(ideology.getArmySupport(), ideologyDTO.getArmySupport()));

        ideology = ideologyDao.save(ideology);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public IdeologyDTO getIdeologyDTOById(Integer ideologyId) {
	
		Ideology ideology = ideologyDao.getById(ideologyId);
		
		IdeologyConvertCriteriaDTO convertCriteria = new IdeologyConvertCriteriaDTO();
		return(this.convertIdeologyToIdeologyDTO(ideology,convertCriteria));
	}

}
