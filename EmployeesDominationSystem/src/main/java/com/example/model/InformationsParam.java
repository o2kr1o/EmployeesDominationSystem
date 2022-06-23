package com.example.model;

import java.sql.Date;

import lombok.Data;

@Data
public class InformationsParam {
	private Long id;
	private Long dept_id;
	private String name;
	private String status;
	private Date update_date;
	private String divisions;
	private String territories;
	private Long clients;
	private Long profits;
}
