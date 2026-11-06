package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Alliance;
import com.worldparliamentsimulator.dto.AllianceDTO;
import com.worldparliamentsimulator.dto.AllianceSearchDTO;
import com.worldparliamentsimulator.dto.AlliancePageDTO;
import com.worldparliamentsimulator.dto.AllianceConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AllianceService extends GenericService<Alliance, Integer> {

	List<Alliance> findAll();

	ResultDTO addAlliance(AllianceDTO allianceDTO, RequestDTO requestDTO);

	ResultDTO updateAlliance(AllianceDTO allianceDTO, RequestDTO requestDTO);

    Page<Alliance> getAllAlliances(Pageable pageable);

    Page<Alliance> getAllAlliances(Specification<Alliance> spec, Pageable pageable);

	ResponseEntity<AlliancePageDTO> getAlliances(AllianceSearchDTO allianceSearchDTO);
	
	List<AllianceDTO> convertAlliancesToAllianceDTOs(List<Alliance> alliances, AllianceConvertCriteriaDTO convertCriteria);

	AllianceDTO getAllianceDTOById(Integer allianceId);



	
}
