package com.worldparliamentsimulator.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.worldparliamentsimulator.dao.GenericDAO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.service.impl.GenericServiceImpl;
import com.worldparliamentsimulator.dao.TurnDAO;
import com.worldparliamentsimulator.domain.Turn;
import com.worldparliamentsimulator.dto.TurnDTO;
import com.worldparliamentsimulator.dto.TurnSearchDTO;
import com.worldparliamentsimulator.dto.TurnPageDTO;
import com.worldparliamentsimulator.dto.TurnConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.TurnService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class TurnServiceImpl extends GenericServiceImpl<Turn, Integer> implements TurnService {

    private final static Logger logger = LoggerFactory.getLogger(TurnServiceImpl.class);

	@Autowired
	TurnDAO turnDao;

	

	@Override
	public GenericDAO<Turn, Integer> getDAO() {
		return (GenericDAO<Turn, Integer>) turnDao;
	}
	
	public List<Turn> findAll () {
		List<Turn> turns = turnDao.findAll();
		
		return turns;	
		
	}

	public ResultDTO addTurn(TurnDTO turnDTO, RequestDTO requestDTO) {

		Turn turn = new Turn();

		turn.setTurnId(turnDTO.getTurnId());

		turn.setActionChosen(turnDTO.getActionChosen());

		turn.setWorldReaction(turnDTO.getWorldReaction());

		turn.setRandomEvent(turnDTO.getRandomEvent());

		turn.setMonth(turnDTO.getMonth());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		turn = turnDao.save(turn);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Turn> getAllTurns(Pageable pageable) {
		return turnDao.findAll(pageable);
	}

	public Page<Turn> getAllTurns(Specification<Turn> spec, Pageable pageable) {
		return turnDao.findAll(spec, pageable);
	}

	public ResponseEntity<TurnPageDTO> getTurns(TurnSearchDTO turnSearchDTO) {
	
			Integer turnId = turnSearchDTO.getTurnId(); 
 			String actionChosen = turnSearchDTO.getActionChosen(); 
 			String worldReaction = turnSearchDTO.getWorldReaction(); 
 			String randomEvent = turnSearchDTO.getRandomEvent(); 
  			String sortBy = turnSearchDTO.getSortBy();
			String sortOrder = turnSearchDTO.getSortOrder();
			String searchQuery = turnSearchDTO.getSearchQuery();
			Integer page = turnSearchDTO.getPage();
			Integer size = turnSearchDTO.getSize();

	        Specification<Turn> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, turnId, "turnId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, actionChosen, "actionChosen"); 
			
			spec = ControllerUtils.andIfNecessary(spec, worldReaction, "worldReaction"); 
			
			spec = ControllerUtils.andIfNecessary(spec, randomEvent, "randomEvent"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("actionChosen")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("worldReaction")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("randomEvent")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Turn> turns = this.getAllTurns(spec, pageable);
		
		//System.out.println(String.valueOf(turns.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(turns.getTotalPages()));
		
		List<Turn> turnsList = turns.getContent();
		
		TurnConvertCriteriaDTO convertCriteria = new TurnConvertCriteriaDTO();
		List<TurnDTO> turnDTOs = this.convertTurnsToTurnDTOs(turnsList,convertCriteria);
		
		TurnPageDTO turnPageDTO = new TurnPageDTO();
		turnPageDTO.setTurns(turnDTOs);
		turnPageDTO.setTotalElements(turns.getTotalElements());
		return ResponseEntity.ok(turnPageDTO);
	}

	public List<TurnDTO> convertTurnsToTurnDTOs(List<Turn> turns, TurnConvertCriteriaDTO convertCriteria) {
		
		List<TurnDTO> turnDTOs = new ArrayList<TurnDTO>();
		
		for (Turn turn : turns) {
			turnDTOs.add(convertTurnToTurnDTO(turn,convertCriteria));
		}
		
		return turnDTOs;

	}
	
	public TurnDTO convertTurnToTurnDTO(Turn turn, TurnConvertCriteriaDTO convertCriteria) {
		
		TurnDTO turnDTO = new TurnDTO();

		turnDTO.setTurnId(turn.getTurnId());

		turnDTO.setActionChosen(turn.getActionChosen());

		turnDTO.setWorldReaction(turn.getWorldReaction());

		turnDTO.setRandomEvent(turn.getRandomEvent());

		turnDTO.setMonth(turn.getMonth());
		
		return turnDTO;
	}

	public ResultDTO updateTurn(TurnDTO turnDTO, RequestDTO requestDTO) {
		
		Turn turn = turnDao.getById(turnDTO.getTurnId());
		
		turn.setTurnId(ControllerUtils.setValue(turn.getTurnId(), turnDTO.getTurnId()));
		
		turn.setActionChosen(ControllerUtils.setValue(turn.getActionChosen(), turnDTO.getActionChosen()));
		
		turn.setWorldReaction(ControllerUtils.setValue(turn.getWorldReaction(), turnDTO.getWorldReaction()));
		
		turn.setRandomEvent(ControllerUtils.setValue(turn.getRandomEvent(), turnDTO.getRandomEvent()));
		
		turn.setMonth(ControllerUtils.setValue(turn.getMonth(), turnDTO.getMonth()));

        turn = turnDao.save(turn);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TurnDTO getTurnDTOById(Integer turnId) {
	
		Turn turn = turnDao.getById(turnId);
		
		TurnConvertCriteriaDTO convertCriteria = new TurnConvertCriteriaDTO();
		return(this.convertTurnToTurnDTO(turn,convertCriteria));
	}

}
