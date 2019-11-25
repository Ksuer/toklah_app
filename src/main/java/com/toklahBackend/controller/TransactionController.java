package com.toklahBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.toklahBackend.model.Transaction;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.service.Imp.TransactionServiceImp;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionServiceImp transactionServiceImp;

	HttpHeaders responseHeaders = new HttpHeaders();
	
	@RequestMapping(value = "/{userId}/create", method = RequestMethod.POST)
	public ResponseEntity<?> createTransaction(@PathVariable int userId ,@RequestBody Transaction transaction ) {
		return new ResponseEntity<>(transactionServiceImp.create(userId, transaction), responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransByUser/{userId}",method = RequestMethod.GET)
	public ResponseEntity<?> getTransByUser(@PathVariable int userId,Pageable pageable) {
		return new ResponseEntity<>(transactionServiceImp.getTransactionByUser(userId,pageable), responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getall",method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(transactionServiceImp.getAll(), responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTransBytransactionNumber/{transactionNumber}",method = RequestMethod.GET)
	public ResponseEntity<?> getTransByUser(@PathVariable String transactionNumber) {
		return new ResponseEntity<>(transactionServiceImp.getTransactionBytransactionNumber(transactionNumber), responseHeaders, HttpStatus.OK);
	}
}
