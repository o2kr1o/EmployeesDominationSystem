package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Areas;
import com.example.entity.Departments;
import com.example.model.BulkRegistrationsListParam;
import com.example.model.GroupOrder;
import com.example.service.BulkRegistrationsService;

@Controller
public class BulkRegistrationsController {
	@Autowired
	BulkRegistrationsService bulkRegistrationsService;

	@GetMapping("/bulkRegist")
	public String displayList(Model model) {
		BulkRegistrationsListParam bulkRegistrationsListParam = bulkRegistrationsService.searchAll();
		model.addAttribute("bulkRegistrationsListParam", bulkRegistrationsListParam);

		List<Departments> deptsList = bulkRegistrationsService.getdeptsList();
		model.addAttribute("deptsList", deptsList);

		List<Areas> areasList = bulkRegistrationsService.getAreasList();
		model.addAttribute("areasList", areasList);
		return "user/bulkRegist";
	}

	@PostMapping("/resetAll")
	public String resetAll() {
		bulkRegistrationsService.resetAll();
		return "redirect:bulkRegist";
	}

	@PostMapping("/tempSave")
	public String tempSave(@Validated(GroupOrder.class) BulkRegistrationsListParam bulkRegistrationsListParam, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : bindingResult.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "user/bulkRegist";
		} else {
			bulkRegistrationsService.tempSave(bulkRegistrationsListParam);
			return "redirect:bulkRegist";
		}
	}

	@PostMapping("/bulkRegist")
	public String updateList(@Validated(GroupOrder.class) BulkRegistrationsListParam bulkRegistrationsListParam, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : bindingResult.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "user/bulkRegist";
		} else {
			bulkRegistrationsService.updateAll(bulkRegistrationsListParam);
			return "redirect:total";
		}
	}

	@PostMapping("/deleteRow")
	public String deleteRow(BulkRegistrationsListParam bulkRegistrationsListParam, @RequestParam int index, Model model) {
		bulkRegistrationsService.deleteRow(bulkRegistrationsListParam, index);
		return "user/bulkRegist";
	}
}
