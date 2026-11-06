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

import com.worldparliamentsimulator.domain.Turn;
import com.worldparliamentsimulator.dto.TurnDTO;
import com.worldparliamentsimulator.dto.TurnSearchDTO;
import com.worldparliamentsimulator.dto.TurnPageDTO;
import com.worldparliamentsimulator.service.TurnService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/turn")
@RestController
public class TurnController {

	private final static Logger logger = LoggerFactory.getLogger(TurnController.class);

	@Autowired
	TurnService turnService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Turn> getAll() {

		List<Turn> turns = turnService.findAll();
		
		return turns;	
	}

	@GetMapping(value = "/{turnId}")
	@ResponseBody
	public TurnDTO getTurn(@PathVariable Integer turnId) {
		
		return (turnService.getTurnDTOById(turnId));
	}

 	@RequestMapping(value = "/addTurn", method = RequestMethod.POST)
	public ResponseEntity<?> addTurn(@RequestBody TurnDTO turnDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = turnService.addTurn(turnDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/turns")
	public ResponseEntity<TurnPageDTO> getTurns(TurnSearchDTO turnSearchDTO) {
 
		return turnService.getTurns(turnSearchDTO);
	}	

	@RequestMapping(value = "/updateTurn", method = RequestMethod.POST)
	public ResponseEntity<?> updateTurn(@RequestBody TurnDTO turnDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = turnService.updateTurn(turnDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
