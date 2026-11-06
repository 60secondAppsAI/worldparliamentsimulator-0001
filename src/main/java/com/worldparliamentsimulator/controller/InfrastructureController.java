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

import com.worldparliamentsimulator.domain.Infrastructure;
import com.worldparliamentsimulator.dto.InfrastructureDTO;
import com.worldparliamentsimulator.dto.InfrastructureSearchDTO;
import com.worldparliamentsimulator.dto.InfrastructurePageDTO;
import com.worldparliamentsimulator.service.InfrastructureService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/infrastructure")
@RestController
public class InfrastructureController {

	private final static Logger logger = LoggerFactory.getLogger(InfrastructureController.class);

	@Autowired
	InfrastructureService infrastructureService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Infrastructure> getAll() {

		List<Infrastructure> infrastructures = infrastructureService.findAll();
		
		return infrastructures;	
	}

	@GetMapping(value = "/{infrastructureId}")
	@ResponseBody
	public InfrastructureDTO getInfrastructure(@PathVariable Integer infrastructureId) {
		
		return (infrastructureService.getInfrastructureDTOById(infrastructureId));
	}

 	@RequestMapping(value = "/addInfrastructure", method = RequestMethod.POST)
	public ResponseEntity<?> addInfrastructure(@RequestBody InfrastructureDTO infrastructureDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = infrastructureService.addInfrastructure(infrastructureDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/infrastructures")
	public ResponseEntity<InfrastructurePageDTO> getInfrastructures(InfrastructureSearchDTO infrastructureSearchDTO) {
 
		return infrastructureService.getInfrastructures(infrastructureSearchDTO);
	}	

	@RequestMapping(value = "/updateInfrastructure", method = RequestMethod.POST)
	public ResponseEntity<?> updateInfrastructure(@RequestBody InfrastructureDTO infrastructureDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = infrastructureService.updateInfrastructure(infrastructureDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
