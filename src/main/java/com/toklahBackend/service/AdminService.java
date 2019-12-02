package com.toklahBackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Login;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;

import javassist.NotFoundException;

public interface AdminService {

	Admin register(Admin admin) throws Exception;

	Admin login(Login login) throws NotFoundException, Exception;

	Admin addAdmins(Admin admin, int priority) throws Exception;

	//Admin getAdmin(int adminId);
	
	Admin getAdmin(String token);

	void accepteventrequest(int eventId);

	Admin editAdmin(int adminId, Admin admin);

	void changePassword(String oldPassword, String newPassword, int adminId);

}
