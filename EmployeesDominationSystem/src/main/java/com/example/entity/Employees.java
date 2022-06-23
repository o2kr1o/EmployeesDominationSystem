package com.example.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="employees")
public class Employees {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="dept_id")
	private Long dept_id;

	@Column(name="area_id")
	private Long area_id;

	@Column(name="password")
	private String password;

	@Column(name="name")
	private String name;

	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

	@Column(name="gender")
	private String gender;

	@Column(name="status")
	private String status;

	@Column(name="start_date")
	private Date start_date;

	@Column(name="update_date")
	private Date update_date;
}
