package com.worldparliamentsimulator.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.worldparliamentsimulator.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.worldparliamentsimulator.domain.Event;
import com.worldparliamentsimulator.dto.EventDTO;
import com.worldparliamentsimulator.dto.EventSearchDTO;
import com.worldparliamentsimulator.dto.EventPageDTO;
import com.worldparliamentsimulator.service.EventService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/event")
@RestController
public class EventController {

	private final static Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventService eventService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Event> getAll() {

		List<Event> events = eventService.findAll();
		
		return events;	
	}

	@GetMapping(value = "/{eventId}")
	@ResponseBody
	public EventDTO getEvent(@PathVariable Integer eventId) {
		
		return (eventService.getEventDTOById(eventId));
	}

 	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public ResponseEntity<?> addEvent(@RequestBody EventDTO eventDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = eventService.addEvent(eventDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/events")
	public ResponseEntity<EventPageDTO> getEvents(EventSearchDTO eventSearchDTO) {
 
		return eventService.getEvents(eventSearchDTO);
	}	

	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = eventService.updateEvent(eventDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
