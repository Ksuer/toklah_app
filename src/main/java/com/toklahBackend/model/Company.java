//package com.toklahBackend.model;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//@Getter @Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "TOKLAH_COMPANY")
//public class Company {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private int companyId;
//	private String companyName;
//	private String companyActivityType;
//	private int companyCrNumber;
//	@Column(unique = true)
//	private String companyEmail;
//	private String contactNumber1;
//	private String contactNumber2;
//
//	@OneToMany(mappedBy = "company",targetEntity = Event.class, cascade =CascadeType.ALL,  fetch = FetchType.EAGER)
//	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "orderId")
//	@JsonIgnore
//	private Set <Event> event;
//
//}
