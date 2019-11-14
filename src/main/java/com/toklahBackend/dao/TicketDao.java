package com.toklahBackend.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.Ticket;

public interface TicketDao extends CrudRepository <Ticket, Integer> {

	@Query("SELECT t FROM Ticket t join t.user u  where u.userId= :userId")
	Page<Ticket> getTicketbyUserId(@Param("userId") int userId, Pageable pageable);


	@Query("SELECT t FROM Ticket  where t.user= :userId and t.eventId= :eventId")
	List<Ticket> getTicketbyUserAndEvent(int userId, int eventId);

}
