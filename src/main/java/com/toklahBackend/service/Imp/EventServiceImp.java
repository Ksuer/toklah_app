package com.toklahBackend.service.Imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toklahBackend.dao.CompanyDao;
import com.toklahBackend.dao.EventDao;
import com.toklahBackend.model.Company;
import com.toklahBackend.model.Event;
import com.toklahBackend.service.EventService;

import javassist.NotFoundException;

@Component
public class EventServiceImp implements EventService{

	@Autowired
	EventDao eventDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Override
	public Event addEvent(Event event, int companyId) {
		
		Company company = companyDao.findOne(companyId);

		Event newEvent= new Event();
		newEvent = event;
		newEvent.setCompany(company);
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
