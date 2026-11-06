package com.worldparliamentsimulator.service;

import com.worldparliamentsimulator.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}