package jp.co.sample.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
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

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	// TOD: アプリケーションスコープを使わずリクエストスコープを用いる ⇒DBがアプリケーションスコープの代わりになるから
	// TOD: リクエストスコープにフォームオブジェクトを格納し詳細画面を表示する

	/**
	 * 従業員一覧を表示させる.
	 * 
	 * @return 従業員一覧画面
	 */
	@RequestMapping("")
	public String index(Model model) {

		List<Employee> employeeList = service.getAllEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/**
	 * 詳細画面を表示させる.
	 * 
	 * @param index 従業員一覧における順番
	 * @param model リクエストスコープ
	 * @return 詳細画面
	 */
	@RequestMapping("/toDetail")
	public String toDetail(int id, Model model) {
		Employee employee = service.getEmployeeById(id);
		model.addAttribute("employee", employee);
		System.out.println("[Fn:toDetail]" + employee);
		return "employee/detail";
	}

	/**
	 * 従業員編集画面を表示させる.
	 *
	 * @param id    編集したい従業員のID
	 * @param model リクエストスコープ
	 * @return 従業員編集画面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(int id, Model model) {
		Employee employee = service.getEmployeeById(id);
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		BeanUtils.copyProperties(employee, updateEmployeeForm);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		updateEmployeeForm.setHireDate(employee.getHireDate().format(formatter));
		model.addAttribute("updateEmployeeForm", updateEmployeeForm);

		return "employee/edit";
	}

	/**
	 * 従業員情報を更新する.
	 * 
	 * @param updateEmployeeForm 従業員更新時に使用するフォーム
	 * @param result             入力値チェックのエラー群
	 * @param model              リクエストスコープ
	 * @return 詳細画面を表示させるメソッド
	 */
	@RequestMapping("/updateEmployeeInfo")
	public String updateEmployeeInfo(@Validated UpdateEmployeeForm updateEmployeeForm, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "employee/edit";
		}

		Employee employee = service.getEmployeeById(updateEmployeeForm.getId());
		BeanUtils.copyProperties(updateEmployeeForm, employee);
		employee.setHireDate(
				LocalDate.parse(updateEmployeeForm.getHireDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		service.updateEmployee(employee);

		return index(model);
	}
}
