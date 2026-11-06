package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Military;
import com.worldparliamentsimulator.dto.MilitaryDTO;
import com.worldparliamentsimulator.dto.MilitarySearchDTO;
import com.worldparliamentsimulator.dto.MilitaryPageDTO;
import com.worldparliamentsimulator.dto.MilitaryConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MilitaryService extends GenericService<Military, Integer> {

	List<Military> findAll();

	ResultDTO addMilitary(MilitaryDTO militaryDTO, RequestDTO requestDTO);

	ResultDTO updateMilitary(MilitaryDTO militaryDTO, RequestDTO requestDTO);

    Page<Military> getAllMilitarys(Pageable pageable);

    Page<Military> getAllMilitarys(Specification<Military> spec, Pageable pageable);

	ResponseEntity<MilitaryPageDTO> getMilitarys(MilitarySearchDTO militarySearchDTO);
	
	List<MilitaryDTO> convertMilitarysToMilitaryDTOs(List<Military> militarys, MilitaryConvertCriteriaDTO convertCriteria);

	MilitaryDTO getMilitaryDTOById(Integer militaryId);



	
}
