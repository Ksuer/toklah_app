package com.toklahBackend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Integer> {

	Page<Transaction> findTransactionByUserId(@Param("userId") int userId, Pageable pageable);
	
	@Query("SELECT t FROM Transaction t  where t.transactionNumber = :transactionNumber")
	Transaction getTransactionBytransactionNumber(@Param("transactionNumber")String transactionNumber);
	@Query("SELECT t FROM Transaction t")
	List<Transaction> getAll();

}
