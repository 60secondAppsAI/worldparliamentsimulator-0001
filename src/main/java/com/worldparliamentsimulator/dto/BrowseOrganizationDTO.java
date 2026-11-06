package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseOrganizationDTO {

	private Integer ownerId;

	private Integer organizationId;

	private Integer organizationStatus;
	
	private Integer nextOrPrevious;
}

