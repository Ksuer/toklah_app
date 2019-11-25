package com.toklahBackend.service;

import com.toklahBackend.model.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

	Transaction create (int userId);

	Page<Transaction> getTransactionByUser(int userId, Pageable pageable);

}
