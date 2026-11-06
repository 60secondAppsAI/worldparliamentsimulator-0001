package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Leader;
import com.worldparliamentsimulator.dto.LeaderDTO;
import com.worldparliamentsimulator.dto.LeaderSearchDTO;
import com.worldparliamentsimulator.dto.LeaderPageDTO;
import com.worldparliamentsimulator.dto.LeaderConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LeaderService extends GenericService<Leader, Integer> {

	List<Leader> findAll();

	ResultDTO addLeader(LeaderDTO leaderDTO, RequestDTO requestDTO);

	ResultDTO updateLeader(LeaderDTO leaderDTO, RequestDTO requestDTO);

    Page<Leader> getAllLeaders(Pageable pageable);

    Page<Leader> getAllLeaders(Specification<Leader> spec, Pageable pageable);

	ResponseEntity<LeaderPageDTO> getLeaders(LeaderSearchDTO leaderSearchDTO);
	
	List<LeaderDTO> convertLeadersToLeaderDTOs(List<Leader> leaders, LeaderConvertCriteriaDTO convertCriteria);

	LeaderDTO getLeaderDTOById(Integer leaderId);



	
}
