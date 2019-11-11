package com.toklahBackend.service;

import com.toklahBackend.model.Complaint;

public interface ComplaintService {

	Complaint createComplain(int userId, Complaint complaint);

}
