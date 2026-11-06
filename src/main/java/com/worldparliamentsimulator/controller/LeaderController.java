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

import com.worldparliamentsimulator.domain.Leader;
import com.worldparliamentsimulator.dto.LeaderDTO;
import com.worldparliamentsimulator.dto.LeaderSearchDTO;
import com.worldparliamentsimulator.dto.LeaderPageDTO;
import com.worldparliamentsimulator.service.LeaderService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/leader")
@RestController
public class LeaderController {

	private final static Logger logger = LoggerFactory.getLogger(LeaderController.class);

	@Autowired
	LeaderService leaderService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Leader> getAll() {

		List<Leader> leaders = leaderService.findAll();
		
		return leaders;	
	}

	@GetMapping(value = "/{leaderId}")
	@ResponseBody
	public LeaderDTO getLeader(@PathVariable Integer leaderId) {
		
		return (leaderService.getLeaderDTOById(leaderId));
	}

 	@RequestMapping(value = "/addLeader", method = RequestMethod.POST)
	public ResponseEntity<?> addLeader(@RequestBody LeaderDTO leaderDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leaderService.addLeader(leaderDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/leaders")
	public ResponseEntity<LeaderPageDTO> getLeaders(LeaderSearchDTO leaderSearchDTO) {
 
		return leaderService.getLeaders(leaderSearchDTO);
	}	

	@RequestMapping(value = "/updateLeader", method = RequestMethod.POST)
	public ResponseEntity<?> updateLeader(@RequestBody LeaderDTO leaderDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leaderService.updateLeader(leaderDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
