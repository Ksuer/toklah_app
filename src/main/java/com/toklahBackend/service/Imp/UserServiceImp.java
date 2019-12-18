package com.toklahBackend.service.Imp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.TicketDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.dao.UserImageDao;
import com.toklahBackend.exception.BadRequestException;
import com.toklahBackend.exception.ConflictException;
import com.toklahBackend.exception.LockedException;
import com.toklahBackend.exception.NotFoundException;
import com.toklahBackend.exception.UnAuthorizedException;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.Login;
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.model.UserImage;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.sendEmail.SendEmail;
import com.toklahBackend.service.AmazonClient;
import com.toklahBackend.service.UserService;

@Component
public class UserServiceImp implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	EventDao eventDao;

	@Autowired
	TicketDao ticketDao;

	@Autowired
	UserImageDao userImageDao;

	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	SendEmail serviceSendEmail;

	@Value("Authorization")
	private String tokenHeader;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Override
	public User register(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPass = passwordEncoder.encode(user.getPassword());

		// checking empty fields
		if (user.getFirstName() == null || user.getFatherName() == null || user.getGrandFatherName() == null
				|| user.getLastName() == null || user.getEmail() == null || user.getPassword() == null
				|| user.getCountryKey() == null || user.getMobileNumber() == null || user.getBirthDate() == null
				|| user.getGender() == null || user.getOccupation() == null || user.getSpecialization() == null
				|| user.getEducationalLevel() == null || user.getT_shirtSize() == null || user.getIbanNumber() == null
				|| user.getLanguage() == null || user.getAboutMe() == null) {
			throw new BadRequestException("MSG001");
		}

		// checking if the user register before
		if (userDao.findByEmail(user.getEmail()) != null || userDao.mobileOremail(user.getMobileNumber()) != null) {
			throw new ConflictException("MSG003");
		}
		
		if (userDao.findByFirstName(user.getFirstName()) != null & userDao.findByFatherName(user.getFatherName()) != null & userDao.findByGrandFatherName(user.getGrandFatherName()) != null & userDao.findByLastName(user.getLastName()) != null) {
			throw new ConflictException("MSG012");
		}
		
		//set lastpayment to today for the free trail  
		Calendar cal = Calendar.getInstance();
		String myFormat = "yyyy-MM-dd" ;
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		user.setLastPayment(sdf.format(cal.getTime()));
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		user.setExpirePayment(sdf.format(cal.getTime()));
		user.setIsLock(true);
		user.setIsPaid(true);
		user.setPassword(hashedPass);
		user.setIsPremium(false);
		userDao.save(user);
		return user;
	}

	@Override
	public User login(Login login) {
		if (login.getMobileOrEmail() == null) {
			throw new BadRequestException("MSG001");
		}
		if (login.getPassword() == null) {
			throw new BadRequestException("MSG001");
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User user = userDao.mobileOremail(login.getMobileOrEmail());
			if (user == null) {
				throw new NotFoundException("MSG013");
			} else {
				final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getMobileOrEmail());
				final String token = jwtTokenUtil.generateToken(userDetails);
				
				if (user.getIsLock()) {
					throw new LockedException("MSG004");
				}
				if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {

					user.setToken(token);
					return user;
				} else {
					throw new UnAuthorizedException("MSG002");
				}
			}
		}
	}
	
	
	@Override
	public User otp(Login login) {
		if (login.getMobileOrEmail().isEmpty()) {
			throw new BadRequestException("MSG001");
		} else {
			User user = userDao.mobileOremail(login.getMobileOrEmail());
			if (user == null) {
				throw new NotFoundException("MSG013");
			}
			if(user.getIsLock() == false) {
				throw new ConflictException("MSG003");
			}else {
				final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getMobileOrEmail());
				final String token = jwtTokenUtil.generateToken(userDetails);
		
				user.setIsLock(false);
				userDao.save(user);
				user.setToken(token);
				
				return user;
			}
		}
	}

	@Override
	public User editUser(int userId, User user) {
		User newUser = userDao.findOne(userId);

		if (user.getFirstName() != null) {
			newUser.setFirstName(user.getFirstName());
		}

		if (user.getFatherName() != null) {
			newUser.setFatherName(user.getFatherName());
		}

		if (user.getGrandFatherName() != null) {
			newUser.setGrandFatherName(user.getGrandFatherName());
		}

		if (user.getLastName() != null) {
			newUser.setLastName(user.getLastName());
		}

		if (user.getEmail() != null) {

			if (!newUser.getEmail().trim().equalsIgnoreCase(user.getEmail().trim())) {
				User checkEmail = userDao.findByEmail(user.getEmail());
				if (checkEmail == null) {
					newUser.setEmail(user.getEmail());
				} else {
					throw new UnAuthorizedException("MSG003");
				}
			}
		}

		if (user.getMobileNumber() != null) {
			if (!newUser.getMobileNumber().trim().equalsIgnoreCase(user.getMobileNumber().trim())) {
				User checkMobile = userDao.findByMobileNumber(user.getMobileNumber());
				if (checkMobile == null) {
					newUser.setMobileNumber(user.getMobileNumber());
				} else {
					throw new UnAuthorizedException("MSG017");
				}
			}
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
		
		if (user.getSkill() != null) {
			newUser.setSkill(user.getSkill());
		}

		if (user.getAboutMe() != null) {
			newUser.setAboutMe(user.getAboutMe());
		}

		userDao.save(newUser);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		newUser.setToken(token);
		return newUser;
	}

	@Override
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public User getUser(int userId) {

		User user = userDao.findOne(userId);

		if (user == null) {
			throw new NotFoundException();
		}
		try {
			//check if the date is older than 30 days and the payment status is true -> it will change the status to false
			 if (!isValid(user.getLastPayment()) && user.getIsPaid()) {
				 user.setIsPaid(false);
				 userDao.save(user);
			 }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Ticket addTicket(int userId, int eventId) {
		User user = userDao.findOne(userId);
		Event event = eventDao.findOne(eventId);
		int countTicket = ticketDao.countUsedTicket(eventId);
		//check if the last payment is less than 30 days
		try {
			 if (!isValid(user.getLastPayment())) {
				 throw new LockedException("MSG015");
			 }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Ticket myTicket = new Ticket(event.getEventId(), event.getEventTitle(), event.getEventDate(),
				event.getEventStartTime(), event.getEventEndtTime(), user.getMobileNumber(), event.getEventReward());
		List<Ticket> userTickets = ticketDao.getValidTicketbyUserAndEvent(userId, eventId);
		
		if (userTickets.size() > 0) {
			throw new ConflictException("MSG016");
		}

		// check if the the tickets are full
		if (countTicket >= event.getEventOrganizerNumber()) {
			throw new ConflictException("MSG008");
		}
		Ticket canceledTicket = ticketDao.getTicketbyUserAndEvent(userId, eventId);
		
		if (canceledTicket != null ) {
			myTicket = canceledTicket;
		}
		
		

		if (event.getIsVolunteering() == false) {
			user.setOrganizingEventNumber(user.getOrganizingEventNumber() + 1);
		}

		if (event.getIsVolunteering() == true) {
			user.setVolunteeringEventNumber(user.getVolunteeringEventNumber() + 1);
		}

		myTicket.setUser(user);
		myTicket.setIsCanceled(false);
		
		event.setRemainingSpot(event.getRemainingSpot() - 1);
		
		eventDao.save(event);
		ticketDao.save(myTicket);
		return myTicket;
	}
	
	@Override
	public Page<Ticket> getAllTicketsByUseryId(int userId, Pageable pageable) {
		Page<Ticket> ticket = ticketDao.getTicketbyUserId(userId, pageable);
		if (ticket != null) {
			return ticket;
		} else {
			throw new NotFoundException("no ticket");
		}
	}
	
	@Override
	public Page<Ticket> getticketsByUseryId(int userId, Pageable pageable) {
		Page<Ticket> ticket = ticketDao.getValidTicketbyUserId(userId, pageable);
		if (ticket != null) {
			return ticket;
		} else {
			throw new NotFoundException("no ticket");
		}
	}

	@Override
	public void deleteTicket(int userId, int ticketId) {
		Ticket ticket = ticketDao.findOne(ticketId);

		ticket.setIsCanceled(true);
		User user = userDao.findOne(userId);
		Event event = eventDao.findOne(ticket.getEventId());
		
		Calendar kk = Calendar.getInstance();
		kk.setTime(event.getEventDate());
		if (kk.before(Calendar.getInstance())) {
			throw new ConflictException("MSG010");
		}

		if (event.getIsVolunteering() == false) {
			user.setOrganizingEventNumber(user.getOrganizingEventNumber() - 1);
		}

		if (event.getIsVolunteering() == true) {
			user.setVolunteeringEventNumber(user.getVolunteeringEventNumber() - 1);
		}
		event.setRemainingSpot(event.getRemainingSpot() + 1);
		
		eventDao.save(event);
		userDao.save(user);
		ticketDao.save(ticket);

	}

	@Override
	public void changePassword(String password, int userId) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user = userDao.findOne(userId);
		if (user == null) {
			throw new NotFoundException();
		} else {
			user.setPassword(passwordEncoder.encode(password));
			userDao.save(user);
		}

	}

	@Override
	public void restorePassword(String oldPass, String newPass, int userId) {
		
		if (oldPass == null || newPass == null) {
			throw new BadRequestException("MSG001");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user = userDao.findOne(userId);
		if (user == null) {
			throw new NotFoundException();
		} else {
			String dbPass = user.getPassword();
			if (passwordEncoder.matches(oldPass, dbPass)) {
				user.setPassword(passwordEncoder.encode(newPass));
				userDao.save(user);
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
		
		User user = new User();
		user = userDao.findByEmail(email.getEmail());
		if (user == null) {
			throw new NotFoundException("MSG006");
		} else {
			String password = RandomStringUtils.random(8,
					"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
			String text = "Your new password is " + password;
			try {
				String title = "Forgot your password? ";
				serviceSendEmail.sendMail(email.getEmail(), text, title);
				user.setPassword(passwordEncoder.encode(password));
				userDao.save(user);
			} catch (Exception e) {
				throw new BadRequestException("MSG014");
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
	
	@Override
	public User Addimage(MultipartFile file, int userId) {
		User user= userDao.findOne(userId);
		if ( user != null) {
		String url = "";
		try {
			url = amazonClient.uploadFileUser(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserImage userImage = new UserImage(file.getOriginalFilename() ,  url , user);
		userImageDao.save(userImage);
		user.setUserImage(url);
		userDao.save(user);
		}else {
			throw new NotFoundException("MSG013");
		}
		return user;
	}
	
	@Override
	public Boolean renewPackage(int userId) {
		User user = userDao.findOne(userId);
		if (user == null) {
			throw new NotFoundException();
		}else {
			try {
				//if there time expire it will true
				return !isValid(user.getExpirePayment());
			} catch (ParseException e) {
				throw new NotFoundException("time error");
			}
		}
	}
	
	public Boolean isValid(String date) throws ParseException {
		Boolean isV = false;
		String myFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		if (date.isEmpty()) {
			date = "2000-01-01";  // think of something better when no date 
		}
		Date lastPayment = sdf.parse(date);
		
		Calendar todayPlus30 = Calendar.getInstance();
		todayPlus30.setTime(lastPayment);
		todayPlus30.add(Calendar.MONTH, 1);
		todayPlus30.set(Calendar.HOUR_OF_DAY, 0);
		todayPlus30.set(Calendar.MINUTE, 0);
		todayPlus30.set(Calendar.SECOND, 0);
		todayPlus30.set(Calendar.MILLISECOND, 0);
		
		if (todayPlus30.after(Calendar.getInstance()) ) {
			isV = true;
		}
		System.out.println("isV = " + isV);
		return isV;
	}
	
}
