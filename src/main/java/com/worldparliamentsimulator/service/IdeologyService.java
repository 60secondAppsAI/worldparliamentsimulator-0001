package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Ideology;
import com.worldparliamentsimulator.dto.IdeologyDTO;
import com.worldparliamentsimulator.dto.IdeologySearchDTO;
import com.worldparliamentsimulator.dto.IdeologyPageDTO;
import com.worldparliamentsimulator.dto.IdeologyConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface IdeologyService extends GenericService<Ideology, Integer> {

	List<Ideology> findAll();

	ResultDTO addIdeology(IdeologyDTO ideologyDTO, RequestDTO requestDTO);

	ResultDTO updateIdeology(IdeologyDTO ideologyDTO, RequestDTO requestDTO);

    Page<Ideology> getAllIdeologys(Pageable pageable);

    Page<Ideology> getAllIdeologys(Specification<Ideology> spec, Pageable pageable);

	ResponseEntity<IdeologyPageDTO> getIdeologys(IdeologySearchDTO ideologySearchDTO);
	
	List<IdeologyDTO> convertIdeologysToIdeologyDTOs(List<Ideology> ideologys, IdeologyConvertCriteriaDTO convertCriteria);

	IdeologyDTO getIdeologyDTOById(Integer ideologyId);



	
}
