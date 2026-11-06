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
@Table(name="infrastructures")
@Getter @Setter @NoArgsConstructor
public class Infrastructure {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="infrastructure_id")
	private Integer infrastructureId;
    
  	@Column(name="schools")
	private int schools;
    
  	@Column(name="hospitals")
	private int hospitals;
    
  	@Column(name="roads")
	private int roads;
    
  	@Column(name="clean_energy_sources")
	private int cleanEnergySources;
    

}
