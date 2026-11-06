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
import com.worldparliamentsimulator.dao.NuclearPolicyDAO;
import com.worldparliamentsimulator.domain.NuclearPolicy;
import com.worldparliamentsimulator.dto.NuclearPolicyDTO;
import com.worldparliamentsimulator.dto.NuclearPolicySearchDTO;
import com.worldparliamentsimulator.dto.NuclearPolicyPageDTO;
import com.worldparliamentsimulator.dto.NuclearPolicyConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.NuclearPolicyService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class NuclearPolicyServiceImpl extends GenericServiceImpl<NuclearPolicy, Integer> implements NuclearPolicyService {

    private final static Logger logger = LoggerFactory.getLogger(NuclearPolicyServiceImpl.class);

	@Autowired
	NuclearPolicyDAO nuclearPolicyDao;

	

	@Override
	public GenericDAO<NuclearPolicy, Integer> getDAO() {
		return (GenericDAO<NuclearPolicy, Integer>) nuclearPolicyDao;
	}
	
	public List<NuclearPolicy> findAll () {
		List<NuclearPolicy> nuclearPolicys = nuclearPolicyDao.findAll();
		
		return nuclearPolicys;	
		
	}

	public ResultDTO addNuclearPolicy(NuclearPolicyDTO nuclearPolicyDTO, RequestDTO requestDTO) {

		NuclearPolicy nuclearPolicy = new NuclearPolicy();

		nuclearPolicy.setNuclearPolicyId(nuclearPolicyDTO.getNuclearPolicyId());

		nuclearPolicy.setHasNukes(nuclearPolicyDTO.getHasNukes());

		nuclearPolicy.setIsProtected(nuclearPolicyDTO.getIsProtected());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		nuclearPolicy = nuclearPolicyDao.save(nuclearPolicy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<NuclearPolicy> getAllNuclearPolicys(Pageable pageable) {
		return nuclearPolicyDao.findAll(pageable);
	}

	public Page<NuclearPolicy> getAllNuclearPolicys(Specification<NuclearPolicy> spec, Pageable pageable) {
		return nuclearPolicyDao.findAll(spec, pageable);
	}

	public ResponseEntity<NuclearPolicyPageDTO> getNuclearPolicys(NuclearPolicySearchDTO nuclearPolicySearchDTO) {
	
			Integer nuclearPolicyId = nuclearPolicySearchDTO.getNuclearPolicyId(); 
   			String sortBy = nuclearPolicySearchDTO.getSortBy();
			String sortOrder = nuclearPolicySearchDTO.getSortOrder();
			String searchQuery = nuclearPolicySearchDTO.getSearchQuery();
			Integer page = nuclearPolicySearchDTO.getPage();
			Integer size = nuclearPolicySearchDTO.getSize();

	        Specification<NuclearPolicy> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, nuclearPolicyId, "nuclearPolicyId"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<NuclearPolicy> nuclearPolicys = this.getAllNuclearPolicys(spec, pageable);
		
		//System.out.println(String.valueOf(nuclearPolicys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(nuclearPolicys.getTotalPages()));
		
		List<NuclearPolicy> nuclearPolicysList = nuclearPolicys.getContent();
		
		NuclearPolicyConvertCriteriaDTO convertCriteria = new NuclearPolicyConvertCriteriaDTO();
		List<NuclearPolicyDTO> nuclearPolicyDTOs = this.convertNuclearPolicysToNuclearPolicyDTOs(nuclearPolicysList,convertCriteria);
		
		NuclearPolicyPageDTO nuclearPolicyPageDTO = new NuclearPolicyPageDTO();
		nuclearPolicyPageDTO.setNuclearPolicys(nuclearPolicyDTOs);
		nuclearPolicyPageDTO.setTotalElements(nuclearPolicys.getTotalElements());
		return ResponseEntity.ok(nuclearPolicyPageDTO);
	}

	public List<NuclearPolicyDTO> convertNuclearPolicysToNuclearPolicyDTOs(List<NuclearPolicy> nuclearPolicys, NuclearPolicyConvertCriteriaDTO convertCriteria) {
		
		List<NuclearPolicyDTO> nuclearPolicyDTOs = new ArrayList<NuclearPolicyDTO>();
		
		for (NuclearPolicy nuclearPolicy : nuclearPolicys) {
			nuclearPolicyDTOs.add(convertNuclearPolicyToNuclearPolicyDTO(nuclearPolicy,convertCriteria));
		}
		
		return nuclearPolicyDTOs;

	}
	
	public NuclearPolicyDTO convertNuclearPolicyToNuclearPolicyDTO(NuclearPolicy nuclearPolicy, NuclearPolicyConvertCriteriaDTO convertCriteria) {
		
		NuclearPolicyDTO nuclearPolicyDTO = new NuclearPolicyDTO();

		nuclearPolicyDTO.setNuclearPolicyId(nuclearPolicy.getNuclearPolicyId());

		nuclearPolicyDTO.setHasNukes(nuclearPolicy.getHasNukes());

		nuclearPolicyDTO.setIsProtected(nuclearPolicy.getIsProtected());
		
		return nuclearPolicyDTO;
	}

	public ResultDTO updateNuclearPolicy(NuclearPolicyDTO nuclearPolicyDTO, RequestDTO requestDTO) {
		
		NuclearPolicy nuclearPolicy = nuclearPolicyDao.getById(nuclearPolicyDTO.getNuclearPolicyId());
		
		nuclearPolicy.setNuclearPolicyId(ControllerUtils.setValue(nuclearPolicy.getNuclearPolicyId(), nuclearPolicyDTO.getNuclearPolicyId()));
		
		nuclearPolicy.setHasNukes(ControllerUtils.setValue(nuclearPolicy.getHasNukes(), nuclearPolicyDTO.getHasNukes()));
		
		nuclearPolicy.setIsProtected(ControllerUtils.setValue(nuclearPolicy.getIsProtected(), nuclearPolicyDTO.getIsProtected()));

        nuclearPolicy = nuclearPolicyDao.save(nuclearPolicy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public NuclearPolicyDTO getNuclearPolicyDTOById(Integer nuclearPolicyId) {
	
		NuclearPolicy nuclearPolicy = nuclearPolicyDao.getById(nuclearPolicyId);
		
		NuclearPolicyConvertCriteriaDTO convertCriteria = new NuclearPolicyConvertCriteriaDTO();
		return(this.convertNuclearPolicyToNuclearPolicyDTO(nuclearPolicy,convertCriteria));
	}

}
