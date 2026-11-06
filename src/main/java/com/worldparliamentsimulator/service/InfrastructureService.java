package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Infrastructure;
import com.worldparliamentsimulator.dto.InfrastructureDTO;
import com.worldparliamentsimulator.dto.InfrastructureSearchDTO;
import com.worldparliamentsimulator.dto.InfrastructurePageDTO;
import com.worldparliamentsimulator.dto.InfrastructureConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InfrastructureService extends GenericService<Infrastructure, Integer> {

	List<Infrastructure> findAll();

	ResultDTO addInfrastructure(InfrastructureDTO infrastructureDTO, RequestDTO requestDTO);

	ResultDTO updateInfrastructure(InfrastructureDTO infrastructureDTO, RequestDTO requestDTO);

    Page<Infrastructure> getAllInfrastructures(Pageable pageable);

    Page<Infrastructure> getAllInfrastructures(Specification<Infrastructure> spec, Pageable pageable);

	ResponseEntity<InfrastructurePageDTO> getInfrastructures(InfrastructureSearchDTO infrastructureSearchDTO);
	
	List<InfrastructureDTO> convertInfrastructuresToInfrastructureDTOs(List<Infrastructure> infrastructures, InfrastructureConvertCriteriaDTO convertCriteria);

	InfrastructureDTO getInfrastructureDTOById(Integer infrastructureId);



	
}
