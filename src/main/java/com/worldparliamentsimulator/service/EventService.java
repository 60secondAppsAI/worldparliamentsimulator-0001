package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Event;
import com.worldparliamentsimulator.dto.EventDTO;
import com.worldparliamentsimulator.dto.EventSearchDTO;
import com.worldparliamentsimulator.dto.EventPageDTO;
import com.worldparliamentsimulator.dto.EventConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EventService extends GenericService<Event, Integer> {

	List<Event> findAll();

	ResultDTO addEvent(EventDTO eventDTO, RequestDTO requestDTO);

	ResultDTO updateEvent(EventDTO eventDTO, RequestDTO requestDTO);

    Page<Event> getAllEvents(Pageable pageable);

    Page<Event> getAllEvents(Specification<Event> spec, Pageable pageable);

	ResponseEntity<EventPageDTO> getEvents(EventSearchDTO eventSearchDTO);
	
	List<EventDTO> convertEventsToEventDTOs(List<Event> events, EventConvertCriteriaDTO convertCriteria);

	EventDTO getEventDTOById(Integer eventId);



	
}
