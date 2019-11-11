package com.toklahBackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.model.ImageInfo;
import com.toklahBackend.payload.UploadFileResponse;
import com.toklahBackend.service.UserImageStorageService;

@RestController
public class ImageController {
	
	 private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	    @Autowired
	    private UserImageStorageService userImageStorageService;

	    @PostMapping("/{userId}/uploadImage")
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int userId) throws Exception {
	    	ImageInfo userImage = userImageStorageService.storeFile(file, userId);

	        return new UploadFileResponse(userImage.getFileName(), file.getContentType(), userId );
	        
	    }
	    
}
