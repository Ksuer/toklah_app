package com.toklahBackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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

	public ImageInfo() {
		super();
	}

	public ImageInfo(String fileName, String fileType, String fileUri , User user) {
		this.fileName = fileName;
	    this.fileType = fileType;
	    this.fileUri = fileUri;
	    this.user= user;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
