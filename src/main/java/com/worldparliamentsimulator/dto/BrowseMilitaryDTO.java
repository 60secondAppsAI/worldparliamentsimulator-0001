package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMilitaryDTO {

	private Integer ownerId;

	private Integer militaryId;

	private Integer militaryStatus;
	
	private Integer nextOrPrevious;
}

