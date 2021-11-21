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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "athletes")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Athlete {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate dateOfBirth;
	private String country;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "athlete_image_id", referencedColumnName = "id")
	private Image athleteImage;
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "athletes")
//	@JoinTable(name = "athletes_events", joinColumns = @JoinColumn(name = "athlete_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	private List<Event> events;
	private LocalDateTime createdOn;
	
	public Athlete(String firstName, String lastName, String gender, LocalDate dateOfBirth, String country,
			Image athleteImage, List<Event> events, LocalDateTime createdOn) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.athleteImage = athleteImage;
		this.events = events;
		this.createdOn = createdOn;
	}	
}
