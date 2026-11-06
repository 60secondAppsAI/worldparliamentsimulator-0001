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
@Table(name="espionages")
@Getter @Setter @NoArgsConstructor
public class Espionage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="espionage_id")
	private Integer espionageId;
    
  	@Column(name="operations_conducted")
	private int operationsConducted;
    

}
