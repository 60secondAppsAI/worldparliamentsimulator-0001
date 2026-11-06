package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseIdeologyDTO {

	private Integer ownerId;

	private Integer ideologyId;

	private Integer ideologyStatus;
	
	private Integer nextOrPrevious;
}

