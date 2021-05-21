package jp.co.sample.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員関連機能の処理の制御を行うコントローラー.
 * 
 * @author takahiro.okuma
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@Autowired
	private ServletContext application;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	@RequestMapping("")
	public String index() {

		List<Employee> employeeList = service.getAllEmployee();
		application.setAttribute("employeeList", employeeList);
		return "employee/list";
	}

	@RequestMapping("/toDetail")
	public String toDetail(int index, Model model) {
		System.out.println("index: " + index);
		@SuppressWarnings("unchecked")
		List<Employee> employeeList = (List<Employee>) application.getAttribute("employeeList");
		// 入社日ごとに並べたため修正必須
		Employee employee = employeeList.get(index);
		model.addAttribute("index", index);
		model.addAttribute("employee", employee);

		return "employee/detail";
	}

	@RequestMapping("/updateEmployeeInfo")
	public String updateEmployeeInfo(@Validated UpdateEmployeeForm updateEmployeeForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			// FIXME : getIndexが必ずnullになってしまっている。スコープにそもそも設置できていない？
			return toDetail(updateEmployeeForm.getIndex(), model);
		}

		@SuppressWarnings("unchecked")
		List<Employee> employeeList = (List<Employee>) application.getAttribute("employeeList");
		Employee employee = employeeList.get(updateEmployeeForm.getIndex());
		employee.setDependentsCount(updateEmployeeForm.getDependentsCount());

		service.dependentsCountUpdate(employee);

		return index();
	}
}
