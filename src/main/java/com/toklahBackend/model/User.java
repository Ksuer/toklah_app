package com.toklahBackend.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "TOKLAH_USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String firstName;
	private String fatherName;
	private String grandFatherName;
	private String lastName;
	@Column(unique = true)
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String countryKey;
	@Column(unique = true)
	private String mobileNumber;
	private Date birthDate;
	private String gender;
	private String occupation;
	private String specialization;
	private String educationalLevel;
	private String t_shirtSize;
	private String ibanNumber;
	private String language;
	private String aboutMe;
	private int organizingEventNumber;
	private int volunteeringEventNumber;
	
	//User registration date ()
	/*@CreationTimestamp
	private LocalDate registerationDate;*/

	@CreationTimestamp
	@Column(name = "creationdate")
	private Date creationdate;

	@OneToOne
	private ImageInfo userImage;
	
	@OneToMany(mappedBy = "user",targetEntity = Ticket.class, cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ticketNumber")
	@JsonIgnore
    private Set <Ticket> ticket;
	
	public User() {
		super();
	}
	
	public User(int userId, String firstName, String fatherName, String grandFatherName, String lastName, String email,
			String password, String countryKey, String mobileNumber, Date birthDate, String gender, String occupation,
			String specialization, String educationalLevel, String t_shirtSize, String ibanNumber, String language,
			String aboutMe) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.fatherName = fatherName;
		this.grandFatherName = grandFatherName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.countryKey = countryKey;
		this.mobileNumber = mobileNumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.occupation = occupation;
		this.specialization = specialization;
		this.educationalLevel = educationalLevel;
		this.t_shirtSize = t_shirtSize;
		this.ibanNumber = ibanNumber;
		this.language = language;
		this.aboutMe = aboutMe;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getGrandFatherName() {
		return grandFatherName;
	}
	public void setGrandFatherName(String grandFatherName) {
		this.grandFatherName = grandFatherName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountryKey() {
		return countryKey;
	}
	public void setCountryKey(String countryKey) {
		this.countryKey = countryKey;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getEducationalLevel() {
		return educationalLevel;
	}
	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}
	public String getT_shirtSize() {
		return t_shirtSize;
	}
	public void setT_shirtSize(String t_shirtSize) {
		this.t_shirtSize = t_shirtSize;
	}
	public String getIbanNumber() {
		return ibanNumber;
	}
	public void setIbanNumber(String ibanNumber) {
		this.ibanNumber = ibanNumber;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	
	/*public LocalDate getRegisterationDate() {
		return registerationDate;
	}

	public void setRegisterationDate(LocalDate registerationDate) {
		this.registerationDate = registerationDate;
	}*/
	

	/*public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}*/

	public int getOrganizingEventNumber() {
		return organizingEventNumber;
	}

	public void setOrganizingEventNumber(int organizingEventNumber) {
		this.organizingEventNumber = organizingEventNumber;
	}

	public int getVolunteeringEventNumber() {
		return volunteeringEventNumber;
	}

	public void setVolunteeringEventNumber(int volunteeringEventNumber) {
		this.volunteeringEventNumber = volunteeringEventNumber;
	}

	public ImageInfo getUserImage() {
		return userImage;
	}

	public void setUserImage(ImageInfo userImage) {
		this.userImage = userImage;
	}

	public Set<Ticket> getTicket() {
		return ticket;
	}

	public void setTicket(Set<Ticket> ticket) {
		this.ticket = ticket;
	}
	
}
