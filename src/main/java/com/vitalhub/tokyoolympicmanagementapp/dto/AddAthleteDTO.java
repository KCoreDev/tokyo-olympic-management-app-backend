package com.vitalhub.tokyoolympicmanagementapp.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAthleteDTO {
	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String gender;
	@JsonProperty
	private String dateOfBirth;
	@JsonProperty
	private String country;
	@JsonProperty
	private List<ExistingEventsDTO> events;
}
