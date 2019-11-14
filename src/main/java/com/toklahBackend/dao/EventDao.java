package com.toklahBackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.toklahBackend.model.Event;

public interface EventDao extends CrudRepository <Event, Integer>{

    //@Query("SELECT e FROM Event e where e.eventTitle Like ('%',:word,'%')")
	//List<Event> searchByWord(@Param("word") String word);

	@Query("SELECT e FROM Event e where e.eventTitle Like CONCAT('%',:word,'%')")
	List<Event> searchByWord(@Param("word") String word);
	
	@Query("SELECT e FROM Event e  where e.isVolunteering = true")
	List<Event> getAllVolunteerEvent();
	
	@Query("SELECT e FROM Event e where e.isVolunteering = false")
	List<Event> getAllRegEvent();
}
