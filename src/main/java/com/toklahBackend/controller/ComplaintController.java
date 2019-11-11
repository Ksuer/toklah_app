package com.toklahBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toklahBackend.model.Complaint;
import com.toklahBackend.service.Imp.ComplaintServiceImp;

@CrossOrigin
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
	
	@Autowired
	private ComplaintServiceImp complaintServiceImp;

	public ComplaintController ( ComplaintServiceImp complaintServiceImp ) {
		this.complaintServiceImp = complaintServiceImp;
	}
	
	HttpHeaders responseHeaders = new HttpHeaders();

	@RequestMapping(value = "/{userId}/createComplain", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createComplain(@RequestBody Complaint complaint, @PathVariable int userId) throws Exception {
		if (complaint != null || userId != 0) {
			Complaint newComplaint = complaintServiceImp.createComplain(userId, complaint);
			return new ResponseEntity<>(newComplaint, responseHeaders, HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
