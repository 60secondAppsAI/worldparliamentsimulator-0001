package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.People;
import com.worldparliamentsimulator.dto.PeopleDTO;
import com.worldparliamentsimulator.dto.PeopleSearchDTO;
import com.worldparliamentsimulator.dto.PeoplePageDTO;
import com.worldparliamentsimulator.dto.PeopleConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PeopleService extends GenericService<People, Integer> {

	List<People> findAll();

	ResultDTO addPeople(PeopleDTO peopleDTO, RequestDTO requestDTO);

	ResultDTO updatePeople(PeopleDTO peopleDTO, RequestDTO requestDTO);

    Page<People> getAllPeoples(Pageable pageable);

    Page<People> getAllPeoples(Specification<People> spec, Pageable pageable);

	ResponseEntity<PeoplePageDTO> getPeoples(PeopleSearchDTO peopleSearchDTO);
	
	List<PeopleDTO> convertPeoplesToPeopleDTOs(List<People> peoples, PeopleConvertCriteriaDTO convertCriteria);

	PeopleDTO getPeopleDTOById(Integer peopleId);



	
}
