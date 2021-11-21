package com.vitalhub.tokyoolympicmanagementapp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.vitalhub.tokyoolympicmanagementapp.dto.AddAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.AthleteDTOForImageUpdate;
import com.vitalhub.tokyoolympicmanagementapp.dto.ImageDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ShowExistingEventsInfoDTO;
import com.vitalhub.tokyoolympicmanagementapp.dto.ViewAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Athlete;
import com.vitalhub.tokyoolympicmanagementapp.entity.Event;
import com.vitalhub.tokyoolympicmanagementapp.mapper.GeneralMapper;
import com.vitalhub.tokyoolympicmanagementapp.repository.AthleteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AthleteService {

	private final AthleteRepository athleteRepository;
	private final EventService eventService;
	private final ImageService imageService;

	GeneralMapper generalMapper;

	@Autowired
	public AthleteService(AthleteRepository athleteRepository, GeneralMapper generalMapper, EventService eventService,
			ImageService imageService) {
		this.athleteRepository = athleteRepository;
		this.generalMapper = generalMapper;
		this.eventService = eventService;
		this.imageService = imageService;
	}

	/**
	 * 
	 * 
	 * @return all the athlete records along with athletes information, events
	 *         athletes are participating in and their results.
	 * @throws IOException                    is thrown if any exception is raised
	 *                                        when decompressing the file data.
	 * @throws EmptyResultDataAccessException is thrown when athlete records are not
	 *                                        available.
	 * 
	 *                                        The purpose of each mapper used is
	 *                                        explained in the GeneralMapper class.
	 * 
	 */

	public List<ViewAthleteDTO> getAthletes() throws IOException, EmptyResultDataAccessException {
		List<ViewAthleteDTO> mappedAthleteRecords = new ArrayList<ViewAthleteDTO>();

		List<Athlete> existingAthleteRecords = athleteRepository.findAll();

		if (!existingAthleteRecords.isEmpty()) {

			existingAthleteRecords.stream().forEach(athlete -> {
				List<ShowExistingEventsInfoDTO> existingEventsDTOs = new ArrayList<ShowExistingEventsInfoDTO>();
				athlete.getEvents().stream().forEach(event -> {
					existingEventsDTOs.add(generalMapper.eventToShowExistingEventsInfoDTO(event));
				});
				mappedAthleteRecords.add(generalMapper.athleteToViewAthleteDTO(athlete));
			});

			List<ViewAthleteDTO> decompressedImageMappedAthleteRecords = new ArrayList<ViewAthleteDTO>();
			mappedAthleteRecords.stream().forEach(existingAthleteRecord -> {
				try {
					existingAthleteRecord.getAthleteImage()
							.setData(imageService.decompressBytes(existingAthleteRecord.getAthleteImage().getData()));
					decompressedImageMappedAthleteRecords.add(existingAthleteRecord);
				} catch (DataFormatException dataFormatException) {
					log.info("Data Format exception whilte decompressing the file.", dataFormatException.getMessage());
				}
			});
			return decompressedImageMappedAthleteRecords;
		} else {
			log.error("No athlete records available.");
			throw new EmptyResultDataAccessException("No athlete records available.", 0);
		}
	}

	/**
	 * 
	 * @param athleteId The id of the athlete selected.
	 * @return all the information of the selected athlete.
	 * 
	 */
	public ViewAthleteDTO getAthleteById(Long athleteId) {
		Athlete athleteRecord = athleteRepository.findById(athleteId).get();
		List<ShowExistingEventsInfoDTO> mapExistingEventsToShowExistingEventsInfoDTO = new ArrayList<ShowExistingEventsInfoDTO>();
		athleteRecord.getEvents().stream().forEach(event -> {
			mapExistingEventsToShowExistingEventsInfoDTO.add(generalMapper.eventToShowExistingEventsInfoDTO(event));
		});
		ViewAthleteDTO viewAthleteDTO = generalMapper.athleteToViewAthleteDTO(athleteRecord);
		viewAthleteDTO.setEvents(mapExistingEventsToShowExistingEventsInfoDTO);
		if (!viewAthleteDTO.equals(null)) {
			return viewAthleteDTO;
		} else {
			log.error("Athlete record not available.");
			throw new EmptyResultDataAccessException("Athlete record not available.", 0);
		}
	}

	/**
	 * Save athlete to the database. This method is handling the many to many
	 * relationship between the athlete and the events.
	 * 
	 * 
	 * @param file        the Multipart file passed to the endpoint from the front
	 *                    end. This file contains an athlete's image. This file will
	 *                    be decompressed and stored in the database as a blob. The
	 *                    image is compressed by calling the compressFileToBytes
	 *                    method in the Image service.
	 * @param athleteInfo information of the employee as a JSON string. This string
	 *                    is converted to the AddAthleteDTO object structure, then
	 *                    the data is extracted from the AddAthleteDTO and mapped to
	 *                    an Athlete object and passed to the save repository.
	 * 
	 * 
	 * @throws IOException
	 */
	public void addAthlete(MultipartFile file, String athleteInfo) throws IOException {
		Gson athleteInfoStringToAthleteGsonMapper = new Gson();
		try {
			AddAthleteDTO addAthleteDTO = athleteInfoStringToAthleteGsonMapper.fromJson(athleteInfo,
					AddAthleteDTO.class);

			log.info("Athlete object", addAthleteDTO);
			if (!addAthleteDTO.equals(null)) {

				List<Event> selectedEvents = addAthleteDTO.getEvents().stream().map(selectedEvent -> generalMapper
						.eventDTOToEvent(eventService.getEventById(selectedEvent.getId()))).toList();

				ImageDTO imageDTO = new ImageDTO();
				if (!file.equals(null)) {
					imageDTO.setName(file.getOriginalFilename());
					imageDTO.setType(file.getContentType());
					imageDTO.setData(imageService.compressFileToBytes(file.getBytes()));

				}

				Athlete athlete = new Athlete(addAthleteDTO.getFirstName(), addAthleteDTO.getLastName(),
						addAthleteDTO.getGender(), LocalDate.parse(addAthleteDTO.getDateOfBirth()),
						addAthleteDTO.getCountry(), generalMapper.imageDTOTOImage(imageDTO), selectedEvents,
						LocalDateTime.now());
				Athlete savedAthlete = athleteRepository.save(athlete);
				selectedEvents.stream().forEach(selectedEvent -> {
					selectedEvent.getAthletes().add(generalMapper
							.athleteDTOForImageUpdateToAthlete(new AthleteDTOForImageUpdate(savedAthlete.getId())));
					eventService.updateEvent(generalMapper.eventToEventDTO(selectedEvent), selectedEvent.getId());
				});
				if (savedAthlete.equals(null)) {
					throw new IOException("Athlete record not saved");
				}
			}
		} catch (IOException ioException) {
			log.error("Unable to add athlete", ioException.getMessage());
			throw new IOException();
		}
	}

	/**
	 * 
	 * @param updatedAthlete has the updated athlete information.
	 * @param athleteId      is the athlete id to be updated.
	 * @return
	 */
	@Transactional
	public Athlete updateAthlete(Athlete updatedAthlete, Long athleteId) {
		Athlete existingAthleteRecord = athleteRepository.findById(athleteId).orElseThrow(
				() -> new EmptyResultDataAccessException("Athlete with " + athleteId + " doesn't exist.", 0));

		if (!existingAthleteRecord.equals(null)) {
			existingAthleteRecord.setFirstName(updatedAthlete.getFirstName());
			existingAthleteRecord.setLastName(updatedAthlete.getLastName());
			existingAthleteRecord.setGender(updatedAthlete.getGender());
			existingAthleteRecord.setDateOfBirth(updatedAthlete.getDateOfBirth());
			existingAthleteRecord.setCountry(updatedAthlete.getCountry());
			existingAthleteRecord.setAthleteImage(updatedAthlete.getAthleteImage());
			existingAthleteRecord.setEvents(updatedAthlete.getEvents());
		}
		return existingAthleteRecord;
	}

	/**
	 * 
	 * @param athleteId is the id of the athlete to be deleted.
	 */
	public void deleteAthlete(Long athleteId) {
		Boolean athleteExists = athleteRepository.existsById(athleteId);
		if (!athleteExists) {
			throw new EmptyResultDataAccessException(0);
		} else {
			athleteRepository.deleteById(athleteId);
		}
	}
}