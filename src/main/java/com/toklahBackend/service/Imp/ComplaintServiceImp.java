package com.toklahBackend.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.ComplaintDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.model.Complaint;
import com.toklahBackend.model.User;
import com.toklahBackend.service.ComplaintService;

@Component
public class ComplaintServiceImp implements ComplaintService{

	@Autowired
	UserDao userDao;
	@Autowired
	ComplaintDao complaintDao;

	@Override
	public Complaint createComplain(int userId, Complaint complaint) {
		User user = new User();
		Complaint newComplaint = new Complaint();
		user = userDao.findOne(userId);
		newComplaint = complaint;
		newComplaint.setUser(user);
		newComplaint = complaintDao.save(newComplaint);
		return newComplaint;
		
	}

}
