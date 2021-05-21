package jp.co.sample.repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員テーブルを操作するリポジトリ
 * 
 * @author takahiro.okuma
 *
 */
@Repository
public class EmployeeRepository {

	/** SQLの発行に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 従業員テーブル名 */
	private static final String TABLE_EMPLOYEES = "employees";

	/** 管理者テーブルの情報をドメインに変換するROW_MAPPER */
	private RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString(rs.getString("gender")));
		// FIXME:もしかしたら日時を変換できていないかも
		employee.setHireDate(LocalDate.parse(rs.getString("hire_date")));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

}
