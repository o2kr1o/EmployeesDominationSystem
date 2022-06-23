package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.entity.Areas;
import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.EmployeesParam;
import com.example.model.GroupOrder;
import com.example.service.EmployeesService;

@Controller
public class EmployeesController {
	@Autowired
	EmployeesService employeesService;

	@GetMapping("/top")
	public String top(Model model) {
		List<Employees> empList = employeesService.findAll();
		model.addAttribute("empList", empList);
		return "user/top";
	}

	@PostMapping("/top")
	public String search(EmployeesParam eParam, Model model) {
		List<Employees> empList = employeesService.serach(eParam.getName(), eParam.getEmail(), eParam.getPhone());
		model.addAttribute("empList", empList);
		return "user/top";
	}

	@GetMapping("/regist")
	public String regist(EmployeesParam eParam, Model model) {
		List<Departments> deptsList = employeesService.getDeptsList();
		model.addAttribute("deptsList", deptsList);

		List<Areas> areasList = employeesService.getAreasList();
		model.addAttribute("areasList", areasList);
		return "user/regist";
	}

	@PostMapping("/regist")
	public String create(@Validated(GroupOrder.class) EmployeesParam eParam, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<Departments> deptsList = employeesService.getDeptsList();
			model.addAttribute("deptsList", deptsList);

			List<Areas> areasList = employeesService.getAreasList();
			model.addAttribute("areasList", areasList);
			return "user/regist";
		} else {
			employeesService.save(eParam);
			return "redirect:/top";
		}
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, EmployeesParam eParam, Model model) {
		Employees empInfo = employeesService.getById(id);
		BeanUtils.copyProperties(empInfo, eParam);

		List<Departments> deptsList = employeesService.getDeptsList();
		model.addAttribute("deptsList", deptsList);

		List<Areas> areasList = employeesService.getAreasList();
		model.addAttribute("areasList", areasList);
		return "user/edit";
	}

	@PutMapping("/edit/{id}")
	public String update(@PathVariable Long id, @Validated(GroupOrder.class) EmployeesParam eParam, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() ) {
			List<Departments> deptsList = employeesService.getDeptsList();
			model.addAttribute("deptsList", deptsList);

			List<Areas> areasList = employeesService.getAreasList();
			model.addAttribute("areasList", areasList);
			return "user/edit";
		} else {
			employeesService.save(eParam);
			return "redirect:/top";
		}
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		employeesService.deleteById(id);
		return "redirect:/top";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		Employees empInfo = employeesService.getById(id);
		model.addAttribute("empInfo", empInfo);
		return "user/detail";
	}
}
