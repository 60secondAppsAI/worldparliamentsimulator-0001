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

import com.worldparliamentsimulator.domain.People;
import com.worldparliamentsimulator.dto.PeopleDTO;
import com.worldparliamentsimulator.dto.PeopleSearchDTO;
import com.worldparliamentsimulator.dto.PeoplePageDTO;
import com.worldparliamentsimulator.service.PeopleService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/people")
@RestController
public class PeopleController {

	private final static Logger logger = LoggerFactory.getLogger(PeopleController.class);

	@Autowired
	PeopleService peopleService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<People> getAll() {

		List<People> peoples = peopleService.findAll();
		
		return peoples;	
	}

	@GetMapping(value = "/{peopleId}")
	@ResponseBody
	public PeopleDTO getPeople(@PathVariable Integer peopleId) {
		
		return (peopleService.getPeopleDTOById(peopleId));
	}

 	@RequestMapping(value = "/addPeople", method = RequestMethod.POST)
	public ResponseEntity<?> addPeople(@RequestBody PeopleDTO peopleDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = peopleService.addPeople(peopleDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/peoples")
	public ResponseEntity<PeoplePageDTO> getPeoples(PeopleSearchDTO peopleSearchDTO) {
 
		return peopleService.getPeoples(peopleSearchDTO);
	}	

	@RequestMapping(value = "/updatePeople", method = RequestMethod.POST)
	public ResponseEntity<?> updatePeople(@RequestBody PeopleDTO peopleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = peopleService.updatePeople(peopleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
