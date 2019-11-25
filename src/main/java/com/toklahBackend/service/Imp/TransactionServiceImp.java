package com.toklahBackend.service.Imp;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.TransactionDao;
import com.toklahBackend.dao.TicketDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.model.Transaction;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;
import com.toklahBackend.service.TransactionService;

@Component
public class TransactionServiceImp implements TransactionService {

	
	@Autowired
	UserDao userDao;
	@Autowired
	TransactionDao transactionDao;
	
	@Override
	public Transaction create (int userId) {
		
		User user = userDao.findOne(userId); 
		Transaction transaction = new Transaction();
		
		//String invoiceNumber = RandomStringUtils.random(8,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		//transaction.setInvoiceNumber(invoiceNumber);
		transaction.setUser(user);
		transaction = transactionDao.save(transaction);
		return transaction;
		
	}

	@Override
	public Page<Transaction> getTransactionByUser(int userId, Pageable pageable) {

		Page<Transaction> transactions = transactionDao.findTransactionByUserId(userId,pageable);
		return transactions;
	}
	
}
