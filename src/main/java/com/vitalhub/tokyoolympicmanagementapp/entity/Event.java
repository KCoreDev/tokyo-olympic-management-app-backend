package com.vitalhub.tokyoolympicmanagementapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "events")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private LocalDate eventDate;
	private String location;
//	@Enumerated(EnumType.STRING)
	private String eventStatus; // Yet to Start, Started, Finished, Cancelled, Postponed
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "result_id", nullable = false)
	private Result result;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "events_athletes", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "athlete_id"))
	private List<Athlete> athletes;
	private LocalDateTime createdOn;
	
	public Event(String name, String description, LocalDate eventDate, String location, String eventStatus,
			Result result, List<Athlete> athletes, LocalDateTime createdOn) {
		this.name = name;
		this.description = description;
		this.eventDate = eventDate;
		this.location = location;
		this.eventStatus = eventStatus;
		this.result = result;
		this.athletes = athletes;
		this.createdOn = createdOn;
	}
	
	
}
