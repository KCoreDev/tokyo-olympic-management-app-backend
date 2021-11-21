package com.vitalhub.tokyoolympicmanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowExistingEventsInfoDTO {

	private Long id;
	private String name;
	private ResultDTO result;
}
