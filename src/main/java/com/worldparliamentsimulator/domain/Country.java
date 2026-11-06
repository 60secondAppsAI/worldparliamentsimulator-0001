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
@Table(name="countrys")
@Getter @Setter @NoArgsConstructor
public class Country {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="country_id")
	private Integer countryId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="continent")
	private String continent;
    
  	@Column(name="flag_emoji")
	private String flagEmoji;
    
  	@Column(name="capital")
	private String capital;
    
  	@Column(name="gdp")
	private double gdp;
    
  	@Column(name="population")
	private int population;
    
  	@Column(name="army_size")
	private int armySize;
    
  	@Column(name="resources")
	private String resources;
    

}
