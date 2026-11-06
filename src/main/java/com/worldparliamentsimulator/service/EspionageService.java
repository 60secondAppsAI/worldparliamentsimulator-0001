package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Espionage;
import com.worldparliamentsimulator.dto.EspionageDTO;
import com.worldparliamentsimulator.dto.EspionageSearchDTO;
import com.worldparliamentsimulator.dto.EspionagePageDTO;
import com.worldparliamentsimulator.dto.EspionageConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EspionageService extends GenericService<Espionage, Integer> {

	List<Espionage> findAll();

	ResultDTO addEspionage(EspionageDTO espionageDTO, RequestDTO requestDTO);

	ResultDTO updateEspionage(EspionageDTO espionageDTO, RequestDTO requestDTO);

    Page<Espionage> getAllEspionages(Pageable pageable);

    Page<Espionage> getAllEspionages(Specification<Espionage> spec, Pageable pageable);

	ResponseEntity<EspionagePageDTO> getEspionages(EspionageSearchDTO espionageSearchDTO);
	
	List<EspionageDTO> convertEspionagesToEspionageDTOs(List<Espionage> espionages, EspionageConvertCriteriaDTO convertCriteria);

	EspionageDTO getEspionageDTOById(Integer espionageId);



	
}
