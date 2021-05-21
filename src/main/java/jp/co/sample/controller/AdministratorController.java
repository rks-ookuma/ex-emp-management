package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administractor;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

	@ModelAttribute
	public InsertAdministratorForm setUpForm() {
		return new InsertAdministratorForm();
	}

	@Autowired
	private AdministratorService service;

	@RequestMapping("")
	public String index() {
		return "administrator/login";
	}

	@RequestMapping("/toPageInsert")
	public String toPageInsert() {

		return "administrator/insert";
	}

	@RequestMapping("/registorAdmin")
	public String registorAdmin(@Validated InsertAdministratorForm insertAdministratorForm, BindingResult result) {

		if (result.hasErrors()) {
			return "administrator/insert";
		}
		Administractor administractor = new Administractor();
		BeanUtils.copyProperties(insertAdministratorForm, administractor);
		administractor = service.registerAdmin(administractor);
		System.out.println(administractor);

		return "administrator/login";
	}

}
