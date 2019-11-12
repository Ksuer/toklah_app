package com.toklahBackend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.User;

public interface UserDao extends CrudRepository <User, Integer>{

	User findByEmail(@Param("email")String email);

	@Query("SELECT u FROM User u where (u.mobileNumber = :mobileOremail or u.email = :mobileOremail) AND u.password = :password")
	User login(@Param("mobileOremail")String mobileOremail,@Param("password") String password);
	
	@Query("SELECT u FROM User u where u.mobileNumber = :mobileOremail or u.email = :mobileOremail")
	User mobileOremail(@Param("mobileOremail")String mobileOremail);

	User findByMobileNumber(@Param("mobile")String mobile);
	
}
