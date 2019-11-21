package com.toklahBackend.service;

import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Login;

import javassist.NotFoundException;

public interface AdminService {

	Admin register(Admin admin) throws Exception;

	Admin login(Login login) throws NotFoundException, Exception;

	Admin addAdmins(Admin admin, int priority) throws Exception;
	
}
