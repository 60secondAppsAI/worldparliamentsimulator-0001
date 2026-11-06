package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.ContinentProblem;
import com.worldparliamentsimulator.dto.ContinentProblemDTO;
import com.worldparliamentsimulator.dto.ContinentProblemSearchDTO;
import com.worldparliamentsimulator.dto.ContinentProblemPageDTO;
import com.worldparliamentsimulator.dto.ContinentProblemConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ContinentProblemService extends GenericService<ContinentProblem, Integer> {

	List<ContinentProblem> findAll();

	ResultDTO addContinentProblem(ContinentProblemDTO continentProblemDTO, RequestDTO requestDTO);

	ResultDTO updateContinentProblem(ContinentProblemDTO continentProblemDTO, RequestDTO requestDTO);

    Page<ContinentProblem> getAllContinentProblems(Pageable pageable);

    Page<ContinentProblem> getAllContinentProblems(Specification<ContinentProblem> spec, Pageable pageable);

	ResponseEntity<ContinentProblemPageDTO> getContinentProblems(ContinentProblemSearchDTO continentProblemSearchDTO);
	
	List<ContinentProblemDTO> convertContinentProblemsToContinentProblemDTOs(List<ContinentProblem> continentProblems, ContinentProblemConvertCriteriaDTO convertCriteria);

	ContinentProblemDTO getContinentProblemDTOById(Integer continentProblemId);



	
}
