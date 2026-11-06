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

import com.worldparliamentsimulator.domain.Alliance;
import com.worldparliamentsimulator.dto.AllianceDTO;
import com.worldparliamentsimulator.dto.AllianceSearchDTO;
import com.worldparliamentsimulator.dto.AlliancePageDTO;
import com.worldparliamentsimulator.service.AllianceService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/alliance")
@RestController
public class AllianceController {

	private final static Logger logger = LoggerFactory.getLogger(AllianceController.class);

	@Autowired
	AllianceService allianceService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Alliance> getAll() {

		List<Alliance> alliances = allianceService.findAll();
		
		return alliances;	
	}

	@GetMapping(value = "/{allianceId}")
	@ResponseBody
	public AllianceDTO getAlliance(@PathVariable Integer allianceId) {
		
		return (allianceService.getAllianceDTOById(allianceId));
	}

 	@RequestMapping(value = "/addAlliance", method = RequestMethod.POST)
	public ResponseEntity<?> addAlliance(@RequestBody AllianceDTO allianceDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = allianceService.addAlliance(allianceDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/alliances")
	public ResponseEntity<AlliancePageDTO> getAlliances(AllianceSearchDTO allianceSearchDTO) {
 
		return allianceService.getAlliances(allianceSearchDTO);
	}	

	@RequestMapping(value = "/updateAlliance", method = RequestMethod.POST)
	public ResponseEntity<?> updateAlliance(@RequestBody AllianceDTO allianceDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = allianceService.updateAlliance(allianceDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
