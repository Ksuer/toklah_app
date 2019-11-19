package com.toklahBackend.model;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "TOKLAH_IMAGE")
public class UserImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	private String fileName;
	private String fileUri;

	@OneToOne
	private User user;
	
	@CreationTimestamp
	@Column(name = "creationdate")
	private Date creationdate;
	
	public UserImage(String fileName , String fileUri , User user) {
		this.fileName = fileName;
	    this.fileUri = fileUri;
	    this.user= user;
	}

}
