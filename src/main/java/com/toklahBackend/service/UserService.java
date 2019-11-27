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

	User register(User user);

	User login(Login login);

	User editUser(int userId, User user);

	List<User> getAllUser();

	User getUser(int userId);

	Ticket addTicket(int userId, int eventId);
	
	Page<Ticket> getticketsByUseryId(int userId, Pageable pageable);

	void deleteTicket(int userId, int ticketId);

	void changePassword(String password, int userId);

	//void restorePassword(String oldPassword, String newPassword, int userId) throws NotFoundException, Exception;

	void emailchangePassword(SentEmail email);

	int getOrganizingEvent(int userId);

	int getVolunteeringEvent(int userId);

	void restorePassword(String oldPass, String newPass, int userId);

	User otp(Login login);

	Page<Ticket> getAllTicketsByUseryId(int userId, Pageable pageable);

	//List<Ticket> getAllTickets();

	//List<Ticket> getAllTickets(int userId);

}
