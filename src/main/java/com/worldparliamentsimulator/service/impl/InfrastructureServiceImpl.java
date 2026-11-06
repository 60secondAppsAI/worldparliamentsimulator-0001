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
import com.worldparliamentsimulator.dao.InfrastructureDAO;
import com.worldparliamentsimulator.domain.Infrastructure;
import com.worldparliamentsimulator.dto.InfrastructureDTO;
import com.worldparliamentsimulator.dto.InfrastructureSearchDTO;
import com.worldparliamentsimulator.dto.InfrastructurePageDTO;
import com.worldparliamentsimulator.dto.InfrastructureConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.InfrastructureService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class InfrastructureServiceImpl extends GenericServiceImpl<Infrastructure, Integer> implements InfrastructureService {

    private final static Logger logger = LoggerFactory.getLogger(InfrastructureServiceImpl.class);

	@Autowired
	InfrastructureDAO infrastructureDao;

	

	@Override
	public GenericDAO<Infrastructure, Integer> getDAO() {
		return (GenericDAO<Infrastructure, Integer>) infrastructureDao;
	}
	
	public List<Infrastructure> findAll () {
		List<Infrastructure> infrastructures = infrastructureDao.findAll();
		
		return infrastructures;	
		
	}

	public ResultDTO addInfrastructure(InfrastructureDTO infrastructureDTO, RequestDTO requestDTO) {

		Infrastructure infrastructure = new Infrastructure();

		infrastructure.setInfrastructureId(infrastructureDTO.getInfrastructureId());

		infrastructure.setSchools(infrastructureDTO.getSchools());

		infrastructure.setHospitals(infrastructureDTO.getHospitals());

		infrastructure.setRoads(infrastructureDTO.getRoads());

		infrastructure.setCleanEnergySources(infrastructureDTO.getCleanEnergySources());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		infrastructure = infrastructureDao.save(infrastructure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Infrastructure> getAllInfrastructures(Pageable pageable) {
		return infrastructureDao.findAll(pageable);
	}

	public Page<Infrastructure> getAllInfrastructures(Specification<Infrastructure> spec, Pageable pageable) {
		return infrastructureDao.findAll(spec, pageable);
	}

	public ResponseEntity<InfrastructurePageDTO> getInfrastructures(InfrastructureSearchDTO infrastructureSearchDTO) {
	
			Integer infrastructureId = infrastructureSearchDTO.getInfrastructureId(); 
     			String sortBy = infrastructureSearchDTO.getSortBy();
			String sortOrder = infrastructureSearchDTO.getSortOrder();
			String searchQuery = infrastructureSearchDTO.getSearchQuery();
			Integer page = infrastructureSearchDTO.getPage();
			Integer size = infrastructureSearchDTO.getSize();

	        Specification<Infrastructure> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, infrastructureId, "infrastructureId"); 
			
			
			
			
			

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

		Page<Infrastructure> infrastructures = this.getAllInfrastructures(spec, pageable);
		
		//System.out.println(String.valueOf(infrastructures.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(infrastructures.getTotalPages()));
		
		List<Infrastructure> infrastructuresList = infrastructures.getContent();
		
		InfrastructureConvertCriteriaDTO convertCriteria = new InfrastructureConvertCriteriaDTO();
		List<InfrastructureDTO> infrastructureDTOs = this.convertInfrastructuresToInfrastructureDTOs(infrastructuresList,convertCriteria);
		
		InfrastructurePageDTO infrastructurePageDTO = new InfrastructurePageDTO();
		infrastructurePageDTO.setInfrastructures(infrastructureDTOs);
		infrastructurePageDTO.setTotalElements(infrastructures.getTotalElements());
		return ResponseEntity.ok(infrastructurePageDTO);
	}

	public List<InfrastructureDTO> convertInfrastructuresToInfrastructureDTOs(List<Infrastructure> infrastructures, InfrastructureConvertCriteriaDTO convertCriteria) {
		
		List<InfrastructureDTO> infrastructureDTOs = new ArrayList<InfrastructureDTO>();
		
		for (Infrastructure infrastructure : infrastructures) {
			infrastructureDTOs.add(convertInfrastructureToInfrastructureDTO(infrastructure,convertCriteria));
		}
		
		return infrastructureDTOs;

	}
	
	public InfrastructureDTO convertInfrastructureToInfrastructureDTO(Infrastructure infrastructure, InfrastructureConvertCriteriaDTO convertCriteria) {
		
		InfrastructureDTO infrastructureDTO = new InfrastructureDTO();

		infrastructureDTO.setInfrastructureId(infrastructure.getInfrastructureId());

		infrastructureDTO.setSchools(infrastructure.getSchools());

		infrastructureDTO.setHospitals(infrastructure.getHospitals());

		infrastructureDTO.setRoads(infrastructure.getRoads());

		infrastructureDTO.setCleanEnergySources(infrastructure.getCleanEnergySources());
		
		return infrastructureDTO;
	}

	public ResultDTO updateInfrastructure(InfrastructureDTO infrastructureDTO, RequestDTO requestDTO) {
		
		Infrastructure infrastructure = infrastructureDao.getById(infrastructureDTO.getInfrastructureId());
		
		infrastructure.setInfrastructureId(ControllerUtils.setValue(infrastructure.getInfrastructureId(), infrastructureDTO.getInfrastructureId()));
		
		infrastructure.setSchools(ControllerUtils.setValue(infrastructure.getSchools(), infrastructureDTO.getSchools()));
		
		infrastructure.setHospitals(ControllerUtils.setValue(infrastructure.getHospitals(), infrastructureDTO.getHospitals()));
		
		infrastructure.setRoads(ControllerUtils.setValue(infrastructure.getRoads(), infrastructureDTO.getRoads()));
		
		infrastructure.setCleanEnergySources(ControllerUtils.setValue(infrastructure.getCleanEnergySources(), infrastructureDTO.getCleanEnergySources()));

        infrastructure = infrastructureDao.save(infrastructure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InfrastructureDTO getInfrastructureDTOById(Integer infrastructureId) {
	
		Infrastructure infrastructure = infrastructureDao.getById(infrastructureId);
		
		InfrastructureConvertCriteriaDTO convertCriteria = new InfrastructureConvertCriteriaDTO();
		return(this.convertInfrastructureToInfrastructureDTO(infrastructure,convertCriteria));
	}

}
