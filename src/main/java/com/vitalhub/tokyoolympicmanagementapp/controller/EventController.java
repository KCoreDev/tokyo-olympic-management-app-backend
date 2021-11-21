package com.vitalhub.tokyoolympicmanagementapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitalhub.tokyoolympicmanagementapp.dto.EventDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ExistingEventsDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ViewAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.response.ListResponse;
import com.vitalhub.tokyoolympicmanagementapp.service.EventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/events/")
@RestController
public class EventController {

private final EventService eventService;
	
	@Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
	
	@GetMapping
	public ResponseEntity<ListResponse<ExistingEventsDTO>> getAllEvents() {
		List<ExistingEventsDTO> allEvents = eventService.getEvents();
		if(!allEvents.isEmpty()) {
			return ResponseEntity.ok().body(new ListResponse<ExistingEventsDTO>(allEvents, "Registered athletes."));
		}
		else {
			log.error("No events available.");
			throw new EmptyResultDataAccessException("No events available.", 0);
		}
	}
	
	@GetMapping(path = "{eventId}")
	public EventDTO getEvent(@PathVariable ("eventId") Long eventId) {
		EventDTO existingEventRecord = eventService.getEventById(eventId);
		if(existingEventRecord.equals(null)) {
			return existingEventRecord;
		}
		else {
			log.error("No events available.");
			throw new EmptyResultDataAccessException("No events available.", 0);
		}
	}
	
	@PostMapping
	public void createEvent(@RequestBody EventDTO eventDTO) throws IOException {
			eventService.addEvent(eventDTO);
	}
	
	@PutMapping("{eventId}")
	public EventDTO updateEvent(@RequestBody EventDTO eventDTO, @PathVariable ("eventId") Long eventId) {
		return eventService.updateEvent(eventDTO, eventId);
	}
	
	@DeleteMapping(path = "{eventId}")
	public void deleteEventRecord(@PathVariable ("eventId") Long eventId) {
		eventService.deleteEvent(eventId);
	}
	
}
