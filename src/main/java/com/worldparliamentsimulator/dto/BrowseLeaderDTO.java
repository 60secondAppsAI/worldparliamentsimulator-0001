package com.worldparliamentsimulator.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseLeaderDTO {

	private Integer ownerId;

	private Integer leaderId;

	private Integer leaderStatus;
	
	private Integer nextOrPrevious;
}

