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
import com.worldparliamentsimulator.dao.PeopleDAO;
import com.worldparliamentsimulator.domain.People;
import com.worldparliamentsimulator.dto.PeopleDTO;
import com.worldparliamentsimulator.dto.PeopleSearchDTO;
import com.worldparliamentsimulator.dto.PeoplePageDTO;
import com.worldparliamentsimulator.dto.PeopleConvertCriteriaDTO;
import com.worldparliamentsimulator.dto.common.RequestDTO;
import com.worldparliamentsimulator.dto.common.ResultDTO;
import com.worldparliamentsimulator.service.PeopleService;
import com.worldparliamentsimulator.util.ControllerUtils;


@Service
public class PeopleServiceImpl extends GenericServiceImpl<People, Integer> implements PeopleService {

    private final static Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

	@Autowired
	PeopleDAO peopleDao;

	

	@Override
	public GenericDAO<People, Integer> getDAO() {
		return (GenericDAO<People, Integer>) peopleDao;
	}
	
	public List<People> findAll () {
		List<People> peoples = peopleDao.findAll();
		
		return peoples;	
		
	}

	public ResultDTO addPeople(PeopleDTO peopleDTO, RequestDTO requestDTO) {

		People people = new People();

		people.setPeopleId(peopleDTO.getPeopleId());

		people.setHappiness(peopleDTO.getHappiness());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		people = peopleDao.save(people);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<People> getAllPeoples(Pageable pageable) {
		return peopleDao.findAll(pageable);
	}

	public Page<People> getAllPeoples(Specification<People> spec, Pageable pageable) {
		return peopleDao.findAll(spec, pageable);
	}

	public ResponseEntity<PeoplePageDTO> getPeoples(PeopleSearchDTO peopleSearchDTO) {
	
			Integer peopleId = peopleSearchDTO.getPeopleId(); 
  			String sortBy = peopleSearchDTO.getSortBy();
			String sortOrder = peopleSearchDTO.getSortOrder();
			String searchQuery = peopleSearchDTO.getSearchQuery();
			Integer page = peopleSearchDTO.getPage();
			Integer size = peopleSearchDTO.getSize();

	        Specification<People> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, peopleId, "peopleId"); 
			
			

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

		Page<People> peoples = this.getAllPeoples(spec, pageable);
		
		//System.out.println(String.valueOf(peoples.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(peoples.getTotalPages()));
		
		List<People> peoplesList = peoples.getContent();
		
		PeopleConvertCriteriaDTO convertCriteria = new PeopleConvertCriteriaDTO();
		List<PeopleDTO> peopleDTOs = this.convertPeoplesToPeopleDTOs(peoplesList,convertCriteria);
		
		PeoplePageDTO peoplePageDTO = new PeoplePageDTO();
		peoplePageDTO.setPeoples(peopleDTOs);
		peoplePageDTO.setTotalElements(peoples.getTotalElements());
		return ResponseEntity.ok(peoplePageDTO);
	}

	public List<PeopleDTO> convertPeoplesToPeopleDTOs(List<People> peoples, PeopleConvertCriteriaDTO convertCriteria) {
		
		List<PeopleDTO> peopleDTOs = new ArrayList<PeopleDTO>();
		
		for (People people : peoples) {
			peopleDTOs.add(convertPeopleToPeopleDTO(people,convertCriteria));
		}
		
		return peopleDTOs;

	}
	
	public PeopleDTO convertPeopleToPeopleDTO(People people, PeopleConvertCriteriaDTO convertCriteria) {
		
		PeopleDTO peopleDTO = new PeopleDTO();

		peopleDTO.setPeopleId(people.getPeopleId());

		peopleDTO.setHappiness(people.getHappiness());
		
		return peopleDTO;
	}

	public ResultDTO updatePeople(PeopleDTO peopleDTO, RequestDTO requestDTO) {
		
		People people = peopleDao.getById(peopleDTO.getPeopleId());
		
		people.setPeopleId(ControllerUtils.setValue(people.getPeopleId(), peopleDTO.getPeopleId()));
		
		people.setHappiness(ControllerUtils.setValue(people.getHappiness(), peopleDTO.getHappiness()));

        people = peopleDao.save(people);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PeopleDTO getPeopleDTOById(Integer peopleId) {
	
		People people = peopleDao.getById(peopleId);
		
		PeopleConvertCriteriaDTO convertCriteria = new PeopleConvertCriteriaDTO();
		return(this.convertPeopleToPeopleDTO(people,convertCriteria));
	}

}
