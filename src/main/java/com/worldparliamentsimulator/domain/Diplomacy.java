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
@Table(name="diplomacys")
@Getter @Setter @NoArgsConstructor
public class Diplomacy {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="diplomacy_id")
	private Integer diplomacyId;
    
  	@Column(name="alliances_formed")
	private int alliancesFormed;
    
  	@Column(name="wars_declared")
	private int warsDeclared;
    
  	@Column(name="peace_treaties_signed")
	private int peaceTreatiesSigned;
    
  	@Column(name="economic_embargos")
	private int economicEmbargos;
    

}
