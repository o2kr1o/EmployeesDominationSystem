package com.example.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EditingsParam {
	@NotNull
	private Long id;
	private Long dept_id;
	@NotNull
	private Long area_id;
	private String name;
	private Date update_date;
	private String territories;
	@NotNull
	private Long clients;
	@NotNull
	private Long profits;
}
