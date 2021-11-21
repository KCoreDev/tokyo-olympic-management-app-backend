package com.vitalhub.tokyoolympicmanagementapp.mapper;

import org.mapstruct.Mapper;

import com.vitalhub.tokyoolympicmanagementapp.dto.AddAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.AthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.AthleteDTOForImageUpdate;
import com.vitalhub.tokyoolympicmanagementapp.dto.EventDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ExistingEventsDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ImageDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ResultDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ShowExistingEventsInfoDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ViewAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Athlete;
import com.vitalhub.tokyoolympicmanagementapp.entity.Event;
import com.vitalhub.tokyoolympicmanagementapp.entity.Image;
import com.vitalhub.tokyoolympicmanagementapp.entity.Result;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

	/**
	 * 
	 * @param addAthleteDTO is used to map the JSON string passed from the createAthete method. The data in this DTO 
	 * 			will be then converted to an Athlete object and saved in the databse. 
	 * @return an Athlete object.
	 */
	Athlete addAthleteDTOToAthlete(AddAthleteDTO addAthleteDTO);
	
	/**
	 * 
	 * @param athlete used mainly in the Athlete service to convert the Athlete type instances passed from the 
	 * 			database to the AthleteDTO. AthleteDTO is then passed to the API layer as required.
	 * @return an AthleteDTO object.
	 */
	AthleteDTO athleteToAthleteDTO(Athlete athlete);
	
	/**
	 * 
	 * @param athleteDTO vice versa of athleteToAthleteDTO. Mainly used at the service layer to convert the incoming 
	 * 			records from the front end to Athlete type since the JPA repository is defined to use Athlete type.
	 * @return Athlete object.
	 */
	Athlete athleteDTOToAthlete(AthleteDTO athleteDTO);
	
	/**
	 * 
	 * @param The AthleteDTOForImageUpdate was created to pass the Athlete id as an object when saving an image. 
	 * 			This athlete's id has to be passed as an object since the image and the Athlete maintains a one to one 
	 * 			relationship mapping configured in the entity layer.
	 * @return an Athlete object.
	 */
	Athlete athleteDTOForImageUpdateToAthlete(AthleteDTOForImageUpdate athleteDTOForImageUpdate);
	
	/**
	 * 
	 * @param athlete converts an object of Athlete type to ViewAthleteDTO to send to the front end. This DTO 
	 * 			is used to return multiple and single records of the athletes.  
	 * @return ViewAthleteDTO instance containing the information of an athlete.This DTO has athlete information, events 
	 * 			the athletes participates, and the results of those events. 
	 */
	ViewAthleteDTO athleteToViewAthleteDTO(Athlete athlete);
	
	/**
	 * 
	 * @param event the list of events to be sent to the front end events dropdown which will be shown in the 
	 * 			athlete add form. 
	 * 			 
	 * @return ExistingEventsDTO. ExistingEventsDTO only has the event id and the name. The id and the name will be passed
	 * 			within the JSON string that has the athlete information which is transferred into the AddAthleteDTO. 
	 * 			The AddAthleteDTO has a list of ExistingEventsDTO to hold multiple events if an employee is 
	 * 			participating in multiple events.
	 */
	ExistingEventsDTO eventToExistingEventDTO(Event event);
	
	/**
	 * 
	 * @param all the events available. In this scenario this object will be the list of events an employee is participating in.
	 * @return ShowExistingEventsInfoDTO object that has the events and results of those events attached to an employee.
	 * 			The difference between ExistingEventsDTO and the ShowExistingEventsInfoDTO is that this DTO is sent within the 
	 * 			ViewAthleteDTO with the result information, where ExistingEventsDTO is used to display the events available in the 
	 * 			athlete add form and to map the events the athlete is interested to partake in with the AddAthleteDTO. 
	 */
	ShowExistingEventsInfoDTO eventToShowExistingEventsInfoDTO(Event event);
	
	/**
	 * 
	 * @param existingEventsDTO purpose is explained in the above mapper description. 
	 * @return an event with all event details to save to the database. 
	 */
	Event existingEventsDTOToEvent(ExistingEventsDTO existingEventsDTO);
	
	EventDTO eventToEventDTO(Event event);
	
	
//	List<EventDTO> eventListToEventDTOList(List<Event> events);
	
//	List<Event> eventDTOListToEventList(List<EventDTO> eventsDTO);
	
//	Event eventListToEvent(List<Event> events);
	
	Event eventDTOToEvent(EventDTO eventDTO);
	
//	List<ExistingEventsDTO> eventsToEventAllDTOs(List<Event> events);
	
//	List<Event> existingEventsDTOListToEventsList(List<ExistingEventsDTO> existingEventsDTOs);
	
	/**
	 * 
	 * @param image entity object.
	 * @return an imageDTO object that has the same information that an object of the Image entity type has.
	 */
	ImageDTO imageToImageDTO(Image image);
	
	/**
	 * 
	 * @param imageDTO has the information passed from the API layer to the service layer. 
	 * @return an image object to persist to the database. 
	 */
	Image imageDTOTOImage(ImageDTO imageDTO);
	
	/**
	 * 
	 * @param has the information passed from the API layer to the service layer.
	 * @return a result object of Result type to persist to the database. 
	 */
	Result resultDTOToResult(ResultDTO resultDTO);
	
	/** 
	 * Vice versa of the resultDTOToResult mapper method.
	 * 
	 * @param result entity object.
	 * @return a ResultDTO type that has the same information that an object of the Result type has. 
	 */
	ResultDTO resultToResultDTO(Result result);
	
}
