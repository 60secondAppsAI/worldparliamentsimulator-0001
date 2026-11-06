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
import com.worldparliamentsimulator.dao.CountryDAO;
import com.worldparliamentsimulator.domain.Country;
import com.worldparliamentsimulator.dto.CountryDTO;
import com.worldparliamentsimulator.dto.CountrySearchDTO;
import com.worldparliamentsimulator.dto.CountryPageDTO;
import com.worldparliamentsimulator.dto.CountryConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.CountryService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class CountryServiceImpl extends GenericServiceImpl<Country, Integer> implements CountryService {

    private final static Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	@Autowired
	CountryDAO countryDao;

	

	@Override
	public GenericDAO<Country, Integer> getDAO() {
		return (GenericDAO<Country, Integer>) countryDao;
	}
	
	public List<Country> findAll () {
		List<Country> countrys = countryDao.findAll();
		
		return countrys;	
		
	}

	public ResultDTO addCountry(CountryDTO countryDTO, RequestDTO requestDTO) {

		Country country = new Country();

		country.setCountryId(countryDTO.getCountryId());

		country.setName(countryDTO.getName());

		country.setContinent(countryDTO.getContinent());

		country.setFlagEmoji(countryDTO.getFlagEmoji());

		country.setCapital(countryDTO.getCapital());

		country.setGdp(countryDTO.getGdp());

		country.setPopulation(countryDTO.getPopulation());

		country.setArmySize(countryDTO.getArmySize());

		country.setResources(countryDTO.getResources());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		country = countryDao.save(country);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Country> getAllCountrys(Pageable pageable) {
		return countryDao.findAll(pageable);
	}

	public Page<Country> getAllCountrys(Specification<Country> spec, Pageable pageable) {
		return countryDao.findAll(spec, pageable);
	}

	public ResponseEntity<CountryPageDTO> getCountrys(CountrySearchDTO countrySearchDTO) {
	
			Integer countryId = countrySearchDTO.getCountryId(); 
 			String name = countrySearchDTO.getName(); 
 			String continent = countrySearchDTO.getContinent(); 
 			String flagEmoji = countrySearchDTO.getFlagEmoji(); 
 			String capital = countrySearchDTO.getCapital(); 
    			String resources = countrySearchDTO.getResources(); 
 			String sortBy = countrySearchDTO.getSortBy();
			String sortOrder = countrySearchDTO.getSortOrder();
			String searchQuery = countrySearchDTO.getSearchQuery();
			Integer page = countrySearchDTO.getPage();
			Integer size = countrySearchDTO.getSize();

	        Specification<Country> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, countryId, "countryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, continent, "continent"); 
			
			spec = ControllerUtils.andIfNecessary(spec, flagEmoji, "flagEmoji"); 
			
			spec = ControllerUtils.andIfNecessary(spec, capital, "capital"); 
			
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, resources, "resources"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("continent")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("flagEmoji")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("capital")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("resources")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Country> countrys = this.getAllCountrys(spec, pageable);
		
		//System.out.println(String.valueOf(countrys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(countrys.getTotalPages()));
		
		List<Country> countrysList = countrys.getContent();
		
		CountryConvertCriteriaDTO convertCriteria = new CountryConvertCriteriaDTO();
		List<CountryDTO> countryDTOs = this.convertCountrysToCountryDTOs(countrysList,convertCriteria);
		
		CountryPageDTO countryPageDTO = new CountryPageDTO();
		countryPageDTO.setCountrys(countryDTOs);
		countryPageDTO.setTotalElements(countrys.getTotalElements());
		return ResponseEntity.ok(countryPageDTO);
	}

	public List<CountryDTO> convertCountrysToCountryDTOs(List<Country> countrys, CountryConvertCriteriaDTO convertCriteria) {
		
		List<CountryDTO> countryDTOs = new ArrayList<CountryDTO>();
		
		for (Country country : countrys) {
			countryDTOs.add(convertCountryToCountryDTO(country,convertCriteria));
		}
		
		return countryDTOs;

	}
	
	public CountryDTO convertCountryToCountryDTO(Country country, CountryConvertCriteriaDTO convertCriteria) {
		
		CountryDTO countryDTO = new CountryDTO();

		countryDTO.setCountryId(country.getCountryId());

		countryDTO.setName(country.getName());

		countryDTO.setContinent(country.getContinent());

		countryDTO.setFlagEmoji(country.getFlagEmoji());

		countryDTO.setCapital(country.getCapital());

		countryDTO.setGdp(country.getGdp());

		countryDTO.setPopulation(country.getPopulation());

		countryDTO.setArmySize(country.getArmySize());

		countryDTO.setResources(country.getResources());
		
		return countryDTO;
	}

	public ResultDTO updateCountry(CountryDTO countryDTO, RequestDTO requestDTO) {
		
		Country country = countryDao.getById(countryDTO.getCountryId());
		
		country.setCountryId(ControllerUtils.setValue(country.getCountryId(), countryDTO.getCountryId()));
		
		country.setName(ControllerUtils.setValue(country.getName(), countryDTO.getName()));
		
		country.setContinent(ControllerUtils.setValue(country.getContinent(), countryDTO.getContinent()));
		
		country.setFlagEmoji(ControllerUtils.setValue(country.getFlagEmoji(), countryDTO.getFlagEmoji()));
		
		country.setCapital(ControllerUtils.setValue(country.getCapital(), countryDTO.getCapital()));
		
		country.setGdp(ControllerUtils.setValue(country.getGdp(), countryDTO.getGdp()));
		
		country.setPopulation(ControllerUtils.setValue(country.getPopulation(), countryDTO.getPopulation()));
		
		country.setArmySize(ControllerUtils.setValue(country.getArmySize(), countryDTO.getArmySize()));
		
		country.setResources(ControllerUtils.setValue(country.getResources(), countryDTO.getResources()));

        country = countryDao.save(country);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CountryDTO getCountryDTOById(Integer countryId) {
	
		Country country = countryDao.getById(countryId);
		
		CountryConvertCriteriaDTO convertCriteria = new CountryConvertCriteriaDTO();
		return(this.convertCountryToCountryDTO(country,convertCriteria));
	}

}
