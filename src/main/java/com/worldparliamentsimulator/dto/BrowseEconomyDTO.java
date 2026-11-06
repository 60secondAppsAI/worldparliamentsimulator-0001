package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseEconomyDTO {

	private Integer ownerId;

	private Integer economyId;

	private Integer economyStatus;
	
	private Integer nextOrPrevious;
}

