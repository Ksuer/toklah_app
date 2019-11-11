package com.toklahBackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	
	
	public Admin() {
		super();
	}

	public Admin(int adminId, String password, String email, String mobile) {
		super();
		this.adminId = adminId;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
