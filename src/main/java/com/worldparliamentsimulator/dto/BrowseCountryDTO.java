package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseCountryDTO {

	private Integer ownerId;

	private Integer countryId;

	private Integer countryStatus;
	
	private Integer nextOrPrevious;
}

