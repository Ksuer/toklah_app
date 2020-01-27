package com.toklahBackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.Event;

public interface EventDao extends CrudRepository <Event, Integer>{

	@Query("SELECT e FROM Event e where e.eventTitle Like CONCAT('%',:word,'%') and e.isValid = true")
	List<Event> searchByPremiumUser(@Param("word") String word);
	
	@Query("SELECT e FROM Event e where  e.isPremium = false and e.isValid = true and e.eventTitle Like CONCAT('%',:word,'%')")
	List<Event> searchByBasicUser(@Param("word") String word);
	
	@Query("SELECT e FROM Event e  where e.isVolunteering = true")
	List<Event> getAllVolunteerEvent();
	
	@Query("SELECT e FROM Event e where e.isVolunteering = false")
	List<Event> getAllRegEvent();
	
	@Query("SELECT e FROM Event e  where e.isVolunteering = true and e.isPremium = false and e.isValid = true AND e.eventDate >= CURDATE()" )
	List<Event> getAllBasicVolunteerEvent();
	
	@Query("SELECT e FROM Event e where e.isVolunteering = false and e.isPremium = false and e.isValid = true AND e.eventDate >= CURDATE()")
	List<Event> getAllBasicRegEvent();
	
	@Query("SELECT e FROM Event e  where e.isVolunteering = true and e.isValid = true AND e.eventDate >= CURDATE()")
	List<Event> P_getAllBasicVolunteerEvent();
	
	@Query("SELECT e FROM Event e where e.isVolunteering = false and e.isValid = true AND e.eventDate >= CURDATE()")
	List<Event> P_getAllBasicRegEvent();
	
}
