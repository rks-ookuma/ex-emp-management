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

	/**
	 * 従業員一覧を表示させる.
	 * 
	 * @return 従業員一覧画面
	 */
	@RequestMapping("")
	public String index() {

		List<Employee> employeeList = service.getAllEmployee();
		application.setAttribute("employeeList", employeeList);
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
	public String toDetail(int index, Model model) {
		System.out.println("index: " + index);
		@SuppressWarnings("unchecked")
		List<Employee> employeeList = (List<Employee>) application.getAttribute("employeeList");
		Employee employee = employeeList.get(index);
		model.addAttribute("employee", employee);
		System.out.println("[Fn:toDetail]" + employee);
		return "employee/detail";
	}

	/**
	 * 扶養人数を更新する.
	 * 
	 * @param updateEmployeeForm 従業員更新時に使用するフォーム
	 * @param result             入力値チェックのエラー群
	 * @param model              リクエストスコープ
	 * @return 詳細画面を表示させるメソッド
	 */
	@RequestMapping("/updateEmployeeInfo")
	public String updateEmployeeInfo(@Validated UpdateEmployeeForm updateEmployeeForm, BindingResult result,
			Model model) {
		System.out.println(updateEmployeeForm);

		if (result.hasErrors()) {
			Employee employee = service.getEmployeeById(updateEmployeeForm.getId());
			model.addAttribute("employee", employee);
			model.addAttribute("error", "扶養人数を入力してください");
			return "employee/detail";
		}

		// 案⓵：リストをViewから取得してきてループさせて従業員を取得する
//		@SuppressWarnings("unchecked")
//		List<Employee> employeeList = (List<Employee>) application.getAttribute("employeeList");
//
//		for (Employee employee : employeeList) {
//			if (employee.getId() == updateEmployeeForm.getId()) {
//				employee.setDependentsCount(updateEmployeeForm.getDependentsCount());
//				service.dependentsCountUpdate(employee);
//				break;
//			}
//		}

		// 案②：DBに接続して従業員を取得してくる⇒DBに接続する方がパフォーマンスが悪いのか？ループさせるほうがパフォーマンスが悪いのか？
		Employee employee = service.getEmployeeById(updateEmployeeForm.getId());
		employee.setDependentsCount(updateEmployeeForm.getDependentsCount());
		service.dependentsCountUpdate(employee);

		return index();
	}
}
