package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseEspionageDTO {

	private Integer ownerId;

	private Integer espionageId;

	private Integer espionageStatus;
	
	private Integer nextOrPrevious;
}

