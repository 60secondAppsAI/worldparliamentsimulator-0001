package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.Diplomacy;
import com.worldparliamentsimulator.dto.DiplomacyDTO;
import com.worldparliamentsimulator.dto.DiplomacySearchDTO;
import com.worldparliamentsimulator.dto.DiplomacyPageDTO;
import com.worldparliamentsimulator.dto.DiplomacyConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DiplomacyService extends GenericService<Diplomacy, Integer> {

	List<Diplomacy> findAll();

	ResultDTO addDiplomacy(DiplomacyDTO diplomacyDTO, RequestDTO requestDTO);

	ResultDTO updateDiplomacy(DiplomacyDTO diplomacyDTO, RequestDTO requestDTO);

    Page<Diplomacy> getAllDiplomacys(Pageable pageable);

    Page<Diplomacy> getAllDiplomacys(Specification<Diplomacy> spec, Pageable pageable);

	ResponseEntity<DiplomacyPageDTO> getDiplomacys(DiplomacySearchDTO diplomacySearchDTO);
	
	List<DiplomacyDTO> convertDiplomacysToDiplomacyDTOs(List<Diplomacy> diplomacys, DiplomacyConvertCriteriaDTO convertCriteria);

	DiplomacyDTO getDiplomacyDTOById(Integer diplomacyId);



	
}
