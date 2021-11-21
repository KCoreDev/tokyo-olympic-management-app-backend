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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vitalhub.tokyoolympicmanagementapp.dto.ViewAthleteDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Athlete;
import com.vitalhub.tokyoolympicmanagementapp.response.ListResponse;
import com.vitalhub.tokyoolympicmanagementapp.response.Response;
import com.vitalhub.tokyoolympicmanagementapp.service.AthleteService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/api/v1/athletes/")
@RestController
public class AthleteController {

	private final AthleteService athleteService;
	
	@Autowired
	public AthleteController(AthleteService athleteService) {
		this.athleteService = athleteService;
	}
	
	@GetMapping
	public ResponseEntity<ListResponse<ViewAthleteDTO>> getAllAthletes() throws IOException, EmptyResultDataAccessException {
		try {
			List<ViewAthleteDTO> athleteRecords = athleteService.getAthletes();
			return ResponseEntity.ok().body(new ListResponse<ViewAthleteDTO>(athleteRecords, "Registered athletes."));
		} catch (IOException ioException) {
			log.error("Unable to retrieve the athletes due to an internal error", ioException.getMessage());
			throw new IOException("Unable to retrieve the athletes due to an internal error");
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			log.error("No athlete records available.", emptyResultDataAccessException.getMessage());
			emptyResultDataAccessException.printStackTrace();
			throw new EmptyResultDataAccessException("No athlete records available.", 0);
		}
	}
	
	@GetMapping(path = "{athleteId}")
	public ResponseEntity<Response> getAthlete(@PathVariable ("athleteId") Long athleteId) {
		try {
			return ResponseEntity.ok().body(new Response(athleteService.getAthleteById(athleteId), "Requested athlete record"));
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			log.error("Athlete record not available.", emptyResultDataAccessException.getMessage());
			emptyResultDataAccessException.printStackTrace();
			throw new EmptyResultDataAccessException("Athlete record not available.", 0);
		}
		
	}
	
	@PostMapping
	public void createAthlete(@RequestParam (value = "file", required = false) MultipartFile file, @RequestParam (value = "athleteInfo") String athleteInfo) throws IOException {
		try {
			athleteService.addAthlete(file, athleteInfo);
		} catch (IOException ioException) {
			log.error("Unable to add athlete");
			ioException.printStackTrace();
			throw new IOException("Unable to add an athlete");
		}
	}
	
	@PutMapping
	public Athlete updateAthlete(@RequestBody Athlete athlete, Long athleteId) {
		return athleteService.updateAthlete(athlete, athleteId);
	}
	
	@DeleteMapping(path = "{athleteId}")
	public void deleteAthleteRecord(@PathVariable ("athleteId") Long athleteId) {
		athleteService.deleteAthlete(athleteId);
	}
	
}
