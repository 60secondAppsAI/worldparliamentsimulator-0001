package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInfrastructureDTO {

	private Integer ownerId;

	private Integer infrastructureId;

	private Integer infrastructureStatus;
	
	private Integer nextOrPrevious;
}

