package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseAllianceDTO {

	private Integer ownerId;

	private Integer allianceId;

	private Integer allianceStatus;
	
	private Integer nextOrPrevious;
}

