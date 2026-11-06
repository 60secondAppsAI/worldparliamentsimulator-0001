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
import com.worldparliamentsimulator.dao.EconomyDAO;
import com.worldparliamentsimulator.domain.Economy;
import com.worldparliamentsimulator.dto.EconomyDTO;
import com.worldparliamentsimulator.dto.EconomySearchDTO;
import com.worldparliamentsimulator.dto.EconomyPageDTO;
import com.worldparliamentsimulator.dto.EconomyConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.EconomyService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class EconomyServiceImpl extends GenericServiceImpl<Economy, Integer> implements EconomyService {

    private final static Logger logger = LoggerFactory.getLogger(EconomyServiceImpl.class);

	@Autowired
	EconomyDAO economyDao;

	

	@Override
	public GenericDAO<Economy, Integer> getDAO() {
		return (GenericDAO<Economy, Integer>) economyDao;
	}
	
	public List<Economy> findAll () {
		List<Economy> economys = economyDao.findAll();
		
		return economys;	
		
	}

	public ResultDTO addEconomy(EconomyDTO economyDTO, RequestDTO requestDTO) {

		Economy economy = new Economy();

		economy.setEconomyId(economyDTO.getEconomyId());

		economy.setInvestmentInEducation(economyDTO.getInvestmentInEducation());

		economy.setInvestmentInHealth(economyDTO.getInvestmentInHealth());

		economy.setInvestmentInFactories(economyDTO.getInvestmentInFactories());

		economy.setMilitarySpending(economyDTO.getMilitarySpending());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		economy = economyDao.save(economy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Economy> getAllEconomys(Pageable pageable) {
		return economyDao.findAll(pageable);
	}

	public Page<Economy> getAllEconomys(Specification<Economy> spec, Pageable pageable) {
		return economyDao.findAll(spec, pageable);
	}

	public ResponseEntity<EconomyPageDTO> getEconomys(EconomySearchDTO economySearchDTO) {
	
			Integer economyId = economySearchDTO.getEconomyId(); 
     			String sortBy = economySearchDTO.getSortBy();
			String sortOrder = economySearchDTO.getSortOrder();
			String searchQuery = economySearchDTO.getSearchQuery();
			Integer page = economySearchDTO.getPage();
			Integer size = economySearchDTO.getSize();

	        Specification<Economy> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, economyId, "economyId"); 
			
			
			
			
			

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

		Page<Economy> economys = this.getAllEconomys(spec, pageable);
		
		//System.out.println(String.valueOf(economys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(economys.getTotalPages()));
		
		List<Economy> economysList = economys.getContent();
		
		EconomyConvertCriteriaDTO convertCriteria = new EconomyConvertCriteriaDTO();
		List<EconomyDTO> economyDTOs = this.convertEconomysToEconomyDTOs(economysList,convertCriteria);
		
		EconomyPageDTO economyPageDTO = new EconomyPageDTO();
		economyPageDTO.setEconomys(economyDTOs);
		economyPageDTO.setTotalElements(economys.getTotalElements());
		return ResponseEntity.ok(economyPageDTO);
	}

	public List<EconomyDTO> convertEconomysToEconomyDTOs(List<Economy> economys, EconomyConvertCriteriaDTO convertCriteria) {
		
		List<EconomyDTO> economyDTOs = new ArrayList<EconomyDTO>();
		
		for (Economy economy : economys) {
			economyDTOs.add(convertEconomyToEconomyDTO(economy,convertCriteria));
		}
		
		return economyDTOs;

	}
	
	public EconomyDTO convertEconomyToEconomyDTO(Economy economy, EconomyConvertCriteriaDTO convertCriteria) {
		
		EconomyDTO economyDTO = new EconomyDTO();

		economyDTO.setEconomyId(economy.getEconomyId());

		economyDTO.setInvestmentInEducation(economy.getInvestmentInEducation());

		economyDTO.setInvestmentInHealth(economy.getInvestmentInHealth());

		economyDTO.setInvestmentInFactories(economy.getInvestmentInFactories());

		economyDTO.setMilitarySpending(economy.getMilitarySpending());
		
		return economyDTO;
	}

	public ResultDTO updateEconomy(EconomyDTO economyDTO, RequestDTO requestDTO) {
		
		Economy economy = economyDao.getById(economyDTO.getEconomyId());
		
		economy.setEconomyId(ControllerUtils.setValue(economy.getEconomyId(), economyDTO.getEconomyId()));
		
		economy.setInvestmentInEducation(ControllerUtils.setValue(economy.getInvestmentInEducation(), economyDTO.getInvestmentInEducation()));
		
		economy.setInvestmentInHealth(ControllerUtils.setValue(economy.getInvestmentInHealth(), economyDTO.getInvestmentInHealth()));
		
		economy.setInvestmentInFactories(ControllerUtils.setValue(economy.getInvestmentInFactories(), economyDTO.getInvestmentInFactories()));
		
		economy.setMilitarySpending(ControllerUtils.setValue(economy.getMilitarySpending(), economyDTO.getMilitarySpending()));

        economy = economyDao.save(economy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EconomyDTO getEconomyDTOById(Integer economyId) {
	
		Economy economy = economyDao.getById(economyId);
		
		EconomyConvertCriteriaDTO convertCriteria = new EconomyConvertCriteriaDTO();
		return(this.convertEconomyToEconomyDTO(economy,convertCriteria));
	}

}
