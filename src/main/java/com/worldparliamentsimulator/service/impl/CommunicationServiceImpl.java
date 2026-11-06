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
import com.worldparliamentsimulator.dao.CommunicationDAO;
import com.worldparliamentsimulator.domain.Communication;
import com.worldparliamentsimulator.dto.CommunicationDTO;
import com.worldparliamentsimulator.dto.CommunicationSearchDTO;
import com.worldparliamentsimulator.dto.CommunicationPageDTO;
import com.worldparliamentsimulator.dto.CommunicationConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.CommunicationService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class CommunicationServiceImpl extends GenericServiceImpl<Communication, Integer> implements CommunicationService {

    private final static Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

	@Autowired
	CommunicationDAO communicationDao;

	

	@Override
	public GenericDAO<Communication, Integer> getDAO() {
		return (GenericDAO<Communication, Integer>) communicationDao;
	}
	
	public List<Communication> findAll () {
		List<Communication> communications = communicationDao.findAll();
		
		return communications;	
		
	}

	public ResultDTO addCommunication(CommunicationDTO communicationDTO, RequestDTO requestDTO) {

		Communication communication = new Communication();

		communication.setCommunicationId(communicationDTO.getCommunicationId());

		communication.setMessage(communicationDTO.getMessage());

		communication.setReactionEmoji(communicationDTO.getReactionEmoji());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		communication = communicationDao.save(communication);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Communication> getAllCommunications(Pageable pageable) {
		return communicationDao.findAll(pageable);
	}

	public Page<Communication> getAllCommunications(Specification<Communication> spec, Pageable pageable) {
		return communicationDao.findAll(spec, pageable);
	}

	public ResponseEntity<CommunicationPageDTO> getCommunications(CommunicationSearchDTO communicationSearchDTO) {
	
			Integer communicationId = communicationSearchDTO.getCommunicationId(); 
 			String message = communicationSearchDTO.getMessage(); 
 			String reactionEmoji = communicationSearchDTO.getReactionEmoji(); 
 			String sortBy = communicationSearchDTO.getSortBy();
			String sortOrder = communicationSearchDTO.getSortOrder();
			String searchQuery = communicationSearchDTO.getSearchQuery();
			Integer page = communicationSearchDTO.getPage();
			Integer size = communicationSearchDTO.getSize();

	        Specification<Communication> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, communicationId, "communicationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, message, "message"); 
			
			spec = ControllerUtils.andIfNecessary(spec, reactionEmoji, "reactionEmoji"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("message")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("reactionEmoji")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Communication> communications = this.getAllCommunications(spec, pageable);
		
		//System.out.println(String.valueOf(communications.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(communications.getTotalPages()));
		
		List<Communication> communicationsList = communications.getContent();
		
		CommunicationConvertCriteriaDTO convertCriteria = new CommunicationConvertCriteriaDTO();
		List<CommunicationDTO> communicationDTOs = this.convertCommunicationsToCommunicationDTOs(communicationsList,convertCriteria);
		
		CommunicationPageDTO communicationPageDTO = new CommunicationPageDTO();
		communicationPageDTO.setCommunications(communicationDTOs);
		communicationPageDTO.setTotalElements(communications.getTotalElements());
		return ResponseEntity.ok(communicationPageDTO);
	}

	public List<CommunicationDTO> convertCommunicationsToCommunicationDTOs(List<Communication> communications, CommunicationConvertCriteriaDTO convertCriteria) {
		
		List<CommunicationDTO> communicationDTOs = new ArrayList<CommunicationDTO>();
		
		for (Communication communication : communications) {
			communicationDTOs.add(convertCommunicationToCommunicationDTO(communication,convertCriteria));
		}
		
		return communicationDTOs;

	}
	
	public CommunicationDTO convertCommunicationToCommunicationDTO(Communication communication, CommunicationConvertCriteriaDTO convertCriteria) {
		
		CommunicationDTO communicationDTO = new CommunicationDTO();

		communicationDTO.setCommunicationId(communication.getCommunicationId());

		communicationDTO.setMessage(communication.getMessage());

		communicationDTO.setReactionEmoji(communication.getReactionEmoji());
		
		return communicationDTO;
	}

	public ResultDTO updateCommunication(CommunicationDTO communicationDTO, RequestDTO requestDTO) {
		
		Communication communication = communicationDao.getById(communicationDTO.getCommunicationId());
		
		communication.setCommunicationId(ControllerUtils.setValue(communication.getCommunicationId(), communicationDTO.getCommunicationId()));
		
		communication.setMessage(ControllerUtils.setValue(communication.getMessage(), communicationDTO.getMessage()));
		
		communication.setReactionEmoji(ControllerUtils.setValue(communication.getReactionEmoji(), communicationDTO.getReactionEmoji()));

        communication = communicationDao.save(communication);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CommunicationDTO getCommunicationDTOById(Integer communicationId) {
	
		Communication communication = communicationDao.getById(communicationId);
		
		CommunicationConvertCriteriaDTO convertCriteria = new CommunicationConvertCriteriaDTO();
		return(this.convertCommunicationToCommunicationDTO(communication,convertCriteria));
	}

}
