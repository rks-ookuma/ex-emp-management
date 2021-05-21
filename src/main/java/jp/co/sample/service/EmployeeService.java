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
	 * 従業員の情報を全部取得する.
	 * 
	 * @return 全従業員のドメインのリスト
	 */
	public List<Employee> getAllEmployee() {
		List<Employee> employeeList = repository.findAll();
		return employeeList;
	}
}
