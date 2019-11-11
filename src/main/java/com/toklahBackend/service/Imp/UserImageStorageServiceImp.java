package com.toklahBackend.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.toklahBackend.dao.UserDao;
import com.toklahBackend.dao.UserImageDao;
import com.toklahBackend.model.ImageInfo;
import com.toklahBackend.model.User;
import com.toklahBackend.service.UserImageStorageService;

@Service
public class UserImageStorageServiceImp implements UserImageStorageService {

	 @Autowired
	 private UserImageDao userImageDao;
	 
	 @Autowired
	 private UserDao userDao;
	 
	 public ImageInfo storeFile(MultipartFile file, int userId) throws Exception {
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			User user= userDao.findOne(userId);

	        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	        		.toUriString();
	            
	        ImageInfo userImage = new ImageInfo(fileName, file.getContentType(), fileUri , user);

	        user.setUserImage(userImage);

	        return userImageDao.save(userImage);
	        
	    }

}
