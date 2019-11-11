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
	
	public Event() {

	}
	
	public Event(int eventId, String eventTitle, String eventType, String eventTargetGroup, String eventLocation,
			Date eventDate, Time eventStartTime, Time eventEndtTime, int eventOrganizerNumber, float eventReward,
			String eventSummary) {
		super();
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.eventType = eventType;
		this.eventTargetGroup = eventTargetGroup;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
		this.eventStartTime = eventStartTime;
		this.eventEndtTime = eventEndtTime;
		this.eventOrganizerNumber = eventOrganizerNumber;
		this.eventReward = eventReward;
		this.eventSummary = eventSummary;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventTargetGroup() {
		return eventTargetGroup;
	}
	public void setEventTargetGroup(String eventTargetGroup) {
		this.eventTargetGroup = eventTargetGroup;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Time getEventStartTime() {
		return eventStartTime;
	}
	public void setEventStartTime(Time eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	public Time getEventEndtTime() {
		return eventEndtTime;
	}
	public void setEventEndtTime(Time eventEndtTime) {
		this.eventEndtTime = eventEndtTime;
	}
	public int getEventOrganizerNumber() {
		return eventOrganizerNumber;
	}
	public void setEventOrganizerNumber(int eventOrganizerNumber) {
		this.eventOrganizerNumber = eventOrganizerNumber;
	}
	public float getEventReward() {
		return eventReward;
	}
	public void setEventReward(float eventReward) {
		this.eventReward = eventReward;
	}
	public String getEventSummary() {
		return eventSummary;
	}
	public void setEventSummary(String eventSummary) {
		this.eventSummary = eventSummary;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
