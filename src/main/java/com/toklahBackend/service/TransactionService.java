package com.toklahBackend.service;

import com.toklahBackend.model.Transaction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

	Transaction create (int userId, Transaction transaction);

	Page<Transaction> getTransactionByUser(int userId, Pageable pageable);
	
	Transaction getTransactionBytransactionNumber(String transactionNumber);
	
	List<Transaction> getAll();

}
