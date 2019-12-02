package com.toklahBackend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.model.Event;

public interface EventService {
	Event addEvent(Event event, int targetId, int typeId) ;

	List<Event> getAllEvent();

	Event getEvent(int eventId) ;

	List<Event> search(String word);

	List<Event> getAllVolunteerEvent();

	List<Event> getAllRegEvent();

	Event addImage(MultipartFile file, int eventId);
}
