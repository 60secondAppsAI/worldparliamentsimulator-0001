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

import com.worldparliamentsimulator.domain.Ideology;
import com.worldparliamentsimulator.dto.IdeologyDTO;
import com.worldparliamentsimulator.dto.IdeologySearchDTO;
import com.worldparliamentsimulator.dto.IdeologyPageDTO;
import com.worldparliamentsimulator.service.IdeologyService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/ideology")
@RestController
public class IdeologyController {

	private final static Logger logger = LoggerFactory.getLogger(IdeologyController.class);

	@Autowired
	IdeologyService ideologyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Ideology> getAll() {

		List<Ideology> ideologys = ideologyService.findAll();
		
		return ideologys;	
	}

	@GetMapping(value = "/{ideologyId}")
	@ResponseBody
	public IdeologyDTO getIdeology(@PathVariable Integer ideologyId) {
		
		return (ideologyService.getIdeologyDTOById(ideologyId));
	}

 	@RequestMapping(value = "/addIdeology", method = RequestMethod.POST)
	public ResponseEntity<?> addIdeology(@RequestBody IdeologyDTO ideologyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = ideologyService.addIdeology(ideologyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/ideologys")
	public ResponseEntity<IdeologyPageDTO> getIdeologys(IdeologySearchDTO ideologySearchDTO) {
 
		return ideologyService.getIdeologys(ideologySearchDTO);
	}	

	@RequestMapping(value = "/updateIdeology", method = RequestMethod.POST)
	public ResponseEntity<?> updateIdeology(@RequestBody IdeologyDTO ideologyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = ideologyService.updateIdeology(ideologyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
