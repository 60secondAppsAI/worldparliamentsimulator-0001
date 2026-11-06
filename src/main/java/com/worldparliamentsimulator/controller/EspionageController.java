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

import com.worldparliamentsimulator.domain.Espionage;
import com.worldparliamentsimulator.dto.EspionageDTO;
import com.worldparliamentsimulator.dto.EspionageSearchDTO;
import com.worldparliamentsimulator.dto.EspionagePageDTO;
import com.worldparliamentsimulator.service.EspionageService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/espionage")
@RestController
public class EspionageController {

	private final static Logger logger = LoggerFactory.getLogger(EspionageController.class);

	@Autowired
	EspionageService espionageService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Espionage> getAll() {

		List<Espionage> espionages = espionageService.findAll();
		
		return espionages;	
	}

	@GetMapping(value = "/{espionageId}")
	@ResponseBody
	public EspionageDTO getEspionage(@PathVariable Integer espionageId) {
		
		return (espionageService.getEspionageDTOById(espionageId));
	}

 	@RequestMapping(value = "/addEspionage", method = RequestMethod.POST)
	public ResponseEntity<?> addEspionage(@RequestBody EspionageDTO espionageDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = espionageService.addEspionage(espionageDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/espionages")
	public ResponseEntity<EspionagePageDTO> getEspionages(EspionageSearchDTO espionageSearchDTO) {
 
		return espionageService.getEspionages(espionageSearchDTO);
	}	

	@RequestMapping(value = "/updateEspionage", method = RequestMethod.POST)
	public ResponseEntity<?> updateEspionage(@RequestBody EspionageDTO espionageDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = espionageService.updateEspionage(espionageDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
