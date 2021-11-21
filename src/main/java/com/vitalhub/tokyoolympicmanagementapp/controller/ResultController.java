package com.vitalhub.tokyoolympicmanagementapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitalhub.tokyoolympicmanagementapp.dto.ResultDTO;
import com.vitalhub.tokyoolympicmanagementapp.service.ResultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/results/")
@RestController
public class ResultController {

	private final ResultService resultService;
	
	@Autowired
	public ResultController(ResultService resultService) {
		this.resultService = resultService;
	}
	
	@GetMapping
	public List<ResultDTO> getAllResults() {
		List<ResultDTO> allResults = resultService.getResults();
		if(!allResults.isEmpty()) {
			return allResults;
		}
		else {
			log.info("No results available.");
			throw new EmptyResultDataAccessException("No results available.", 0);
		}
	}
	
	@GetMapping(path = "{resultId}")
	public ResultDTO getResult(@PathVariable ("resultId") Long resultId) {
		ResultDTO existingResultRecord = resultService.getResultById(resultId);
		if(existingResultRecord.equals(null)) {
			return existingResultRecord;
		}
		else {
			log.info("No results available.");
			throw new EmptyResultDataAccessException("No results available.", 0);
		}
	}
	
	@PostMapping
	public void createResult(@RequestBody ResultDTO resultDTO) throws IOException {
			resultService.addResult(resultDTO);
	}
	
	@PutMapping("{resultId}")
	public ResultDTO updateResult(@RequestBody ResultDTO resultDTO, @PathVariable ("resultId") Long resultId) {
		return resultService.updateResult(resultDTO, resultId);
	}
	
	@DeleteMapping(path = "{resultId}")
	public void deleteResultRecord(@PathVariable ("resultId") Long resultId) {
		resultService.deleteResult(resultId);
	}
}
