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

import com.worldparliamentsimulator.domain.ContinentProblem;
import com.worldparliamentsimulator.dto.ContinentProblemDTO;
import com.worldparliamentsimulator.dto.ContinentProblemSearchDTO;
import com.worldparliamentsimulator.dto.ContinentProblemPageDTO;
import com.worldparliamentsimulator.service.ContinentProblemService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/continentProblem")
@RestController
public class ContinentProblemController {

	private final static Logger logger = LoggerFactory.getLogger(ContinentProblemController.class);

	@Autowired
	ContinentProblemService continentProblemService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ContinentProblem> getAll() {

		List<ContinentProblem> continentProblems = continentProblemService.findAll();
		
		return continentProblems;	
	}

	@GetMapping(value = "/{continentProblemId}")
	@ResponseBody
	public ContinentProblemDTO getContinentProblem(@PathVariable Integer continentProblemId) {
		
		return (continentProblemService.getContinentProblemDTOById(continentProblemId));
	}

 	@RequestMapping(value = "/addContinentProblem", method = RequestMethod.POST)
	public ResponseEntity<?> addContinentProblem(@RequestBody ContinentProblemDTO continentProblemDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = continentProblemService.addContinentProblem(continentProblemDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/continentProblems")
	public ResponseEntity<ContinentProblemPageDTO> getContinentProblems(ContinentProblemSearchDTO continentProblemSearchDTO) {
 
		return continentProblemService.getContinentProblems(continentProblemSearchDTO);
	}	

	@RequestMapping(value = "/updateContinentProblem", method = RequestMethod.POST)
	public ResponseEntity<?> updateContinentProblem(@RequestBody ContinentProblemDTO continentProblemDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = continentProblemService.updateContinentProblem(continentProblemDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
