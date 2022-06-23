package com.example.model;

import lombok.Data;

@Data
public class DepartmentsParam {
	private Long dept_id;
	private String divisions;
	private String territories;
	private Long total_client;
	private Long total_profit;
	private Long avg_profit;
}
