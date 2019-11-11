package com.toklahBackend.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Entity
@Table(name = "TOKLAH_COMPLAINT")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int complainId;
	private String complainText;
	
	@OneToOne
	private User user;
	
	@CreationTimestamp
	@Column(name = "creationDate")
	private Date creationDate;

	public int getComplainId() {
		return complainId;
	}

	public void setComplainId(int complainId) {
		this.complainId = complainId;
	}

	public String getComplainText() {
		return complainText;
	}

	public void setComplainText(String complainText) {
		this.complainText = complainText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
