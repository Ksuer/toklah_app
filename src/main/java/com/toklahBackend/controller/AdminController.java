package com.toklahBackend.controller;

import javax.ws.rs.HeaderParam;

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
		Admin admin= adminServiceImp.login(login);
		if(admin == null) {
			return new ResponseEntity<>("admin not found", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(admin,HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAdmin(@RequestHeader("Authorization") String token){
	return new ResponseEntity<>(adminServiceImp.getAdminByToken(token),HttpStatus.OK);
	}
}
