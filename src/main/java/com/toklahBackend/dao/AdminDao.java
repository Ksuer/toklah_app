package com.toklahBackend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.Admin;

public interface AdminDao extends CrudRepository<Admin, Integer>{

	@Query("SELECT a FROM Admin a where a.mobile = :mobileOremail or a.email = :mobileOremail")
	Admin mobileOremail(@Param("mobileOremail") String mobileOremail);
	
	@Query("SELECT a FROM Admin a where (a.mobile = :mobileOremail or a.email = :mobileOremail) AND a.password = :password")
	Admin login(@Param("mobileOremail")String mobileOremail,@Param("password") String password);


}
