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

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name="TOKLAH_TICKET")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ticketNumber;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user")
	private User user;
	private int eventId;
	private String name; //eventName
	private String eventType;
	private Date date;
	private String startTime;
	private String endTime;
	private String mobileNumber;
	private float eventReward;
	private Boolean isCanceled;

	public Ticket(int eventId, String name, Date date, String startTime, String endTime, String mobileNumber,

			float eventReward) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mobileNumber = mobileNumber;
		this.eventReward = eventReward;
	}
}
