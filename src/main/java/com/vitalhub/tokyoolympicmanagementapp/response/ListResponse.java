package com.vitalhub.tokyoolympicmanagementapp.response;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse <T> {

	private List<T> data;
	private String message;
	
	public ListResponse(List<T> data, String message) {
		this.data = data;
		this.message = message;
	}
	
	public ListResponse(String message) {
		this.message = message;
	}
}
