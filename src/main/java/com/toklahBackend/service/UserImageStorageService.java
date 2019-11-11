package com.toklahBackend.service;

import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.model.ImageInfo;

public interface UserImageStorageService {

	public ImageInfo storeFile(MultipartFile file, int userId) throws Exception;
}
