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
	
	private String name; //eventName
	private String eventType;
	private Date date;
	private Time startTime;
	private Time endTime;
	private String mobileNumber;
	private float eventReward;
	
	public Ticket(String name, Date date, Time startTime, Time endTime, String mobileNumber,
			float eventReward) {
		super();
		this.name = name;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mobileNumber = mobileNumber;
		this.eventReward = eventReward;
	}

	public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getName() {
		return name;
	}



}
