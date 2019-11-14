package com.toklahBackend.service.Imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.model.Event;
import com.toklahBackend.service.EventService;
import com.toklahBackend.unit.EventTarget;
import com.toklahBackend.unit.EventType;

import javassist.NotFoundException;

@Component
public class EventServiceImp implements EventService{

	@Autowired
	EventDao eventDao;
	@Override
	public Event addEvent(Event event, int targetId, int typeId) {
		Event newEvent= new Event();
		switch(typeId) {
		case 1: newEvent.setEventType(EventType.EVENT);
		case 2: newEvent.setEventType(EventType.ART);
		case 3:	newEvent.setEventType(EventType.MUSIC);
		case 4: newEvent.setEventType(EventType.MUSIC_EVENT);
		case 5: newEvent.setEventType(EventType.EDICATION);
		case 6: newEvent.setEventType(EventType.OTHER);
		default:
		}
		
		switch(targetId) {
		case 1: newEvent.setEventTargetGroup(EventTarget.CHILDREN);
		case 2: newEvent.setEventTargetGroup(EventTarget.FMAILY);
		case 3:	newEvent.setEventTargetGroup(EventTarget.FEMALE);
		case 4: newEvent.setEventTargetGroup(EventTarget.MALE);
		default:
		}
		
		newEvent = event;
		eventDao.save(newEvent);
		return newEvent;
	}
	
	@Override
	public List<Event> getAllEvent() {
		return (List<Event>) eventDao.findAll();
	}

	@Override
	public Event getEvent(int eventId) throws NotFoundException {
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
		
		//Trying to implement search
		
	    /*List<Event> events=(List<Event>) eventDao.findAll() ;

		List<Event> searchedEvents = new ArrayList<Event>();
        for(Event e: events) {
            if(e.getEventTitle().toLowerCase().contains(word.toLowerCase())) {
            	searchedEvents.add(e);
            }
        }

        return searchedEvents;*/
	}
	
}
