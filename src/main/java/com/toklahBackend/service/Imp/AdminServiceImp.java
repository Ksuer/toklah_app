package com.toklahBackend.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.AdminDao;
import com.toklahBackend.exception.ConflictException;
import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Login;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.service.AdminService;


@Component
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminDao adminDao;
	
	@Value("Authorization")
	private String tokenHeader;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;
	
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
			admin.setPriority(1);
			newAdmin= adminDao.save(admin);
			return newAdmin;
		}
	}
	
	@Override
	public Admin addAdmins(Admin admin, int priority) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(admin.getPassword());
		if (adminDao.mobileOremail(admin.getEmail()) != null
				|| adminDao.mobileOremail(admin.getMobile()) != null) {
			throw new Exception("this admin already registered");
		}else if(priority < 1 ) {
			throw new ConflictException("wrong type");
		}else {
			Admin newAdmin = new Admin();
			admin.setPassword(hashedPass);
			admin.setPriority(priority);
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
					final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getMobileOrEmail());
					final String token = jwtTokenUtil.generateToken(userDetails);
					admin.setToken(token);
					return admin;
				} else {
					throw new Exception("Password not match");
				}
			}
		}
	}

}
