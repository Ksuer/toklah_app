package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "TOKLAH_IMAGE")
public class ImageInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	private String fileName;
	private String fileType;
	private String fileUri;

	@OneToOne
	private User user;

	public ImageInfo(String fileName, String fileType, String fileUri , User user) {
		this.fileName = fileName;
	    this.fileType = fileType;
	    this.fileUri = fileUri;
	    this.user= user;
	}

}
