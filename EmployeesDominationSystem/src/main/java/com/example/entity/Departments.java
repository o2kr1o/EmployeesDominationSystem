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
@Table(name="departments")
public class Departments {
	@Id
	@Column(name="dept_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long dept_id;

	@Column(name="divisions")
	private String divisions;
}
