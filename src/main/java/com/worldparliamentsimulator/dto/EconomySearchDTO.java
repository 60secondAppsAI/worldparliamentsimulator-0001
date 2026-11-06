package com.worldparliamentsimulator.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EconomySearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer economyId;
	
	private double investmentInEducation;
	
	private double investmentInHealth;
	
	private double investmentInFactories;
	
	private double militarySpending;
	
}
