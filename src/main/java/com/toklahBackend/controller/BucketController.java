package com.toklahBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.service.Imp.EventServiceImp;
import com.toklahBackend.service.Imp.UserServiceImp;

@RestController
@RequestMapping("/image/")
public class BucketController {

	 @Autowired
	 private UserServiceImp userServiceImp;
	 @Autowired
	 private EventServiceImp eventServiceImp;

	HttpHeaders responseHeaders = new HttpHeaders();

	@PostMapping("/{userId}/uploadUserImage")
	@ResponseBody
	public  ResponseEntity<?> uploadUserImage(@RequestPart(value = "file") MultipartFile file, @PathVariable int userId){
    	
		return new ResponseEntity<>(userServiceImp.Addimage(file, userId), responseHeaders, HttpStatus.CREATED);
	}
	
	@PostMapping("/{eventId}/uploadEventImage")
	@ResponseBody
	public ResponseEntity<?>  uploadEventImage(@RequestPart(value = "file") MultipartFile file, @PathVariable int eventId){
   
		return new ResponseEntity<>(eventServiceImp.addImage(file,eventId), responseHeaders, HttpStatus.CREATED);

	}
	
}