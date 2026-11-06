package com.worldparliamentsimulator.dao;

import java.util.List;
import java.util.Date;

import com.worldparliamentsimulator.dao.GenericDAO;
import com.worldparliamentsimulator.domain.Ideology;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface IdeologyDAO extends GenericDAO<Ideology, Integer> {
  
	List<Ideology> findAll();
	


}

