package com.toklahBackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "TOKLAH_EVENTIMAGE")
public class EventImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	private String fileName;
	private String fileUri;

	/*@JsonBackReference("event-image")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "event")
	private Event event;*/

}
