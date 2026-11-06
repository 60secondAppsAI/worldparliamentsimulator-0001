package com.worldparliamentsimulator.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CountryDTO {

	private Integer countryId;

	private String name;

	private String continent;

	private String flagEmoji;

	private String capital;

	private double gdp;

	private int population;

	private int armySize;

	private String resources;






}
