package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Economy;
import com.worldparliamentsimulator.dto.EconomyDTO;
import com.worldparliamentsimulator.dto.EconomySearchDTO;
import com.worldparliamentsimulator.dto.EconomyPageDTO;
import com.worldparliamentsimulator.dto.EconomyConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EconomyService extends GenericService<Economy, Integer> {

	List<Economy> findAll();

	ResultDTO addEconomy(EconomyDTO economyDTO, RequestDTO requestDTO);

	ResultDTO updateEconomy(EconomyDTO economyDTO, RequestDTO requestDTO);

    Page<Economy> getAllEconomys(Pageable pageable);

    Page<Economy> getAllEconomys(Specification<Economy> spec, Pageable pageable);

	ResponseEntity<EconomyPageDTO> getEconomys(EconomySearchDTO economySearchDTO);
	
	List<EconomyDTO> convertEconomysToEconomyDTOs(List<Economy> economys, EconomyConvertCriteriaDTO convertCriteria);

	EconomyDTO getEconomyDTOById(Integer economyId);



	
}
