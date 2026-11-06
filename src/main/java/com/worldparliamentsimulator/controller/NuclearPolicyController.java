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

import com.worldparliamentsimulator.domain.NuclearPolicy;
import com.worldparliamentsimulator.dto.NuclearPolicyDTO;
import com.worldparliamentsimulator.dto.NuclearPolicySearchDTO;
import com.worldparliamentsimulator.dto.NuclearPolicyPageDTO;
import com.worldparliamentsimulator.service.NuclearPolicyService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/nuclearPolicy")
@RestController
public class NuclearPolicyController {

	private final static Logger logger = LoggerFactory.getLogger(NuclearPolicyController.class);

	@Autowired
	NuclearPolicyService nuclearPolicyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<NuclearPolicy> getAll() {

		List<NuclearPolicy> nuclearPolicys = nuclearPolicyService.findAll();
		
		return nuclearPolicys;	
	}

	@GetMapping(value = "/{nuclearPolicyId}")
	@ResponseBody
	public NuclearPolicyDTO getNuclearPolicy(@PathVariable Integer nuclearPolicyId) {
		
		return (nuclearPolicyService.getNuclearPolicyDTOById(nuclearPolicyId));
	}

 	@RequestMapping(value = "/addNuclearPolicy", method = RequestMethod.POST)
	public ResponseEntity<?> addNuclearPolicy(@RequestBody NuclearPolicyDTO nuclearPolicyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = nuclearPolicyService.addNuclearPolicy(nuclearPolicyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/nuclearPolicys")
	public ResponseEntity<NuclearPolicyPageDTO> getNuclearPolicys(NuclearPolicySearchDTO nuclearPolicySearchDTO) {
 
		return nuclearPolicyService.getNuclearPolicys(nuclearPolicySearchDTO);
	}	

	@RequestMapping(value = "/updateNuclearPolicy", method = RequestMethod.POST)
	public ResponseEntity<?> updateNuclearPolicy(@RequestBody NuclearPolicyDTO nuclearPolicyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = nuclearPolicyService.updateNuclearPolicy(nuclearPolicyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
