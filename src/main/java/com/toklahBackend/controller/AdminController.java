package com.toklahBackend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toklahBackend.model.Admin;
import com.toklahBackend.model.ChangePassword;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.Login;
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.service.Imp.AdminServiceImp;
import com.toklahBackend.service.Imp.EventServiceImp;
import com.toklahBackend.service.Imp.UserServiceImp;

import javassist.NotFoundException;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminServiceImp adminServiceImp;
	@Autowired
	private EventServiceImp eventServiceImp;
	@Autowired
	private UserServiceImp userServiceImp;
	
	public AdminController ( AdminServiceImp adminServiceImp, EventServiceImp eventServiceImp, UserServiceImp userServiceImp ) {
		this.adminServiceImp = adminServiceImp;
		this.eventServiceImp = eventServiceImp;
		this.userServiceImp = userServiceImp;
	}

	HttpHeaders responseHeaders = new HttpHeaders();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody Admin admin){
	return new ResponseEntity<>(adminServiceImp.register(admin),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> log(@RequestBody Login login)  {
		return new ResponseEntity<>( adminServiceImp.login(login),HttpStatus.OK);
	}

	@RequestMapping(value = "/getprofile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getadmin(@RequestHeader (name="Authorization") String token) {
		return new ResponseEntity<>(adminServiceImp.getAdminByToken(token), HttpStatus.OK);
		}
	
	@RequestMapping(value = "/{eventId}/acceptEventRequest/{isValid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> accepteventrequest(@PathVariable("eventId") int eventId,@PathVariable("isValid") boolean isValid) {
		return new ResponseEntity<>(adminServiceImp.acceptEventRequest(eventId, isValid), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{eventId}/changeeventtype/{isPremium}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> changeEventType(@PathVariable("eventId") int eventId,@PathVariable("isPremium") boolean isPremium) {
		return new ResponseEntity<>(adminServiceImp.changeEventType(eventId, isPremium), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/{adminId}/editAdmin", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> editaccount(@PathVariable("adminId") int adminId, @RequestBody Admin admin) {
		return new ResponseEntity<>(adminServiceImp.editAdmin(adminId, admin), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> changepassword(@RequestBody ChangePassword changePassword) {
		adminServiceImp.changePassword(changePassword.getOldPassword(), changePassword.getNewPassword(),
				changePassword.getAdminId());
		return new ResponseEntity<>("password changed successfully", HttpStatus.OK);
	}
	@RequestMapping(value = "/forgotpassword",  method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> forgotPassword(@RequestBody SentEmail email) {
		adminServiceImp.emailchangePassword(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getvolunteerevents", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Event>> getAllVolunteerEvent() {
		return new ResponseEntity<>(eventServiceImp.getAllAdminVolunteerEvent(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getregevents", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Event>> getAllRegEvent() {
		return new ResponseEntity<>(eventServiceImp.getAllAdminRegEvent(), HttpStatus.OK);
	}
	@RequestMapping(value = "/getallevents", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Event>> getallEvent() {
		return new ResponseEntity<>(eventServiceImp.getAllEvent(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<>(userServiceImp.getAllUser(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{userId}/getAllTickets", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<Ticket>> getuserTickets(@PathVariable int userId, Pageable pageable){
		
		return new ResponseEntity<Page<Ticket>>(userServiceImp.getticketsByUseryId(userId, pageable), responseHeaders, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{typeId}/{targetId}/{eventId}/editEvent", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> editEvent(@RequestBody Event event, @PathVariable int targetId, @PathVariable int eventId,
			@PathVariable int typeId) {
		return new ResponseEntity<>(adminServiceImp.editEvent(event, eventId, targetId, typeId), responseHeaders, HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/{eventId}/deleteEvent", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> deleteEvent(  @PathVariable int eventId) {
		return new ResponseEntity<>(adminServiceImp.deleteEventRequest(eventId), responseHeaders, HttpStatus.OK);
	
	}

}
