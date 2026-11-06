package com.worldparliamentsimulator.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.worldparliamentsimulator.domain.NuclearPolicy;
import com.worldparliamentsimulator.dto.NuclearPolicyDTO;
import com.worldparliamentsimulator.dto.NuclearPolicySearchDTO;
import com.worldparliamentsimulator.dto.NuclearPolicyPageDTO;
import com.worldparliamentsimulator.dto.NuclearPolicyConvertCriteriaDTO;
import com.worldparliamentsimulator.service.GenericService;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface NuclearPolicyService extends GenericService<NuclearPolicy, Integer> {

	List<NuclearPolicy> findAll();

	ResultDTO addNuclearPolicy(NuclearPolicyDTO nuclearPolicyDTO, RequestDTO requestDTO);

	ResultDTO updateNuclearPolicy(NuclearPolicyDTO nuclearPolicyDTO, RequestDTO requestDTO);

    Page<NuclearPolicy> getAllNuclearPolicys(Pageable pageable);

    Page<NuclearPolicy> getAllNuclearPolicys(Specification<NuclearPolicy> spec, Pageable pageable);

	ResponseEntity<NuclearPolicyPageDTO> getNuclearPolicys(NuclearPolicySearchDTO nuclearPolicySearchDTO);
	
	List<NuclearPolicyDTO> convertNuclearPolicysToNuclearPolicyDTOs(List<NuclearPolicy> nuclearPolicys, NuclearPolicyConvertCriteriaDTO convertCriteria);

	NuclearPolicyDTO getNuclearPolicyDTOById(Integer nuclearPolicyId);



	
}
