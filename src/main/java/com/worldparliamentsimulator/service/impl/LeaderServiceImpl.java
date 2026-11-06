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
import com.worldparliamentsimulator.dao.LeaderDAO;
import com.worldparliamentsimulator.domain.Leader;
import com.worldparliamentsimulator.dto.LeaderDTO;
import com.worldparliamentsimulator.dto.LeaderSearchDTO;
import com.worldparliamentsimulator.dto.LeaderPageDTO;
import com.worldparliamentsimulator.dto.LeaderConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.LeaderService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class LeaderServiceImpl extends GenericServiceImpl<Leader, Integer> implements LeaderService {

    private final static Logger logger = LoggerFactory.getLogger(LeaderServiceImpl.class);

	@Autowired
	LeaderDAO leaderDao;

	

	@Override
	public GenericDAO<Leader, Integer> getDAO() {
		return (GenericDAO<Leader, Integer>) leaderDao;
	}
	
	public List<Leader> findAll () {
		List<Leader> leaders = leaderDao.findAll();
		
		return leaders;	
		
	}

	public ResultDTO addLeader(LeaderDTO leaderDTO, RequestDTO requestDTO) {

		Leader leader = new Leader();

		leader.setLeaderId(leaderDTO.getLeaderId());

		leader.setName(leaderDTO.getName());

		leader.setIdeology(leaderDTO.getIdeology());

		leader.setHistory(leaderDTO.getHistory());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		leader = leaderDao.save(leader);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Leader> getAllLeaders(Pageable pageable) {
		return leaderDao.findAll(pageable);
	}

	public Page<Leader> getAllLeaders(Specification<Leader> spec, Pageable pageable) {
		return leaderDao.findAll(spec, pageable);
	}

	public ResponseEntity<LeaderPageDTO> getLeaders(LeaderSearchDTO leaderSearchDTO) {
	
			Integer leaderId = leaderSearchDTO.getLeaderId(); 
 			String name = leaderSearchDTO.getName(); 
 			String ideology = leaderSearchDTO.getIdeology(); 
 			String history = leaderSearchDTO.getHistory(); 
 			String sortBy = leaderSearchDTO.getSortBy();
			String sortOrder = leaderSearchDTO.getSortOrder();
			String searchQuery = leaderSearchDTO.getSearchQuery();
			Integer page = leaderSearchDTO.getPage();
			Integer size = leaderSearchDTO.getSize();

	        Specification<Leader> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, leaderId, "leaderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ideology, "ideology"); 
			
			spec = ControllerUtils.andIfNecessary(spec, history, "history"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("ideology")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("history")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Leader> leaders = this.getAllLeaders(spec, pageable);
		
		//System.out.println(String.valueOf(leaders.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(leaders.getTotalPages()));
		
		List<Leader> leadersList = leaders.getContent();
		
		LeaderConvertCriteriaDTO convertCriteria = new LeaderConvertCriteriaDTO();
		List<LeaderDTO> leaderDTOs = this.convertLeadersToLeaderDTOs(leadersList,convertCriteria);
		
		LeaderPageDTO leaderPageDTO = new LeaderPageDTO();
		leaderPageDTO.setLeaders(leaderDTOs);
		leaderPageDTO.setTotalElements(leaders.getTotalElements());
		return ResponseEntity.ok(leaderPageDTO);
	}

	public List<LeaderDTO> convertLeadersToLeaderDTOs(List<Leader> leaders, LeaderConvertCriteriaDTO convertCriteria) {
		
		List<LeaderDTO> leaderDTOs = new ArrayList<LeaderDTO>();
		
		for (Leader leader : leaders) {
			leaderDTOs.add(convertLeaderToLeaderDTO(leader,convertCriteria));
		}
		
		return leaderDTOs;

	}
	
	public LeaderDTO convertLeaderToLeaderDTO(Leader leader, LeaderConvertCriteriaDTO convertCriteria) {
		
		LeaderDTO leaderDTO = new LeaderDTO();

		leaderDTO.setLeaderId(leader.getLeaderId());

		leaderDTO.setName(leader.getName());

		leaderDTO.setIdeology(leader.getIdeology());

		leaderDTO.setHistory(leader.getHistory());
		
		return leaderDTO;
	}

	public ResultDTO updateLeader(LeaderDTO leaderDTO, RequestDTO requestDTO) {
		
		Leader leader = leaderDao.getById(leaderDTO.getLeaderId());
		
		leader.setLeaderId(ControllerUtils.setValue(leader.getLeaderId(), leaderDTO.getLeaderId()));
		
		leader.setName(ControllerUtils.setValue(leader.getName(), leaderDTO.getName()));
		
		leader.setIdeology(ControllerUtils.setValue(leader.getIdeology(), leaderDTO.getIdeology()));
		
		leader.setHistory(ControllerUtils.setValue(leader.getHistory(), leaderDTO.getHistory()));

        leader = leaderDao.save(leader);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LeaderDTO getLeaderDTOById(Integer leaderId) {
	
		Leader leader = leaderDao.getById(leaderId);
		
		LeaderConvertCriteriaDTO convertCriteria = new LeaderConvertCriteriaDTO();
		return(this.convertLeaderToLeaderDTO(leader,convertCriteria));
	}

}
