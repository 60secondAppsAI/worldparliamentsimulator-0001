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

import com.worldparliamentsimulator.domain.Military;
import com.worldparliamentsimulator.dto.MilitaryDTO;
import com.worldparliamentsimulator.dto.MilitarySearchDTO;
import com.worldparliamentsimulator.dto.MilitaryPageDTO;
import com.worldparliamentsimulator.service.MilitaryService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/military")
@RestController
public class MilitaryController {

	private final static Logger logger = LoggerFactory.getLogger(MilitaryController.class);

	@Autowired
	MilitaryService militaryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Military> getAll() {

		List<Military> militarys = militaryService.findAll();
		
		return militarys;	
	}

	@GetMapping(value = "/{militaryId}")
	@ResponseBody
	public MilitaryDTO getMilitary(@PathVariable Integer militaryId) {
		
		return (militaryService.getMilitaryDTOById(militaryId));
	}

 	@RequestMapping(value = "/addMilitary", method = RequestMethod.POST)
	public ResponseEntity<?> addMilitary(@RequestBody MilitaryDTO militaryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = militaryService.addMilitary(militaryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/militarys")
	public ResponseEntity<MilitaryPageDTO> getMilitarys(MilitarySearchDTO militarySearchDTO) {
 
		return militaryService.getMilitarys(militarySearchDTO);
	}	

	@RequestMapping(value = "/updateMilitary", method = RequestMethod.POST)
	public ResponseEntity<?> updateMilitary(@RequestBody MilitaryDTO militaryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = militaryService.updateMilitary(militaryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
