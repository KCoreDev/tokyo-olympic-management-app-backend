package com.vitalhub.tokyoolympicmanagementapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vitalhub.tokyoolympicmanagementapp.dto.EventDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ExistingEventsDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Event;
import com.vitalhub.tokyoolympicmanagementapp.mapper.GeneralMapper;
import com.vitalhub.tokyoolympicmanagementapp.repository.EventRepository;

@Service
public class EventService {
	
	private final EventRepository eventRepository;
	private final GeneralMapper generalMapper;
	
	@Autowired
	public EventService(EventRepository eventRepository, GeneralMapper generalMapper) {
		this.eventRepository = eventRepository;
		this.generalMapper = generalMapper;
	}
	
	/**
	 * 
	 * @return all the event records.
	 */
	public List<ExistingEventsDTO> getEvents() {
		return eventRepository.findAll().stream().map(event -> generalMapper.eventToExistingEventDTO(event)).toList();
	}
	
	/**
	 * 
	 * @param eventId the id of the event to that needs to be retrieved from the database. 
	 * @return the retrieved event record's information. 
	 */
	
	public EventDTO getEventById(Long eventId) {
		return generalMapper.eventToEventDTO(eventRepository.findById(eventId).get());
	}
	
	/**
	 * 
	 * @param eventDTO has the event information to be saved.
	 * @return the saved event information. 
	 */
	public EventDTO addEvent(EventDTO eventDTO) {
		return generalMapper.eventToEventDTO(eventRepository.save(generalMapper.eventDTOToEvent(eventDTO)));
	}
	
	/**
	 * 
	 * @param eventDTO has the updated event information.
	 * @param eventId the eventId of the event record to be updated.
	 * @return the updated event record information. 
	 */
	@Transactional
	public EventDTO updateEvent(EventDTO eventDTO, Long eventId) {
		Event existingEventRecord = eventRepository.findById(eventId)
				.orElseThrow(() -> new EmptyResultDataAccessException("Event with " + eventId + " doesn't exist.", 0));
		
		Event updatedEvent = generalMapper.eventDTOToEvent(eventDTO);
		if(!existingEventRecord.equals(null)) {
			existingEventRecord.setName(updatedEvent.getName());
			existingEventRecord.setDescription(updatedEvent.getDescription());
			existingEventRecord.setEventDate(updatedEvent.getEventDate());
			existingEventRecord.setLocation(updatedEvent.getLocation());
			existingEventRecord.setResult(updatedEvent.getResult());
			existingEventRecord.setAthletes(updatedEvent.getAthletes());
		}
		return generalMapper.eventToEventDTO(existingEventRecord);
	}
	
	/**
	 * 
	 * @param eventId to be deleted. 
	 */
	public void deleteEvent(Long eventId) {
		Boolean eventExists = eventRepository.existsById(eventId);
		if(!eventExists) {
			throw new EmptyResultDataAccessException(0);
		} else {
			eventRepository.deleteById(eventId);
		}
	}

}
