package com.toklahBackend.service.Imp;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.TicketDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.dao.UserImageDao;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.Login;
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.sendEmail.SendEmail;
import com.toklahBackend.service.UserService;

import javassist.NotFoundException;

@Component
public class UserServiceImp implements UserService{

	@Autowired
	UserDao userDao;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	TicketDao ticketDao;
	
	@Autowired
	UserImageDao userImageDao;
	
	@Autowired
    SendEmail serviceSendEmail;
	
	@Override
	public User register(User user) throws Exception {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(user.getPassword());
		
		//checking empty fields
		if (user.getEmail() == null || user.getPassword() == null) {
			throw new Exception("enter the required fields");
		}
		//checking if the user register before
		if (userDao.findByEmail(user.getEmail()) != null || userDao.mobileOremail(user.getMobileNumber()) != null) {
			throw new Exception("this user already registered");
		}
		
		user.setPassword(hashedPass);
		userDao.save(user);
		return user;
	}

	@Override
	public User login(Login login) throws Exception {
	
		if (login.getMobileOrEmail().isEmpty()) {
			throw new Exception("Missing email or mobile #");
		}
		if (login.getPassword().isEmpty()) {
			throw new Exception("Missing password");
		}else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User user = userDao.mobileOremail(login.getMobileOrEmail());
			if (user == null) {
				throw new Exception("User not found");
			} else {
				if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
					return user;
					
				} else {
					throw new Exception("Password not match");
					}
				}
			}
	}
	
	@Override
	public User editUser(int userId, User user) throws Exception {
				
		User newUser= userDao.findOne(userId);
		
		if (user.getFirstName() != null) {
			newUser.setFirstName(user.getFirstName());
		}
		
		if (user.getFatherName() != null) {
			newUser.setFatherName(user.getFirstName());
		}
		
		if (user.getGrandFatherName() != null) {
			newUser.setGrandFatherName(user.getFirstName());
		}
		
		if (user.getLastName() != null) {
			newUser.setLastName(user.getLastName());
		}
		
		if (user.getLastName() != null) {
			newUser.setLastName(user.getLastName());
		}
		
		if (user.getEmail() != null) {
			User checkEmail = userDao.findByEmail(user.getEmail());
			
			if(checkEmail == null) {
					newUser.setEmail(user.getEmail());
			}else {
				throw new Exception("Email already exist"); 
			}
			
		}
		
		if (user.getMobileNumber() != null) {
			User checkMobile = userDao.findByMobileNumber(user.getMobileNumber());
			
			if(checkMobile == null) {
				newUser.setMobileNumber(user.getMobileNumber());
			}else {
				throw new Exception("another user with this mobile number"); 
			}
		newUser.setMobileNumber(user.getMobileNumber());
		}
	
		if (user.getCountryKey() != null) {
			newUser.setCountryKey(user.getCountryKey());
		}
		
		if (user.getBirthDate() != null) {
			newUser.setBirthDate(user.getBirthDate());
		}
		
		if (user.getGender() != null) {
			newUser.setGender(user.getGender());
		}
		
		if (user.getOccupation() != null) {
			newUser.setOccupation(user.getOccupation());
		}
		
		if (user.getSpecialization() != null) {
			newUser.setSpecialization(user.getSpecialization());
		}
		
		if (user.getEducationalLevel() != null) {
			newUser.setEducationalLevel(user.getEducationalLevel());
		}
		
		if (user.getT_shirtSize() != null) {
			newUser.setT_shirtSize(user.getT_shirtSize());
		}
		
		if (user.getIbanNumber() != null) {
			newUser.setIbanNumber(user.getIbanNumber());
		}
		
		if (user.getLanguage() != null) {
			newUser.setLanguage(user.getLanguage());
		}
		
		if (user.getAboutMe() != null) {
			newUser.setAboutMe(user.getAboutMe());
		}
		
		
		userDao.save(newUser);
		return newUser;
	}

	@Override
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}
	
	@Override
	public User getUser(int userId) throws NotFoundException {
		
		User user = userDao.findOne(userId);
		
		if (user == null) {
			throw new NotFoundException(null);
		}
				
		return user;
	}

	@Override
	public Ticket addTicket(int userId, int eventId) throws Exception {

		User user = userDao.findOne(userId);
		Event event = eventDao.findOne(eventId);
		Ticket myTicket= new Ticket(event.getEventId(), event.getEventTitle(), event.getEventDate(), event.getEventStartTime(), event.getEventEndtTime(), user.getMobileNumber(), event.getEventReward());
		List<Ticket> userTickets= ticketDao.getTicketbyUserAndEvent(userId, eventId);
		if (userTickets.size() > 0)
		{
			throw new Exception("You have ticket for this event");
		} 
		if (event.getIsVolunteering() == false)
		{
			user.setOrganizingEventNumber(user.getOrganizingEventNumber()+1);
		}
		
		if (event.getIsVolunteering() == true)
		{
			user.setVolunteeringEventNumber(user.getVolunteeringEventNumber()+1);
		}
		
		myTicket.setUser(user);
		ticketDao.save(myTicket);
		return myTicket;
	}

	@Override
	public Page<Ticket> getticketsByUseryId(int userId, Pageable pageable) throws NotFoundException {
		Page<Ticket> ticket = ticketDao.getTicketbyUserId(userId, pageable);
		if(ticket != null) {
		return ticket;
		}else {
			throw new NotFoundException("no ticket");
		}
	}

	@Override
	public void deleteTicket(int userId, int ticketId) {
		Ticket ticket= ticketDao.findOne(ticketId);
		ticket.setIsCanceled(true);  
		User user = userDao.findOne(userId);
		Event event = eventDao.findOne(ticket.getEventId());
		
		if (event.getIsVolunteering() == false)
		{
			user.setOrganizingEventNumber(user.getOrganizingEventNumber()-1);
		}
		
		if (event.getIsVolunteering() == true)
		{
			user.setVolunteeringEventNumber(user.getVolunteeringEventNumber()-1);
		}
		
		userDao.save(user);
		ticketDao.save(ticket); 
		
	}

	@Override
	public void changePassword(String password, int userId) throws NotFoundException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user = userDao.findOne(userId);
		if (user == null) {
			throw new NotFoundException("user not found");
		} else {
			user.setPassword(passwordEncoder.encode(password));
			userDao.save(user);
		}

	}

	@Override
	public void emailchangePassword(SentEmail email) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user = userDao.findByEmail(email.getEmail());
		if (user == null) {
			throw new NotFoundException("No one with his email");
		} else {
			String password = RandomStringUtils.random(8,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
			String text = "Your new password is " + password ;
			try {
			String title = "Forgot your password? ";
			serviceSendEmail.sendMail(email.getEmail(),text, title);
			user.setPassword(passwordEncoder.encode(password));
			userDao.save(user);
			}catch(Exception e) {
				throw new Exception("Error while sending the password" + e.getMessage());
			}
		}

	}
	
	@Override
	public int getOrganizingEvent(int userId) {

		User user = userDao.findOne(userId);
		return user.getOrganizingEventNumber();
	}

	@Override
	public int getVolunteeringEvent(int userId) {

		User user = userDao.findOne(userId);
		return user.getVolunteeringEventNumber();
	}

}
