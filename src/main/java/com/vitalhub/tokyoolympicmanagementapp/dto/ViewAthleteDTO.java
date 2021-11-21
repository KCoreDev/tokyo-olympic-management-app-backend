package com.vitalhub.tokyoolympicmanagementapp.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewAthleteDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate dateOfBirth;
	private String country;
	private ImageDTO athleteImage;
	private List<ShowExistingEventsInfoDTO> events;
}
