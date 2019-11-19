package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.mapping.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toklahBackend.unit.EventTarget;
import com.toklahBackend.unit.EventType;

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
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	@Enumerated(EnumType.STRING)
	private EventTarget eventTargetGroup;
	private Double lat;
	private Double lng;
	private String eventImage;
	private Date eventDate;
	private String eventStartTime;
	private String eventEndtTime;
	private int eventOrganizerNumber;
	private float eventReward;
	private String eventSummary;
	private Boolean isVolunteering;
	private Boolean isPremium;
	private Boolean isValid;
	
	private String companyName;
	private String companyActivityType;
	private String companyCrNumber;
	private String companyEmail;
	private String contactNumber1;
	private String contactNumber2;
		
}
