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
@Table(name="economys")
@Getter @Setter @NoArgsConstructor
public class Economy {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="economy_id")
	private Integer economyId;
    
  	@Column(name="investment_in_education")
	private double investmentInEducation;
    
  	@Column(name="investment_in_health")
	private double investmentInHealth;
    
  	@Column(name="investment_in_factories")
	private double investmentInFactories;
    
  	@Column(name="military_spending")
	private double militarySpending;
    

}
