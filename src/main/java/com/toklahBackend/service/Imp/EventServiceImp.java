package com.toklahBackend.service.Imp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.EventDao;

import com.toklahBackend.exception.BadRequestException;
import com.toklahBackend.exception.NotFoundException;

import com.toklahBackend.model.Event;
import com.toklahBackend.service.EventService;
import com.toklahBackend.unit.EventTarget;
import com.toklahBackend.unit.EventType;


@Component
public class EventServiceImp implements EventService{

	@Autowired
	EventDao eventDao;

	@Override
	public Event addEvent(Event event, int targetId, int typeId){
		Event newEvent= new Event();
			
		if (event.getEventTitle()==null || /*event.getEventType()==null*/ typeId == 0 ||/* event.getEventTargetGroup()==null*/ targetId == 0||
				event.getLat()==null|| event.getLng()==null || event.getEventDate()==null ||
				event.getEventStartTime()==null || event.getEventEndtTime()==null || event.getEventSummary()==null ||
				event.getCompanyName()==null || event.getCompanyActivityType()==null || event.getCompanyCrNumber()==null ||
				event.getCompanyEmail()==null || event.getContactNumber1()==null/* || event.getContactNumber2()==null*/) {
			throw new BadRequestException("MSG001");
		} else {
			if(event.getEventReward() == 0) {
				event.setIsVolunteering(true);
			}else {
				event.setIsVolunteering(false);
			}
			event.setIsPremium(false);
			event.setIsValid(true); // in production false 
			
			switch(typeId) {
			case 1: event.setEventType(EventType.EVENT); break; 
			case 2: event.setEventType(EventType.ART); break; 
			case 3:	event.setEventType(EventType.MUSIC); break; 
			case 4: event.setEventType(EventType.MUSIC_EVENT); break; 
			case 5: event.setEventType(EventType.EDICATION); break; 
			case 6: event.setEventType(EventType.OTHER); break; 
			default:
			}
			
			switch(targetId) {
			case 1: event.setEventTargetGroup(EventTarget.CHILDREN); break; 
			case 2: event.setEventTargetGroup(EventTarget.FAMELY); break; 
			case 3:	event.setEventTargetGroup(EventTarget.FEMALE); break; 
			case 4: event.setEventTargetGroup(EventTarget.MALE); break; 
			default:
			}
			newEvent = event;
			eventDao.save(newEvent);
			return newEvent;
		}

	}
	
	@Override
	public List<Event> getAllEvent() {
		return (List<Event>) eventDao.findAll();
	}
	
	@Override
	public List<Event> getAllVolunteerEvent() {
		return eventDao.getAllVolunteerEvent();
	}
	
	@Override
	public List<Event> getAllRegEvent() {
		return eventDao.getAllRegEvent();
	}
	
	@Override
	public Event getEvent(int eventId) {
		Event event = eventDao.findOne(eventId);
		if(event != null) {
		return event;
		}else {
			throw new NotFoundException("no event");
		}
	}

	@Override
	public List<Event> search(String word) {
		
		List<Event> event = null;
		event =  eventDao.searchByWord(word);
		return event;
	}
	
}
