package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseContinentProblemDTO {

	private Integer ownerId;

	private Integer continentProblemId;

	private Integer continentProblemStatus;
	
	private Integer nextOrPrevious;
}

