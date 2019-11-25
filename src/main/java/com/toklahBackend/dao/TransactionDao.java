package com.toklahBackend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.toklahBackend.model.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Integer> {

	Page<Transaction> findTransactionByUserId(int userId, Pageable pageable);

}
