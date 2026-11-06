package com.worldparliamentsimulator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="turns")
@Getter @Setter @NoArgsConstructor
public class Turn {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="turn_id")
	private Integer turnId;
    
  	@Column(name="action_chosen")
	private String actionChosen;
    
  	@Column(name="world_reaction")
	private String worldReaction;
    
  	@Column(name="random_event")
	private String randomEvent;
    
  	@Column(name="month")
	private int month;
    

}
