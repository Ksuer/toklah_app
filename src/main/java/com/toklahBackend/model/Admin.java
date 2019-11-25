package com.toklahBackend.model;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "TOKLAH_ADMIN")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int adminId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String mobile;
	private String token;
	private int priority; // 1 is the highest

	@CreationTimestamp
	@Column(name = "creationdate")
	private Date creationdate;
	
	public Admin(int adminId, String password, String email, String mobile) {
		super();
		this.adminId = adminId;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
	}

}
