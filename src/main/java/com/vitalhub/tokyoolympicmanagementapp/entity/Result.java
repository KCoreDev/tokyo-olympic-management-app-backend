package com.vitalhub.tokyoolympicmanagementapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "results")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
//	@OneToMany(mappedBy = "result")
//	private List<Event> events;
	
	public Result(String name) {
		this.name = name;
//		this.events = events;
	}
}
