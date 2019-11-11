package com.toklahBackend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "TOKLAH_COMPANY")
public class Company {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int companyId;
	private String companyName;
	private String companyActivityType;
	private int companyCrNumber;
	@Column(unique = true)
	private String companyEmail;
	private String contactNumber1;
	private String contactNumber2;

	@OneToMany(mappedBy = "company",targetEntity = Event.class, cascade =CascadeType.ALL,  fetch = FetchType.EAGER)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "orderId")
	@JsonIgnore
	private Set <Event> event;
	
	public Company(int companyId, String companyName, String companyActivityType, int companyCrNumber,
			String companyEmail, String contactNumber1, String contactNumber2) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyActivityType = companyActivityType;
		this.companyCrNumber = companyCrNumber;
		this.companyEmail = companyEmail;
		this.contactNumber1 = contactNumber1;
		this.contactNumber2 = contactNumber2;
	}

	
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyActivityType() {
		return companyActivityType;
	}

	public void setCompanyActivityType(String companyActivityType) {
		this.companyActivityType = companyActivityType;
	}

	public int getCompanyCrNumber() {
		return companyCrNumber;
	}

	public void setCompanyCrNumber(int companyCrNumber) {
		this.companyCrNumber = companyCrNumber;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getContactNumber1() {
		return contactNumber1;
	}

	public void setContactNumber1(String contactNumber1) {
		this.contactNumber1 = contactNumber1;
	}

	public String getContactNumber2() {
		return contactNumber2;
	}

	public void setContactNumber2(String contactNumber2) {
		this.contactNumber2 = contactNumber2;
	}

	public Set<Event> getEvent() {
		return event;
	}

	public void setEvent(Set<Event> event) {
		this.event = event;
	}


}
