package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter @Setter
@NoArgsConstructor
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
	private String skill;
	private String token;
	private String aboutMe;
	private int organizingEventNumber;
	private int volunteeringEventNumber;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String lastPayment;
	private Boolean isLock;  // true -> if the user didn't do the otp
	private Boolean isPaid; //true -> if the user pay for the type


	@CreationTimestamp
	@Column(name = "creationdate")
	private Date creationdate;

	private String userImage;
	
	@OneToMany(mappedBy = "user",targetEntity = Ticket.class, cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ticketNumber")
	@JsonIgnore
    private Set <Ticket> ticket;
	
}
