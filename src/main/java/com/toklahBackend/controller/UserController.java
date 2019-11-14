package com.toklahBackend.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toklahBackend.model.Login;
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.service.Imp.UserServiceImp;

import javassist.NotFoundException;

@ControllerAdvice
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImp userServiceImp;

	public UserController(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}
	
	HttpHeaders responseHeaders = new HttpHeaders();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody User user) throws Exception {
		return new ResponseEntity<>(userServiceImp.register(user), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> log(@RequestBody Login login) throws Exception {
		User user = userServiceImp.login(login);
		if (user == null) {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<>(userServiceImp.getAllUser(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getuser/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getaccount(@PathVariable int userId) throws NotFoundException {
		User user = userServiceImp.getUser(userId);
		if (user == null) {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userServiceImp.getUser(userId), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/{userId}/editUser", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> editaccount(@PathVariable int userId, @RequestBody  User user) throws Exception {
		return new ResponseEntity<>(userServiceImp.editUser(userId, user), HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/changepassword", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> changepassword(@PathVariable int userId, @RequestBody String password) throws NotFoundException {
		userServiceImp.changePassword(password, userId);
		return new ResponseEntity<>("password changed", HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword",  method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> forgotPassword(@RequestBody SentEmail email) throws Exception{
		userServiceImp.emailchangePassword(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/{eventId}/registertoEvent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Ticket> addTicket(@PathVariable int userId, @PathVariable int eventId) throws Exception {	
		Ticket myticket = userServiceImp.addTicket(userId,eventId);
		return new ResponseEntity<Ticket>(myticket, responseHeaders, HttpStatus.CREATED);
	
	}

	@RequestMapping(value = "/{userId}/getAllTickets", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<Ticket>> getuserTickets(@PathVariable int userId, Pageable pageable) throws NotFoundException {
		
		return new ResponseEntity<Page<Ticket>>(userServiceImp.getticketsByUseryId(userId, pageable), responseHeaders, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{userId}/getOrganizingEventNumber", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getOrganizingEvent(@PathVariable int userId) {
		
		return new ResponseEntity<>(userServiceImp.getOrganizingEvent(userId), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{userId}/getVolunteeringEventNumber", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getVolunteeringEvent(@PathVariable int userId) {
		
		return new ResponseEntity<>(userServiceImp.getVolunteeringEvent(userId), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{userId}/{ticketId}/deleteTicket", method = RequestMethod.DELETE)
	@Transactional
	public void Ticket(@PathVariable int userId, @PathVariable int ticketId) {	
		userServiceImp.deleteTicket(userId,ticketId);
	
	}
	
}
