package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員関連機能の業務処理を行うクラス.
 * 
 * @author takahiro.okuma
 *
 */
@Service
public class EmployeeService {

	/** 従業員テーブルを操作するリポジトリ */
	@Autowired
	private EmployeeRepository repository;

	/**
	 * IDによって従業員を1件取得する.
	 * 
	 * @param id 取得したい従業員のID
	 * @return 指定されたIDの従業員ドメイン
	 */
	public Employee getEmployeeById(int id) {
		return repository.load(id);
	}

	/**
	 * 従業員の情報を全部取得する.
	 * 
	 * @return 全従業員のドメインのリスト
	 */
	public List<Employee> getAllEmployee() {
		List<Employee> employeeList = repository.findAll();
		return employeeList;
	}

	/**
	 * 扶養人数を更新する.
	 * 
	 * @param employee 更新したい従業員ドメイン
	 * @return 更新された従業員ドメイン
	 */
	public Employee dependentsCountUpdate(Employee employee) {
		return repository.update(employee);
	}
}
