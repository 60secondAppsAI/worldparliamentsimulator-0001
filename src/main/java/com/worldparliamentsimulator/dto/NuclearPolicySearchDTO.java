package com.worldparliamentsimulator.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NuclearPolicySearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer nuclearPolicyId;
	
	private boolean hasNukes;
	
	private boolean isProtected;
	
}
