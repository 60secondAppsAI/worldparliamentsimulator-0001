package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Country;
import com.worldparliamentsimulator.dto.CountryDTO;
import com.worldparliamentsimulator.dto.CountrySearchDTO;
import com.worldparliamentsimulator.dto.CountryPageDTO;
import com.worldparliamentsimulator.dto.CountryConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CountryService extends GenericService<Country, Integer> {

	List<Country> findAll();

	ResultDTO addCountry(CountryDTO countryDTO, RequestDTO requestDTO);

	ResultDTO updateCountry(CountryDTO countryDTO, RequestDTO requestDTO);

    Page<Country> getAllCountrys(Pageable pageable);

    Page<Country> getAllCountrys(Specification<Country> spec, Pageable pageable);

	ResponseEntity<CountryPageDTO> getCountrys(CountrySearchDTO countrySearchDTO);
	
	List<CountryDTO> convertCountrysToCountryDTOs(List<Country> countrys, CountryConvertCriteriaDTO convertCriteria);

	CountryDTO getCountryDTOById(Integer countryId);



	
}
