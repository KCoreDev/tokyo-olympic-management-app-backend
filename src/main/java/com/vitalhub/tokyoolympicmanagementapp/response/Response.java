package com.vitalhub.tokyoolympicmanagementapp.response;

import lombok.Data;

@Data
public class Response {

	private Object data;
	private String message;
	
	public Response(String message) {
		this.message = message;
	}
	
	public Response(Object data, String message) {
		this.data = data;
		this.message = message;
	}
}
