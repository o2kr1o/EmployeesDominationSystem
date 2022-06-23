package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.entity.Areas;
import com.example.entity.Departments;
import com.example.model.DepartmentsParam;
import com.example.model.EditingsParam;
import com.example.model.InformationsParam;
import com.example.service.DepartmentsService;

@Controller
public class DepartmentsController {
	@Autowired
	private DepartmentsService departmentsService;

	@GetMapping("/total")
	public String total(Model model) {
		List<DepartmentsParam> dpList = departmentsService.total();
		model.addAttribute("dpList", dpList);
		return "user/total";
	}

	@GetMapping("/dept/{dept_id}")
	public String dept(@PathVariable Long dept_id, @ModelAttribute InformationsParam iParam, Model model) {
		Departments depts = departmentsService.deptNo(dept_id);
		model.addAttribute("depts", depts);

		model.addAttribute("iParam", iParam);

		List<InformationsParam> ipList = departmentsService.info(iParam, dept_id);
		model.addAttribute("ipList", ipList);
		return "user/dept";
	}

	@GetMapping("/deptReturn/{id}")
	public String deptReturn(@PathVariable Long id, @ModelAttribute InformationsParam iParam, Model model) {
		Long deptInfo = departmentsService.getDeptId(id);
		Departments depts = departmentsService.deptNo(deptInfo);
		model.addAttribute("depts", depts);

		model.addAttribute("iParam", iParam);

		List<InformationsParam> ipList = departmentsService.info(iParam, deptInfo);
		model.addAttribute("ipList", ipList);
		return "user/dept";
	}

	@PostMapping("/retire/{id}")
	public String retire(@PathVariable Long id) {
		departmentsService.retire(id);
		return "redirect:/deptReturn/{id}";
	}

	@GetMapping("/update/{id}")
	public String edit(@PathVariable Long id, EditingsParam eParam, Model model) {
		departmentsService.getById(eParam, id);
		List<Areas> areasList = departmentsService.getAreasList();
		model.addAttribute("areasList", areasList);
		return "user/update";
	}

	@PutMapping("/update/{id}")
	public String update(@PathVariable Long id, @Validated EditingsParam eParam, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			departmentsService. getNameId(eParam, id);
			List<Areas> areasList = departmentsService.getAreasList();
			model.addAttribute("areasList", areasList);
			return "user/update";
		} else {
			departmentsService.save(eParam);
			return "redirect:/deptReturn/{id}";
		}
	}
}
