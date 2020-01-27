package com.toklahBackend.service.Imp;

import java.util.List;

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
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.sendEmail.SendEmail;
import com.toklahBackend.service.AdminService;
import com.toklahBackend.unit.EventTarget;
import com.toklahBackend.unit.EventType;


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
	public Boolean deleteEventRequest(int eventId) {
		Event event = eventDao.findOne(eventId);
		List<Ticket> ticket = ticketDao.getTicketByEvent(eventId);
			if( event != null) {
				if(ticket.size() == 0) {
					eventDao.delete(event);
					return true;
				}else {
					throw new BadRequestException("Someone has a ticket in this event can not be deleted");
				}
			}else {
				throw new BadRequestException("event not found");
			}
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
	
	@Override
	public Event editEvent(Event event, int eventId, int targetId, int typeId){
		Event newEvent= eventDao.findOne(eventId);
		if (newEvent != null) {
			switch(typeId) {
			case 1: newEvent.setEventType(EventType.EXPOSITION); break; 
			case 2: newEvent.setEventType(EventType.ARTS); break; 
			case 3:	newEvent.setEventType(EventType.MUSIC); break; 
			case 4: newEvent.setEventType(EventType.CONCERT); break; 
			case 5: newEvent.setEventType(EventType.EDUCATIONAL); break; 
			case 6: newEvent.setEventType(EventType.OTHER); break; 
			default:
			}
			
			switch(targetId) {
			case 1: newEvent.setEventTargetGroup(EventTarget.CHILDREN); break; 
			case 2: newEvent.setEventTargetGroup(EventTarget.FAMELY); break; 
			case 3:	newEvent.setEventTargetGroup(EventTarget.FEMALE); break; 
			case 4: newEvent.setEventTargetGroup(EventTarget.MALE); break; 
			default:
			}
			
			if (event.getEventTitle()!=null) {
				newEvent.setEventTitle(event.getEventTitle());
			}
			if (event.getLat()!=null) {
				newEvent.setLat(event.getLat());
			} 
			if (event.getLng()!=null){
				newEvent.setLng(event.getLng());
			}
			if (event.getEventDate()!=null){
				newEvent.setEventDate(event.getEventDate());
			} 
			if (event.getEventStartTime()!=null){
				newEvent.setEventStartTime(event.getEventStartTime());
			}
			if (event.getEventEndtTime()!=null){
				newEvent.setEventEndtTime(event.getEventEndtTime());
			} 
			if (event.getEventOrganizerNumber()!= -1){
				newEvent.setEventOrganizerNumber(event.getEventOrganizerNumber());
			} 
			if (event.getEventSummary()!=null){
				newEvent.setEventSummary(event.getEventSummary());
			}
			if (event.getCompanyName()!=null){
				newEvent.setCompanyName(event.getCompanyName());
			} 
			if (event.getCompanyActivityType()!=null){
				newEvent.setCompanyActivityType(event.getCompanyActivityType());
			} 
			if (event.getCompanyCrNumber()!=null){
				newEvent.setCompanyCrNumber(event.getCompanyCrNumber());
			}
			if (event.getCompanyEmail()!=null){
				newEvent.setCompanyEmail(event.getCompanyEmail());
			} 
			if (event.getContactNumber1()!=null) {
				newEvent.setContactNumber1(event.getContactNumber1());
			}
			if (event.getEventReward() != -1) {
				newEvent.setEventReward(event.getEventReward());
			}
			if (event.getIsVolunteering() != null) {
				newEvent.setIsVolunteering(event.getIsVolunteering());
			}
			if (event.getIsPremium() !=null) {
				newEvent.setIsPremium(event.getIsPremium());
			}
			if (event.getIsValid() != null) {
				newEvent.setIsValid(event.getIsValid());
			}
			if (event.getRemainingSpot() != -1) {
				newEvent.setRemainingSpot(event.getRemainingSpot());
			}
			newEvent = eventDao.save(newEvent);
				
		}
			return newEvent;
	}
	
}
