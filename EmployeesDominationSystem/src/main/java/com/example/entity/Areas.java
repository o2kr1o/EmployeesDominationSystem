package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="areas")
public class Areas {
	@Id
	@Column(name="area_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long area_id;

	@Column(name="territories")
	private String territories;
}
