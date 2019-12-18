package com.toklahBackend.service.Imp;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.toklahBackend.dao.EventDao;
import com.toklahBackend.dao.EventImageDao;
import com.toklahBackend.dao.UserDao;
import com.toklahBackend.exception.BadRequestException;
import com.toklahBackend.exception.NotFoundException;
import com.toklahBackend.model.Admin;
import com.toklahBackend.model.Event;
import com.toklahBackend.model.EventImage;
import com.toklahBackend.model.User;
import com.toklahBackend.security.JwtTokenUtil;
import com.toklahBackend.service.AmazonClient;
import com.toklahBackend.service.EventService;
import com.toklahBackend.unit.EventTarget;
import com.toklahBackend.unit.EventType;


@Component
public class EventServiceImp implements EventService{

	@Autowired
	private EventDao eventDao;
	@Autowired
	private EventImageDao eventImageDao;
	@Autowired
	private AmazonClient amazonClient;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	UserDao userDao;

	@Override
	public Event addEvent(Event event, int targetId, int typeId){
		Event newEvent= new Event();
		if (event.getEventTitle()==null || /*event.getEventType()==null*/ typeId == 0 ||/* event.getEventTargetGroup()==null*/ targetId == 0||
				event.getLat()==null|| event.getLng()==null || event.getEventDate()==null || event.getEventStartTime()==null ||
				event.getEventEndtTime()==null || event.getEventOrganizerNumber()==0 || event.getEventSummary()==null ||
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
			event.setRemainingSpot(event.getEventOrganizerNumber());
			
			switch(typeId) {
			case 1: event.setEventType(EventType.EXPOSITION); break; 
			case 2: event.setEventType(EventType.ARTS); break; 
			case 3:	event.setEventType(EventType.MUSIC); break; 
			case 4: event.setEventType(EventType.CONCERT); break; 
			case 5: event.setEventType(EventType.EDUCATIONAL); break; 
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
	public List<Event> getAllVolunteerEvent(String token) {
		if (token != null) {
			token = token.replace("Bearer ", "");
			String userName = jwtTokenUtil.getUsernameFromToken(token);
			
			User user = userDao.mobileOremail(userName);
			if( user != null) {
				if (user.getIsPremium() == false) {
					return eventDao.getAllBasicVolunteerEvent();
				}else {
					return eventDao.getAllVolunteerEvent();
				}
				
			}else {
				throw new NotFoundException();
			}
		}else {
			throw new NotFoundException();
		}
		
	}
	
	@Override
	public List<Event> getAllRegEvent(String token) {
		if (token != null) {
			token = token.replace("Bearer ", "");
			String userName = jwtTokenUtil.getUsernameFromToken(token);
			
			User user = userDao.mobileOremail(userName);
			if( user != null) {
				if (user.getIsPremium() == false) {
					return eventDao.getAllBasicRegEvent();
				}else {
					return eventDao.getAllRegEvent();
				}
				
			}else {
				throw new NotFoundException();
			}
		}else {
			throw new NotFoundException();
		}
	}	
	
	@Override
	public List<Event> getAllAdminVolunteerEvent() {
		return eventDao.getAllVolunteerEvent();
	}
	
	@Override
	public List<Event> getAllAdminRegEvent() {
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
	public List<Event> search(String word, String token) {
		
		if (token != null) {
			token = token.replace("Bearer ", "");
			String userName = jwtTokenUtil.getUsernameFromToken(token);
			
			List<Event> event = null;
			
			User user = userDao.mobileOremail(userName);
			if( user != null) {
				if (user.getIsPremium() == false) {					
					event =  eventDao.searchByBasicUser(word);

					if(event != null) {
						return event;
						}else {
							throw new NotFoundException("MSG009");
						}
					
				}else {
					event =  eventDao.searchByPremiumUser(word);

					if(event != null) {
						return event;
						}else {
							throw new NotFoundException("MSG009");
						}
				}
				
			}else {
				throw new NotFoundException();
			}
		}else {
			throw new NotFoundException();
		}
		
		
	}
	
	@Override
	public Event addImage(MultipartFile file, int eventId) {
		String url = "";
		Event event= eventDao.findOne(eventId);
		if(event != null) {
		try {
			url = amazonClient.uploadFileEvent(file);
		} catch (IOException e) {
		}
		
		EventImage eventImage = new EventImage( file.getOriginalFilename() ,  url , event);
		eventImageDao.save(eventImage);
		event.setEventImage(url);
		eventDao.save(event);
		}else {
			throw new NotFoundException("MSG013");
		}
		return event;
	}
	
}
