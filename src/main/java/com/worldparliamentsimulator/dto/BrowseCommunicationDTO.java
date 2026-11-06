package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseCommunicationDTO {

	private Integer ownerId;

	private Integer communicationId;

	private Integer communicationStatus;
	
	private Integer nextOrPrevious;
}

