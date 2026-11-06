package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTurnDTO {

	private Integer ownerId;

	private Integer turnId;

	private Integer turnStatus;
	
	private Integer nextOrPrevious;
}

