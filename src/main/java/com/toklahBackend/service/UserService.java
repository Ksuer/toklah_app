package com.toklahBackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.toklahBackend.model.Login;
import com.toklahBackend.model.SentEmail;
import com.toklahBackend.model.Ticket;
import com.toklahBackend.model.User;

import javassist.NotFoundException;

public interface UserService {

	User register(User user) throws Exception;

	User login(Login login) throws Exception;

	User editUser(int userId, User user) throws Exception;

	List<User> getAllUser();

	User getUser(int userId) throws NotFoundException;

	Ticket addTicket(int userId, int eventId) throws Exception;
	
	Page<Ticket> getticketsByUseryId(int userId, Pageable pageable) throws NotFoundException;

	void deleteTicket(int userId, int ticketId);

	void changePassword(String password, int userId) throws NotFoundException;

	//void restorePassword(String oldPassword, String newPassword, int userId) throws NotFoundException, Exception;

	void emailchangePassword(SentEmail email) throws NotFoundException, Exception;

	int getOrganizingEvent(int userId);

	int getVolunteeringEvent(int userId);

	//List<Ticket> getAllTickets();

	//List<Ticket> getAllTickets(int userId);

}
