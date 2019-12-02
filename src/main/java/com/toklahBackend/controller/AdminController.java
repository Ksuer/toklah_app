package com.toklahBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
import com.toklahBackend.model.Login;
import com.toklahBackend.service.Imp.AdminServiceImp;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminServiceImp adminServiceImp;
	
	public AdminController ( AdminServiceImp adminServiceImp ) {
		this.adminServiceImp = adminServiceImp;
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
		//return new ResponseEntity<>(adminServiceImp.acceptEventRequest(eventId), HttpStatus.OK);
		adminServiceImp.accepteventrequest(eventId, isValid);
		return new ResponseEntity<>("event request accepted", HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{adminId}/editAdmin", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> editaccount(@PathVariable("adminId") int adminId, @RequestBody Admin admin) throws Exception {
		return new ResponseEntity<>(adminServiceImp.editAdmin(adminId, admin), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> changepassword(@RequestBody ChangePassword changePassword) {
		adminServiceImp.changePassword(changePassword.getOldPassword(), changePassword.getNewPassword(),
				changePassword.getAdminId());
		return new ResponseEntity<>("password changed successfully", HttpStatus.OK);
	}

}
