package com.toklahBackend.service.Imp;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.User;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.sendEmail.SendEmail;
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
	SendEmail serviceSendEmail;
	
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
		if (token != null) {
			token = token.replace("Bearer ", "");
			String userName = jwtTokenUtil.getUsernameFromToken(token);
			
			Admin admin = adminDao.mobileOremail(userName);
			if( admin != null) {
				return admin;
			}else {
				throw new NotFoundException();
			}
		}else {
			throw new NotFoundException();
		}
		
	}

	@Override
	public Event acceptEventRequest(int eventId, boolean isValid) {
		Event event = eventDao.findOne(eventId);
		try {
			if( event != null) {
				event.setIsValid(isValid);
				eventDao.save(event);
			}else {
				throw new BadRequestException("event stauts not changed");
			}
		}catch(Exception ex) {
			throw new BadRequestException("event stauts not changed");
		}
		return event;
	}
	
	@Override
	public Event changeEventType(int eventId, boolean isPremium) {
		Event event = eventDao.findOne(eventId);
		try {
			if( event != null) {
				event.setIsPremium(isPremium);
				eventDao.save(event);
			}else {
				throw new BadRequestException("event type not changed");
			}
		}catch(Exception ex) {
			throw new BadRequestException("event type not changed");
		}
		return event;
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
		
		if (oldPassword == null || newPassword == null) {
			throw new BadRequestException("MSG001");
		}
		
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
	
	@Override
	public void emailchangePassword(SentEmail email) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (email == null) {
			throw new BadRequestException("MSG001");
		}
		
		Admin admin = new Admin();
		admin = adminDao.findByEmail(email.getEmail());
		if (admin == null) {
			throw new NotFoundException("MSG006");
		} else {
			String password = RandomStringUtils.random(8,
					"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
			String text = "Your new password is " + password;
			try {
				String title = "Forgot your password? ";
				serviceSendEmail.sendMail(email.getEmail(), text, title);
				admin.setPassword(passwordEncoder.encode(password));
				adminDao.save(admin);
			} catch (Exception e) {
				throw new BadRequestException("MSG014");
			}
		}

	}

}
