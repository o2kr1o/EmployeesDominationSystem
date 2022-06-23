package com.example.model;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class BulkRegistrationsListParam {
	@Valid
	private List<BulkRegistrationsParam> bulkRegistrationsParam;
}
