package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Areas;
import com.example.entity.Benefits;
import com.example.entity.BulkRegistrations;
import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.BulkRegistrationsListParam;
import com.example.model.BulkRegistrationsParam;
import com.example.repository.AreasRepository;
import com.example.repository.BenefitsRepository;
import com.example.repository.BulkRegistrationsRepository;
import com.example.repository.DepartmentsRepository;
import com.example.repository.EmployeesRepository;

@Service
public class BulkRegistrationsService {
	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private AreasRepository areasRepository;

	@Autowired
	private BenefitsRepository benefitsRepository;

	@Autowired
	private BulkRegistrationsRepository bulkRegistrationsRepository;

	public List<Areas> getAreasList() {
		List<Areas> areasList = areasRepository.findAll();
		return areasList;
	}

	public List<Departments> getdeptsList() {
		List<Departments> deptsList = departmentsRepository.findAll();
		return deptsList;
	}

	public BulkRegistrationsListParam searchAll() {
		List<BulkRegistrations> brList = bulkRegistrationsRepository.findAll();

		BulkRegistrationsListParam bulkRegistrationsListParam = new BulkRegistrationsListParam();
		List<BulkRegistrationsParam> brpList = new ArrayList<>();

		if(brList == null || brList.size() == 0) {
			BulkRegistrationsParam bulkRegistrationsParam = new BulkRegistrationsParam();
			Date date = new Date();
			Long timeInMilliSeconds = date.getTime();
			java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
			bulkRegistrationsParam.setStart_date(sqlDate);
			brpList.add(bulkRegistrationsParam);
		} else {
			for(BulkRegistrations br : brList) {
				BulkRegistrationsParam bulkRegistrationsParam = new BulkRegistrationsParam();

				bulkRegistrationsParam.setId(br.getId());
				bulkRegistrationsParam.setDept_id(br.getDept_id());
				bulkRegistrationsParam.setArea_id(br.getArea_id());
				bulkRegistrationsParam.setPassword(br.getPassword());
				bulkRegistrationsParam.setName(br.getName());
				bulkRegistrationsParam.setEmail(br.getEmail());
				bulkRegistrationsParam.setPhone(br.getPhone());
				bulkRegistrationsParam.setStart_date(br.getStart_date());
				bulkRegistrationsParam.setGender(br.getGender());
				bulkRegistrationsParam.setStatus(br.getStatus());
				bulkRegistrationsParam.setDivisions(br.getDivisions());
				bulkRegistrationsParam.setTerritories(br.getTerritories());
				bulkRegistrationsParam.setClients(br.getClients());
				bulkRegistrationsParam.setProfits(br.getProfits());
				brpList.add(bulkRegistrationsParam);
			}
		}
		bulkRegistrationsListParam.setBulkRegistrationsParam(brpList);
		return bulkRegistrationsListParam;
	}

	public void updateAll(BulkRegistrationsListParam bulkRegistrationsListParam) {
		if(bulkRegistrationsListParam.getBulkRegistrationsParam() != null) {
			for(BulkRegistrationsParam bulkRegistrationsParam : bulkRegistrationsListParam.getBulkRegistrationsParam() ) {
				if(bulkRegistrationsParam.getPassword() != null &&
					StringUtils.hasLength(bulkRegistrationsParam.getName()) &&
					bulkRegistrationsParam.getEmail() != null &&
					bulkRegistrationsParam.getPhone() != null) {
					Employees emp = new Employees();
					emp.setPassword(bulkRegistrationsParam.getPassword());
					emp.setName(bulkRegistrationsParam.getName());
					emp.setDept_id(bulkRegistrationsParam.getDept_id());
					emp.setArea_id(bulkRegistrationsParam.getArea_id());
					emp.setEmail(bulkRegistrationsParam.getEmail());
					emp.setPhone(bulkRegistrationsParam.getPhone());
					emp.setStart_date(bulkRegistrationsParam.getStart_date());
					emp.setGender(bulkRegistrationsParam.getGender());
					emp.setStatus(bulkRegistrationsParam.getStatus());

					Date utilDate = new Date();
					Long timeInMilliSeconds = utilDate.getTime();
					java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
					emp.setUpdate_date(sqlDate);
					employeesRepository.save(emp);

					Benefits benefits = new Benefits();
					benefits.setId(emp.getId());
					benefits.setClients(bulkRegistrationsParam.getClients());
					benefits.setProfits(bulkRegistrationsParam.getProfits());
					benefitsRepository.save(benefits);
				}
			}
		}
		bulkRegistrationsRepository.deleteAll();
	}

	public void tempSave(BulkRegistrationsListParam bulkRegistrationsListParam) {
		bulkRegistrationsRepository.deleteAll();
		if(bulkRegistrationsListParam.getBulkRegistrationsParam() != null) {
			for(BulkRegistrationsParam bulkRegistrationsParam : bulkRegistrationsListParam.getBulkRegistrationsParam() ) {
				if(bulkRegistrationsParam.getPassword() != null &&
					StringUtils.hasLength(bulkRegistrationsParam.getName()) &&
					bulkRegistrationsParam.getEmail() != null &&
					bulkRegistrationsParam.getPhone() != null) {
					BulkRegistrations br = new BulkRegistrations();
					br.setPassword(bulkRegistrationsParam.getPassword());
					br.setName(bulkRegistrationsParam.getName());
					br.setDept_id(bulkRegistrationsParam.getDept_id());
					br.setArea_id(bulkRegistrationsParam.getArea_id());
					br.setEmail(bulkRegistrationsParam.getEmail());
					br.setPhone(bulkRegistrationsParam.getPhone());
					br.setStart_date(bulkRegistrationsParam.getStart_date());
					br.setGender(bulkRegistrationsParam.getGender());
					br.setStatus(bulkRegistrationsParam.getStatus());
					br.setClients(bulkRegistrationsParam.getClients());
					br.setProfits(bulkRegistrationsParam.getProfits());
					bulkRegistrationsRepository.save(br);
				}
			}
		}
	}

	public void resetAll() {
		bulkRegistrationsRepository.deleteAll();
	}

	public BulkRegistrationsListParam deleteRow(BulkRegistrationsListParam bulkRegistrationsListParam, @RequestParam int index) {
		List<BulkRegistrationsParam> brpList = bulkRegistrationsListParam.getBulkRegistrationsParam();
		if(brpList == null || brpList.size() == 0) {
		} else {
			brpList.remove(index);
		}
		bulkRegistrationsListParam.setBulkRegistrationsParam(brpList);
		return bulkRegistrationsListParam;
	}
}
