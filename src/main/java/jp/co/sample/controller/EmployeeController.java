package jp.co.sample.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
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

	@RequestMapping("")
	public String index() {

		List<Employee> employeeList = service.getAllEmployee();
		application.setAttribute("employeeList", employeeList);
		System.out.println(employeeList.get(1));
		return "employee/list";
	}

	@RequestMapping("/toDetail")
	public String toDetail(int id, Model model) {
		System.out.println("id:" + id);
		@SuppressWarnings("unchecked")
		List<Employee> employeeList = (List<Employee>) application.getAttribute("employeeList");
		Employee employee = employeeList.get(id - 1);
		model.addAttribute("employee", employee);

		return "employee/detail";
	}

	@RequestMapping("/updateEmployeeInfo")
	public String updateEmployeeInfo(int dependentsCount) {

	}
}
