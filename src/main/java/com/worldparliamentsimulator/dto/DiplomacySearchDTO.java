package com.worldparliamentsimulator.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiplomacySearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer diplomacyId;
	
	private int alliancesFormed;
	
	private int warsDeclared;
	
	private int peaceTreatiesSigned;
	
	private int economicEmbargos;
	
}
