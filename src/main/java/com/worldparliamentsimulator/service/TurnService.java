package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Turn;
import com.worldparliamentsimulator.dto.TurnDTO;
import com.worldparliamentsimulator.dto.TurnSearchDTO;
import com.worldparliamentsimulator.dto.TurnPageDTO;
import com.worldparliamentsimulator.dto.TurnConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TurnService extends GenericService<Turn, Integer> {

	List<Turn> findAll();

	ResultDTO addTurn(TurnDTO turnDTO, RequestDTO requestDTO);

	ResultDTO updateTurn(TurnDTO turnDTO, RequestDTO requestDTO);

    Page<Turn> getAllTurns(Pageable pageable);

    Page<Turn> getAllTurns(Specification<Turn> spec, Pageable pageable);

	ResponseEntity<TurnPageDTO> getTurns(TurnSearchDTO turnSearchDTO);
	
	List<TurnDTO> convertTurnsToTurnDTOs(List<Turn> turns, TurnConvertCriteriaDTO convertCriteria);

	TurnDTO getTurnDTOById(Integer turnId);



	
}
