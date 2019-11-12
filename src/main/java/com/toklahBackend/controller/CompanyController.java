//package com.toklahBackend.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.toklahBackend.model.Company;
//import com.toklahBackend.service.Imp.CompanyServiceImp;
//
//@ControllerAdvice
//@CrossOrigin
//@RestController
//@RequestMapping("/company")
//public class CompanyController {
//
//	@Autowired
//	private CompanyServiceImp companyServiceImp;
//	
//	@RequestMapping(value = "/eventRequest", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> addRequest(@RequestBody Company company) {	
//		return new ResponseEntity<>(companyServiceImp.add(company), HttpStatus.OK);
//
//	}
//	
//}
