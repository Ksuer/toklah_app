package com.toklahBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toklahBackend.service.Imp.EventServiceImp;

import javassist.NotFoundException;

import com.toklahBackend.model.Event;;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventServiceImp eventServiceImp;

	public EventController(EventServiceImp eventServiceImp) {
		this.eventServiceImp = eventServiceImp;
	}

	HttpHeaders responseHeaders = new HttpHeaders();

	@RequestMapping(value = "/{typeId}/{targetId}/addEvent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Event> addEvent(@RequestBody Event event, @PathVariable int targetId,
			@PathVariable int typeId) {	
		Event myEvent = eventServiceImp.addEvent(event, targetId, typeId);

		return new ResponseEntity<Event>(myEvent, responseHeaders, HttpStatus.CREATED);
	
	}
	
	@RequestMapping(value = "/getallevents", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Event>> getallEvent() {
		return new ResponseEntity<>(eventServiceImp.getAllEvent(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getevent/{eventId}",  method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getEvent(@PathVariable int eventId) throws NotFoundException {
		Event event = eventServiceImp.getEvent(eventId);
		if (event != null) {
			return new ResponseEntity<>(event, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value ="/search",  method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> search(@Param("word") String word) {
			return new ResponseEntity<>(eventServiceImp.search(word), responseHeaders, HttpStatus.OK);
	}

}
