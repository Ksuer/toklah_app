package com.toklahBackend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.model.Event;

public interface EventService {
	Event addEvent(Event event, int targetId, int typeId) ;

	List<Event> getAllEvent();

	Event getEvent(int eventId) ;

	List<Event> search(String word, String token);

	List<Event> getAllVolunteerEvent(String token);

	List<Event> getAllRegEvent(String token);

	Event addImage(MultipartFile file, int eventId);

	List<Event> getAllAdminRegEvent();

	List<Event> getAllAdminVolunteerEvent();
}
