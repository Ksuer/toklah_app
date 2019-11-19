package com.toklahBackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.EventImageDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.dao.UserImageDao;
import com.toklahBackend.exception.BadRequestException;
import com.toklahBackend.model.UserImage;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.EventImage;
import com.toklahBackend.model.User;
import com.toklahBackend.service.AmazonClient;

@RestController
@RequestMapping("/image/")
public class BucketController {

	private AmazonClient amazonClient;

	 @Autowired
	 private UserDao userDao;
	 @Autowired
	 private UserImageDao userImageDao;
	 @Autowired
	 private EventDao eventDao;
	 @Autowired
	 private EventImageDao eventImageDao;
	
	@Autowired
	BucketController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	@PostMapping("/{userId}/uploadUserImage")
	public String uploadUserImage(@RequestPart(value = "file") MultipartFile file, @PathVariable int userId) throws Exception {
    	
		User user= userDao.findOne(userId);

		UserImage userImage = new UserImage(file.getOriginalFilename() ,  this.amazonClient.uploadFile(file), user);
		
		user.setUserImage(userImage);
		userImageDao.save(userImage);
		
		return this.amazonClient.uploadFile(file);
	}
	
	/*@PostMapping("/{eventId}/uploadEventImage")
	public String uploadEventImage(@RequestPart(value = "file") MultipartFile file, @PathVariable int eventId) throws Exception {
    	
		Event event= eventDao.findOne(eventId);

	}*/
	
}