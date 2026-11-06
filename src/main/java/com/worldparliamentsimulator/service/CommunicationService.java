package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Communication;
import com.worldparliamentsimulator.dto.CommunicationDTO;
import com.worldparliamentsimulator.dto.CommunicationSearchDTO;
import com.worldparliamentsimulator.dto.CommunicationPageDTO;
import com.worldparliamentsimulator.dto.CommunicationConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CommunicationService extends GenericService<Communication, Integer> {

	List<Communication> findAll();

	ResultDTO addCommunication(CommunicationDTO communicationDTO, RequestDTO requestDTO);

	ResultDTO updateCommunication(CommunicationDTO communicationDTO, RequestDTO requestDTO);

    Page<Communication> getAllCommunications(Pageable pageable);

    Page<Communication> getAllCommunications(Specification<Communication> spec, Pageable pageable);

	ResponseEntity<CommunicationPageDTO> getCommunications(CommunicationSearchDTO communicationSearchDTO);
	
	List<CommunicationDTO> convertCommunicationsToCommunicationDTOs(List<Communication> communications, CommunicationConvertCriteriaDTO convertCriteria);

	CommunicationDTO getCommunicationDTOById(Integer communicationId);



	
}
