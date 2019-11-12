package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "TOKLAH_EVENT")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int eventId;
	private String eventTitle;
	private String eventType;
	private String eventTargetGroup;
	private String eventLocation;
	private Date eventDate;
	private Time eventStartTime;
	private Time eventEndtTime;
	private int eventOrganizerNumber;
	private float eventReward;
	private String eventSummary;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company")
    private Company company;
	
	
}
