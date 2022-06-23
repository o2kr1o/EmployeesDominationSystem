package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Areas;
import com.example.entity.Benefits;
import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.EmployeesParam;
import com.example.repository.AreasRepository;
import com.example.repository.BenefitsRepository;
import com.example.repository.DepartmentsRepository;
import com.example.repository.EmployeesRepository;

@Service
public class EmployeesService {
	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private AreasRepository areasRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private BenefitsRepository benefitsRepository;

	public List<Employees> findAll() {
		return employeesRepository.findAll();
	}

	public List<Employees> serach(String name, String email, String phone) {
		return employeesRepository.search(name, email, phone);
	}

	public List<Departments> getDeptsList() {
		List<Departments> deptsList = departmentsRepository.findAll();
		return deptsList;
	}

	public List<Areas> getAreasList() {
		List<Areas> areasList = areasRepository.findAll();
		return areasList;
	}

	public void save(EmployeesParam eParam) {
		Employees emp = new Employees();
		emp.setId(eParam.getId());
		emp.setDept_id(eParam.getDept_id());
		emp.setArea_id(eParam.getArea_id());
		emp.setPassword(eParam.getPassword());
		emp.setName(eParam.getName());
		emp.setEmail(eParam.getEmail());
		emp.setPhone(eParam.getPhone());
		emp.setGender(eParam.getGender());
		emp.setStatus(eParam.getStatus());
		emp.setStart_date(eParam.getStart_date());

		Date utilDate = new Date();
		Long timeInMilliSeconds = utilDate.getTime();
		java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		emp.setUpdate_date(sqlDate);
		employeesRepository.save(emp);

		Benefits benefits = new Benefits();
		benefits.setId(emp.getId());
		benefits.setClients(0L);
		benefits.setProfits(0L);
		benefitsRepository.save(benefits);
	}

	public Employees getById(Long id) {
		return employeesRepository.getById(id);
	}

	public void deleteById(Long id) {
		employeesRepository.deleteById(id);
		benefitsRepository.deleteById(id);
	}
}
