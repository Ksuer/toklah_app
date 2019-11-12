package com.toklahBackend.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.AdminDao;
import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Login;
import com.toklahBackend.service.AdminService;


@Component
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminDao adminDao;
	
	@Override
	public Admin register(Admin admin) throws Exception {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(admin.getPassword());
		
		if (adminDao.mobileOremail(admin.getEmail()) != null
				|| adminDao.mobileOremail(admin.getMobile()) != null) {
			throw new Exception("this admin already registered");
		}else {
			Admin newAdmin = new Admin();
			admin.setPassword(hashedPass);
			newAdmin= adminDao.save(admin);
			return newAdmin;
		}
	}
	
	@Override
	public Admin login(Login login) throws Exception {
		
		if (login.getMobileOrEmail() == null) {
			throw new Exception("Missing email or mobile #");
		}
		if (login.getPassword() == null) {
			throw new Exception("Missing Password");
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			Admin admin = adminDao.mobileOremail(login.getMobileOrEmail());
			if (admin == null) {
				throw new Exception("Admin not found");
			} else {
				if (passwordEncoder.matches(login.getPassword(), admin.getPassword())) {
						return admin;
				} else {
					throw new Exception("Password not match");
				}
			}
		}
	}

}
