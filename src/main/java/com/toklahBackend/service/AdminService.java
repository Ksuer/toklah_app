package com.toklahBackend.service;

import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Login;

import javassist.NotFoundException;

public interface AdminService {

	Admin register(Admin admin) throws Exception;

	Admin login(Login login) throws NotFoundException, Exception;

	Admin addAdmins(Admin admin, int priority) throws Exception;

	Admin getAdminByToken(String token);

	void accepteventrequest(int eventId, boolean isValid);

	Admin editAdmin(int adminId, Admin admin);

	void changePassword(String oldPassword, String newPassword, int adminId);

}
