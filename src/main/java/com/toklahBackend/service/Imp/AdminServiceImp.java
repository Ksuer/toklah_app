package com.toklahBackend.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.AdminDao;
import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.TicketDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.exception.BadRequestException;
import com.toklahBackend.exception.ConflictException;
import com.toklahBackend.exception.NotFoundException;
import com.toklahBackend.exception.UnAuthorizedException;
import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.Login;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.service.AdminService;


@Component
public class AdminServiceImp implements AdminService{

	@Autowired
	AdminDao adminDao;
	@Autowired
	UserDao userDao;
	@Autowired
	TicketDao ticketDao;
	@Autowired
	EventDao eventDao;
	
	@Value("Authorization")
	private String tokenHeader;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	
	@Autowired
	@Qualifier("customAdminDetailsService")
	private UserDetailsService adminDetailsService;
	
	@Override
	public Admin register(Admin admin) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(admin.getPassword());
		
		if (adminDao.mobileOremail(admin.getEmail()) != null
				|| adminDao.mobileOremail(admin.getMobile()) != null) {
			throw new ConflictException("this admin already registered");
		}else {
			Admin newAdmin = new Admin();
			admin.setPassword(hashedPass);
			admin.setPriority(1);
			newAdmin= adminDao.save(admin);
			return newAdmin;
		}
	}
	
	@Override
	public Admin addAdmins(Admin admin, int priority) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(admin.getPassword());
		if (adminDao.mobileOremail(admin.getEmail()) != null
				|| adminDao.mobileOremail(admin.getMobile()) != null) {
			throw new ConflictException("this admin already registered");
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
	public Admin login(Login login) {
		
		if (login.getMobileOrEmail() == null) {
			throw new BadRequestException("MSG001");
		}
		if (login.getPassword() == null) {
			throw new BadRequestException("MSG001");
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			Admin admin = adminDao.mobileOremail(login.getMobileOrEmail());
			if (admin == null) {
				throw new NotFoundException("MSG013");
			} else {
				if (passwordEncoder.matches(login.getPassword(), admin.getPassword())) {
					final UserDetails userDetails = adminDetailsService.loadUserByUsername(login.getMobileOrEmail());
					final String token = jwtTokenUtil.generateToken(userDetails);
					admin.setToken(token);
					return admin;
				} else {
					throw new UnAuthorizedException("MSG002");
				}
			}
		}
	}
	
	@Override
	public Admin getAdminByToken(String token) {
		String userName = jwtTokenUtil.getUsernameFromToken(token);
	
		Admin admin = adminDao.mobileOremail(userName);
		if( admin != null) {
			return admin;
		}else {
			throw new NotFoundException();
		}
		
	}

	@Override
	public void accepteventrequest(int eventId, boolean isValid) {
		Event event = eventDao.findOne(eventId);
		try {
		if( event != null) {
			event.setIsValid(isValid);
		}
		}catch(Exception ex) {
			throw new BadRequestException("event stauts not changed");
		}
	}

	@Override
	public Admin editAdmin(int adminId, Admin admin) {
		Admin newAdmin = adminDao.findOne(adminId);

		if (admin.getEmail() != null) {

			if (!newAdmin.getEmail().trim().equalsIgnoreCase(admin.getEmail().trim())) {
				Admin checkEmail = adminDao.findByEmail(admin.getEmail());
				if (checkEmail == null) {
					newAdmin.setEmail(admin.getEmail());
				} else {
					throw new UnAuthorizedException("MSG003");
				}
			}
		}

		if (admin.getMobile() != null) {
			if (!newAdmin.getMobile().trim().equalsIgnoreCase(admin.getMobile().trim())) {
				Admin checkMobile = adminDao.findByMobile(admin.getMobile());
				if (checkMobile == null) {
					newAdmin.setMobile(admin.getMobile());
				} else {
					throw new UnAuthorizedException("MSG018");
				}
			}
		}
		
		adminDao.save(newAdmin);
		final UserDetails userDetails = adminDetailsService.loadUserByUsername(newAdmin.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		newAdmin.setToken(token);
		
		return newAdmin;
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword, int adminId) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Admin admin = new Admin();
		admin = adminDao.findOne(adminId);
		if (admin == null) {
			throw new NotFoundException();
		} else {
			String dbPass = admin.getPassword();
			if (passwordEncoder.matches(oldPassword, dbPass)) {
				admin.setPassword(passwordEncoder.encode(newPassword));
				adminDao.save(admin);
			} else {
				throw new UnAuthorizedException("MSG011");
			}
		}
		
	}

}
