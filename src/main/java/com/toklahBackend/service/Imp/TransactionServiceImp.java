package com.toklahBackend.service.Imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.toklahBackend.exception.NotFoundException;
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
	public Transaction create (int userId, Transaction transaction) {
		User user = userDao.findOne(userId); 
		Calendar today = Calendar.getInstance();
		String currentTime;
		String currentDate;
		Transaction newTransaction = new Transaction();
		newTransaction = transaction;
		 
		String myFormat = "yyyy-MM-dd";
		String timeFormat = "hh:mm a";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		SimpleDateFormat tsdf = new SimpleDateFormat(timeFormat);
		currentDate = sdf.format(today.getTime());
		currentTime = tsdf.format(today.getTime());
		
		newTransaction.setTransactionDate(currentDate);
		newTransaction.setTransactionTime(currentTime);
		newTransaction.setUserId(user.getUserId());
		transactionDao.save(newTransaction);
		
		//update last payment date and change the is paid to true; 
		try {
			user.setLastPayment(addTime(user.getLastPayment()));
			user.setExpirePayment(addTime(user.getLastPayment()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setIsPaid(true);
		user.setIsPremium(transaction.getIsPremium());
		userDao.save(user);
		return newTransaction;
	}

	@Override
	public Page<Transaction> getTransactionByUser(int userId, Pageable pageable) {
		Page<Transaction> transaction = transactionDao.findTransactionByUserId(userId,pageable);
		return transaction;
	}

	@Override
	public Transaction getTransactionBytransactionNumber(String transactionNumber) {
		Transaction transcation = transactionDao.getTransactionBytransactionNumber(transactionNumber);
		if(transcation == null) {
			throw new NotFoundException("kk");
		}
		return transcation;
	}

	@Override
	public List<Transaction> getAll() {
		List<Transaction> transactions = transactionDao.getAll();	
		return transactions;
	}
	
	
	public String addTime (String date) throws ParseException {
	    String myFormat = "yyyy-MM-dd";
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
	    String newDate = "2000-01-01";
	    if (date.isEmpty()) {
	        date = "2000-01-01";  // think of something better when no date
	    }
	    Date lastPayment = sdf.parse(date);

	    Calendar todayPlus30 = Calendar.getInstance();
	    todayPlus30.setTime(lastPayment);
	    todayPlus30.add(Calendar.DAY_OF_YEAR, 30);
	    todayPlus30.set(Calendar.HOUR_OF_DAY, 0);
	    todayPlus30.set(Calendar.MINUTE, 0);
	    todayPlus30.set(Calendar.SECOND, 0);
	    todayPlus30.set(Calendar.MILLISECOND, 0);

	    // the account not expire we add 1 month above the 30 day he has from the expire date
	    if (todayPlus30.after(Calendar.getInstance()) ) {
	        Calendar extra30Day = Calendar.getInstance();
	        extra30Day.setTime(lastPayment);
	        extra30Day.add(Calendar.MONTH, 1);
	        extra30Day.set(Calendar.HOUR_OF_DAY, 0);
	        extra30Day.set(Calendar.MINUTE, 0);
	        extra30Day.set(Calendar.SECOND, 0);
	        extra30Day.set(Calendar.MILLISECOND, 0);
			newDate = sdf.format(extra30Day.getTime());
	    }else { // the account expire we add 1 month to ftom todday
	        Calendar extra30Day = Calendar.getInstance();
	        extra30Day.add(Calendar.MONTH, 1);
	        extra30Day.set(Calendar.HOUR_OF_DAY, 0);
	        extra30Day.set(Calendar.MINUTE, 0);
	        extra30Day.set(Calendar.SECOND, 0);
	        extra30Day.set(Calendar.MILLISECOND, 0);
			newDate = sdf.format(extra30Day.getTime());
	    }

	    System.out.println("addTime = " + newDate);

	    return newDate;
	}

}
