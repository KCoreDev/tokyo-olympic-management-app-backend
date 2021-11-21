package com.vitalhub.tokyoolympicmanagementapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

	private Long id;
	private String name;
	private String description;
	private LocalDate eventDate;
	private String location;
//	@Enumerated(EnumType.STRING)
	private String eventStatus;
	private ResultDTO result;
	private List<AthleteDTO> athletes;
	private LocalDateTime createdOn;
}
