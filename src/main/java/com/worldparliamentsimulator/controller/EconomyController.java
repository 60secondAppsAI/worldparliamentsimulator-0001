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

import com.worldparliamentsimulator.domain.Economy;
import com.worldparliamentsimulator.dto.EconomyDTO;
import com.worldparliamentsimulator.dto.EconomySearchDTO;
import com.worldparliamentsimulator.dto.EconomyPageDTO;
import com.worldparliamentsimulator.service.EconomyService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/economy")
@RestController
public class EconomyController {

	private final static Logger logger = LoggerFactory.getLogger(EconomyController.class);

	@Autowired
	EconomyService economyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Economy> getAll() {

		List<Economy> economys = economyService.findAll();
		
		return economys;	
	}

	@GetMapping(value = "/{economyId}")
	@ResponseBody
	public EconomyDTO getEconomy(@PathVariable Integer economyId) {
		
		return (economyService.getEconomyDTOById(economyId));
	}

 	@RequestMapping(value = "/addEconomy", method = RequestMethod.POST)
	public ResponseEntity<?> addEconomy(@RequestBody EconomyDTO economyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = economyService.addEconomy(economyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/economys")
	public ResponseEntity<EconomyPageDTO> getEconomys(EconomySearchDTO economySearchDTO) {
 
		return economyService.getEconomys(economySearchDTO);
	}	

	@RequestMapping(value = "/updateEconomy", method = RequestMethod.POST)
	public ResponseEntity<?> updateEconomy(@RequestBody EconomyDTO economyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = economyService.updateEconomy(economyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
