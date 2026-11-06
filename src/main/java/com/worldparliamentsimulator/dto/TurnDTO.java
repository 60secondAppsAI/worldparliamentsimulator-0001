package com.worldparliamentsimulator.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TurnDTO {

	private Integer turnId;

	private String actionChosen;

	private String worldReaction;

	private String randomEvent;

	private int month;






}
