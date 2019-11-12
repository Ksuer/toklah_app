package com.toklahBackend.service;

import java.util.List;

import com.toklahBackend.model.Event;

import javassist.NotFoundException;

public interface EventService {

	Event addEvent(Event event, int targetId, int typeId);

	List<Event> getAllEvent();

	Event getEvent(int eventId) throws NotFoundException;

	List<Event> search(String word);

	List<Event> getAllVolunteerEvent();

	List<Event> getAllRegEvent();

}
