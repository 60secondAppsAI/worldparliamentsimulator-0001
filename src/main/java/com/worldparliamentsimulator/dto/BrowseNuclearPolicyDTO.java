package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseNuclearPolicyDTO {

	private Integer ownerId;

	private Integer nuclearPolicyId;

	private Integer nuclearPolicyStatus;
	
	private Integer nextOrPrevious;
}

