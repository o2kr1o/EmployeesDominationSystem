package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Areas;
import com.example.entity.Benefits;
import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.DepartmentsParam;
import com.example.model.EditingsParam;
import com.example.model.InformationsParam;
import com.example.repository.AreasRepository;
import com.example.repository.BenefitsRepository;
import com.example.repository.DepartmentsRepository;
import com.example.repository.EmployeesRepository;

@Service
public class DepartmentsService {
	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private AreasRepository areasRepository;

	@Autowired
	private BenefitsRepository benefitsRepository;

	public List<DepartmentsParam> total() {
		List<DepartmentsParam> dpList = new ArrayList<>();

		for(Long i = 1L ; i <= 4 ; i++) {
			DepartmentsParam dP = new DepartmentsParam();

			Long total_client = 0L;
			Long total_profit = 0L;
			Long avg_profit = 0L;
			Integer empCount = 1;

			List<Employees> empList = employeesRepository.findAll();

			for(Employees emp : empList) {
				if(emp.getDept_id() == i) {
					Benefits benefitsInfo = benefitsRepository.getById(emp.getId());
					if(!(emp.getStatus().equals("退職"))) {
						total_client += benefitsInfo.getClients();
						total_profit += benefitsInfo.getProfits();
						avg_profit = total_profit/empCount++;
					}
				}
			}
			Departments deptInfo = departmentsRepository.getById(i);
			dP.setDept_id(i);
			dP.setDivisions(deptInfo.getDivisions());
			dP.setTotal_client(total_client);
			dP.setTotal_profit(total_profit);
			dP.setAvg_profit(avg_profit);
			dpList.add(dP);
		}
		return dpList;
	}

	public List<InformationsParam> info(InformationsParam iParam, Long id) {
		List<InformationsParam> ipList = new ArrayList<>();
		List<Employees> empList = employeesRepository.findAll();

		for(Employees emp :empList) {
			if(emp.getDept_id() == id) {
				if("在籍".equals(iParam.getStatus()) && "退職".equals(emp.getStatus())) {
					continue;
				} else if("退職".equals(iParam.getStatus()) && "在籍".equals(emp.getStatus())) {
					continue;
				}

				InformationsParam infoParam = new InformationsParam();

				infoParam.setId(emp.getId());
				infoParam.setDept_id(emp.getDept_id());
				infoParam.setName(emp.getName());
				infoParam.setStatus(emp.getStatus());
				infoParam.setUpdate_date(emp.getUpdate_date());

				List<Areas> areasList = areasRepository.findAll();

				for(Areas areas : areasList) {
					if(areas.getArea_id() == emp.getArea_id()) {
						infoParam.setTerritories(areas.getTerritories());
					}
				}

				List<Benefits> benefitsList = benefitsRepository.findAll();

				for(Benefits benefits : benefitsList) {
					if(benefits.getId() == emp.getId()) {
						infoParam.setClients(benefits.getClients());
						infoParam.setProfits(benefits.getProfits());
					}
				}
				ipList.add(infoParam);
			}
		}
		return ipList;
	}

	public Departments deptNo(Long id) {
		List<Departments> drList = departmentsRepository.findAll();
		Departments dept = new Departments();
		for(Departments dr : drList) {
			if(dr.getDept_id() == id) {
				dept.setDivisions(dr.getDivisions());
			}
		}
		return dept;
	}

	public void retire(Long id) {
		Employees empInfo = employeesRepository.getById(id);
		empInfo.setStatus("退職");
		Date utilDate = new Date();
		Long timeInMilliSeconds = utilDate.getTime();
		java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		empInfo.setUpdate_date(sqlDate);
		employeesRepository.save(empInfo);
	}

	public Long getDeptId(Long id) {
		Employees empInfo = employeesRepository.getById(id);
		return empInfo.getDept_id();
	}

	public EditingsParam getById(EditingsParam eParam, Long id) {
		Employees empInfo = employeesRepository.getById(id);
		Areas areaInfo = areasRepository.getById(empInfo.getArea_id());
		Benefits benefitInfo = benefitsRepository.getById(id);

		eParam.setId(id);
		eParam.setName(empInfo.getName());
		eParam.setArea_id(areaInfo.getArea_id());
		eParam.setTerritories(areaInfo.getTerritories());
		eParam.setClients(benefitInfo.getClients());
		eParam.setProfits(benefitInfo.getProfits());
		return eParam;
	}

	public EditingsParam getNameId(EditingsParam eParam, Long id) {
		Employees empInfo = employeesRepository.getById(id);
		Benefits benefitInfo = benefitsRepository.getById(id);

		eParam.setId(id);
		eParam.setName(empInfo.getName());
		eParam.setClients(benefitInfo.getClients());
		eParam.setProfits(benefitInfo.getProfits());
		return eParam;
	}

	public List<Areas> getAreasList() {
		List<Areas> areasList = areasRepository.findAll();
		return areasList;
	}

	public void save(EditingsParam eParam) {
		Employees empInfo = employeesRepository.getById(eParam.getId());

		empInfo.setId(eParam.getId());
		empInfo.setArea_id(eParam.getArea_id());
		Date utilDate = new Date();
		Long timeInMilliSeconds = utilDate.getTime();
		java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		empInfo.setUpdate_date(sqlDate);
		employeesRepository.save(empInfo);

		Benefits benefits = new Benefits();
		benefits.setId(eParam.getId());
		benefits.setClients(eParam.getClients());
		benefits.setProfits(eParam.getProfits());
		benefitsRepository.save(benefits);
	}
}
