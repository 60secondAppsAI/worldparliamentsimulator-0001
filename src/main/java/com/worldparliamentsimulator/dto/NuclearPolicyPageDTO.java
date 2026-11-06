package com.worldparliamentsimulator.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NuclearPolicyPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<NuclearPolicyDTO> nuclearPolicys;
}





