package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseDiplomacyDTO {

	private Integer ownerId;

	private Integer diplomacyId;

	private Integer diplomacyStatus;
	
	private Integer nextOrPrevious;
}

