package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="benefits")
public class Benefits {
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="clients")
	private Long clients;

	@Column(name="profits")
	private Long profits;
}
