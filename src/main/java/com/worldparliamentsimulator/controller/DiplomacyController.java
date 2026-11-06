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

import com.worldparliamentsimulator.domain.Diplomacy;
import com.worldparliamentsimulator.dto.DiplomacyDTO;
import com.worldparliamentsimulator.dto.DiplomacySearchDTO;
import com.worldparliamentsimulator.dto.DiplomacyPageDTO;
import com.worldparliamentsimulator.service.DiplomacyService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/diplomacy")
@RestController
public class DiplomacyController {

	private final static Logger logger = LoggerFactory.getLogger(DiplomacyController.class);

	@Autowired
	DiplomacyService diplomacyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Diplomacy> getAll() {

		List<Diplomacy> diplomacys = diplomacyService.findAll();
		
		return diplomacys;	
	}

	@GetMapping(value = "/{diplomacyId}")
	@ResponseBody
	public DiplomacyDTO getDiplomacy(@PathVariable Integer diplomacyId) {
		
		return (diplomacyService.getDiplomacyDTOById(diplomacyId));
	}

 	@RequestMapping(value = "/addDiplomacy", method = RequestMethod.POST)
	public ResponseEntity<?> addDiplomacy(@RequestBody DiplomacyDTO diplomacyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = diplomacyService.addDiplomacy(diplomacyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/diplomacys")
	public ResponseEntity<DiplomacyPageDTO> getDiplomacys(DiplomacySearchDTO diplomacySearchDTO) {
 
		return diplomacyService.getDiplomacys(diplomacySearchDTO);
	}	

	@RequestMapping(value = "/updateDiplomacy", method = RequestMethod.POST)
	public ResponseEntity<?> updateDiplomacy(@RequestBody DiplomacyDTO diplomacyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = diplomacyService.updateDiplomacy(diplomacyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
