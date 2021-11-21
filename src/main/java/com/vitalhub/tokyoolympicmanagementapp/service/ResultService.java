package com.vitalhub.tokyoolympicmanagementapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vitalhub.tokyoolympicmanagementapp.dto.ResultDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Result;
import com.vitalhub.tokyoolympicmanagementapp.mapper.GeneralMapper;
import com.vitalhub.tokyoolympicmanagementapp.repository.ResultRepository;

@Service
public class ResultService {
	private final ResultRepository resultRepository;
	private final GeneralMapper generalMapper;
	
	@Autowired
	public ResultService(ResultRepository resultRepository, GeneralMapper generalMapper) {
		this.resultRepository = resultRepository;
		this.generalMapper = generalMapper;
	}
	
	public List<ResultDTO> getResults() {
		return resultRepository.findAll().stream().map(result -> generalMapper.resultToResultDTO(result)).toList();
	}
	
	public ResultDTO getResultById(Long resultId) {
		return generalMapper.resultToResultDTO(resultRepository.findById(resultId).get());
	}
	
	public ResultDTO addResult(ResultDTO resultDTO) {
		return generalMapper.resultToResultDTO(resultRepository.save(generalMapper.resultDTOToResult(resultDTO)));
	}
	
	public ResultDTO updateResult(ResultDTO resultDTO, Long resultId) {
		Result existingResultRecord = resultRepository.findById(resultId)
				.orElseThrow(() -> new EmptyResultDataAccessException("Result with " + resultId + " doesn't exist.", 0));
		
		Result updatedResult = generalMapper.resultDTOToResult(resultDTO);
		if(!existingResultRecord.equals(null)) {
			existingResultRecord.setName(updatedResult.getName());
		}
		return generalMapper.resultToResultDTO(existingResultRecord);
	}
	
	public void deleteResult(Long resultId) {
		Boolean resultExists = resultRepository.existsById(resultId);
		if(!resultExists) {
			throw new EmptyResultDataAccessException(0);
		} else {
			resultRepository.deleteById(resultId);
		}
	}
}
