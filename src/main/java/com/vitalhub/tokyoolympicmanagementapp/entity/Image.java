package com.vitalhub.tokyoolympicmanagementapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "images")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	@Column(length = 1000)
	private byte[] data;
//	@OneToOne(mappedBy = "athleteImage")
//	private Athlete athlete;
	
	public Image(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
//		this.athlete = athlete;
	}
}
