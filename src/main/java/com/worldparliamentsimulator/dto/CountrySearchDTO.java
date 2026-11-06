package com.worldparliamentsimulator.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CountrySearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer countryId;
	
	private String name;
	
	private String continent;
	
	private String flagEmoji;
	
	private String capital;
	
	private double gdp;
	
	private int population;
	
	private int armySize;
	
	private String resources;
	
}
