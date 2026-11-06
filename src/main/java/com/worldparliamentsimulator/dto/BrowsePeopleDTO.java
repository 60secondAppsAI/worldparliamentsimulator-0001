package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePeopleDTO {

	private Integer ownerId;

	private Integer peopleId;

	private Integer peopleStatus;
	
	private Integer nextOrPrevious;
}

