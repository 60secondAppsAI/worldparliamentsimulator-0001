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
import com.worldparliamentsimulator.dao.DiplomacyDAO;
import com.worldparliamentsimulator.domain.Diplomacy;
import com.worldparliamentsimulator.dto.DiplomacyDTO;
import com.worldparliamentsimulator.dto.DiplomacySearchDTO;
import com.worldparliamentsimulator.dto.DiplomacyPageDTO;
import com.worldparliamentsimulator.dto.DiplomacyConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.DiplomacyService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class DiplomacyServiceImpl extends GenericServiceImpl<Diplomacy, Integer> implements DiplomacyService {

    private final static Logger logger = LoggerFactory.getLogger(DiplomacyServiceImpl.class);

	@Autowired
	DiplomacyDAO diplomacyDao;

	

	@Override
	public GenericDAO<Diplomacy, Integer> getDAO() {
		return (GenericDAO<Diplomacy, Integer>) diplomacyDao;
	}
	
	public List<Diplomacy> findAll () {
		List<Diplomacy> diplomacys = diplomacyDao.findAll();
		
		return diplomacys;	
		
	}

	public ResultDTO addDiplomacy(DiplomacyDTO diplomacyDTO, RequestDTO requestDTO) {

		Diplomacy diplomacy = new Diplomacy();

		diplomacy.setDiplomacyId(diplomacyDTO.getDiplomacyId());

		diplomacy.setAlliancesFormed(diplomacyDTO.getAlliancesFormed());

		diplomacy.setWarsDeclared(diplomacyDTO.getWarsDeclared());

		diplomacy.setPeaceTreatiesSigned(diplomacyDTO.getPeaceTreatiesSigned());

		diplomacy.setEconomicEmbargos(diplomacyDTO.getEconomicEmbargos());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		diplomacy = diplomacyDao.save(diplomacy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Diplomacy> getAllDiplomacys(Pageable pageable) {
		return diplomacyDao.findAll(pageable);
	}

	public Page<Diplomacy> getAllDiplomacys(Specification<Diplomacy> spec, Pageable pageable) {
		return diplomacyDao.findAll(spec, pageable);
	}

	public ResponseEntity<DiplomacyPageDTO> getDiplomacys(DiplomacySearchDTO diplomacySearchDTO) {
	
			Integer diplomacyId = diplomacySearchDTO.getDiplomacyId(); 
     			String sortBy = diplomacySearchDTO.getSortBy();
			String sortOrder = diplomacySearchDTO.getSortOrder();
			String searchQuery = diplomacySearchDTO.getSearchQuery();
			Integer page = diplomacySearchDTO.getPage();
			Integer size = diplomacySearchDTO.getSize();

	        Specification<Diplomacy> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, diplomacyId, "diplomacyId"); 
			
			
			
			
			

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

		Page<Diplomacy> diplomacys = this.getAllDiplomacys(spec, pageable);
		
		//System.out.println(String.valueOf(diplomacys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(diplomacys.getTotalPages()));
		
		List<Diplomacy> diplomacysList = diplomacys.getContent();
		
		DiplomacyConvertCriteriaDTO convertCriteria = new DiplomacyConvertCriteriaDTO();
		List<DiplomacyDTO> diplomacyDTOs = this.convertDiplomacysToDiplomacyDTOs(diplomacysList,convertCriteria);
		
		DiplomacyPageDTO diplomacyPageDTO = new DiplomacyPageDTO();
		diplomacyPageDTO.setDiplomacys(diplomacyDTOs);
		diplomacyPageDTO.setTotalElements(diplomacys.getTotalElements());
		return ResponseEntity.ok(diplomacyPageDTO);
	}

	public List<DiplomacyDTO> convertDiplomacysToDiplomacyDTOs(List<Diplomacy> diplomacys, DiplomacyConvertCriteriaDTO convertCriteria) {
		
		List<DiplomacyDTO> diplomacyDTOs = new ArrayList<DiplomacyDTO>();
		
		for (Diplomacy diplomacy : diplomacys) {
			diplomacyDTOs.add(convertDiplomacyToDiplomacyDTO(diplomacy,convertCriteria));
		}
		
		return diplomacyDTOs;

	}
	
	public DiplomacyDTO convertDiplomacyToDiplomacyDTO(Diplomacy diplomacy, DiplomacyConvertCriteriaDTO convertCriteria) {
		
		DiplomacyDTO diplomacyDTO = new DiplomacyDTO();

		diplomacyDTO.setDiplomacyId(diplomacy.getDiplomacyId());

		diplomacyDTO.setAlliancesFormed(diplomacy.getAlliancesFormed());

		diplomacyDTO.setWarsDeclared(diplomacy.getWarsDeclared());

		diplomacyDTO.setPeaceTreatiesSigned(diplomacy.getPeaceTreatiesSigned());

		diplomacyDTO.setEconomicEmbargos(diplomacy.getEconomicEmbargos());
		
		return diplomacyDTO;
	}

	public ResultDTO updateDiplomacy(DiplomacyDTO diplomacyDTO, RequestDTO requestDTO) {
		
		Diplomacy diplomacy = diplomacyDao.getById(diplomacyDTO.getDiplomacyId());
		
		diplomacy.setDiplomacyId(ControllerUtils.setValue(diplomacy.getDiplomacyId(), diplomacyDTO.getDiplomacyId()));
		
		diplomacy.setAlliancesFormed(ControllerUtils.setValue(diplomacy.getAlliancesFormed(), diplomacyDTO.getAlliancesFormed()));
		
		diplomacy.setWarsDeclared(ControllerUtils.setValue(diplomacy.getWarsDeclared(), diplomacyDTO.getWarsDeclared()));
		
		diplomacy.setPeaceTreatiesSigned(ControllerUtils.setValue(diplomacy.getPeaceTreatiesSigned(), diplomacyDTO.getPeaceTreatiesSigned()));
		
		diplomacy.setEconomicEmbargos(ControllerUtils.setValue(diplomacy.getEconomicEmbargos(), diplomacyDTO.getEconomicEmbargos()));

        diplomacy = diplomacyDao.save(diplomacy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DiplomacyDTO getDiplomacyDTOById(Integer diplomacyId) {
	
		Diplomacy diplomacy = diplomacyDao.getById(diplomacyId);
		
		DiplomacyConvertCriteriaDTO convertCriteria = new DiplomacyConvertCriteriaDTO();
		return(this.convertDiplomacyToDiplomacyDTO(diplomacy,convertCriteria));
	}

}
