package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員テーブルを操作するリポジトリ.
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
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date").toLocalDate());
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

	/**
	 * idによって従業員をひとりだけ取得する.
	 * 
	 * @param id 取得したい従業員のＩＤ
	 * @return 指定したＩＤの従業員ドメイン
	 */
	public Employee load(int id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM "
				+ TABLE_EMPLOYEES + " WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}

	/**
	 * 従業員データを全件検索する.
	 * 
	 * @return 全従業員のドメインのリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count"
				+ " FROM " + TABLE_EMPLOYEES + " ORDER bY hire_date DESC;";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}

	/**
	 * 開始番号の次から指定された件数分の従業員データを検索する.
	 * 
	 * @param startIndex 何番目のデータから取得してくるかの最初の前の番号（1番目から取得する場合は、0を入力する）
	 * @param viewData   取得したいデータの件数
	 * @return 開始番号からの次から指定された件数分の従業員ドメインのリスト
	 */
	public List<Employee> find10Data(int startIndex, int viewData) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
				+ " FROM " + TABLE_EMPLOYEES + " ORDER BY hire_date DESC LIMIT :viewData OFFSET :startIndex;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("viewData", viewData).addValue("startIndex",
				startIndex);
		List<Employee> employee10DataList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee10DataList;
	}

	/**
	 * 従業員データを更新する.
	 * 
	 * @param employee 更新したい従業員ドメイン
	 * @return 更新された従業員ドメイン
	 */
	public Employee update(Employee employee) {
		String sql = "UPDATE " + TABLE_EMPLOYEES
				+ " SET name=:name,image=:image,gender=:gender,hire_date=:hireDate,mail_address=:mailAddress,zip_code=:zipCode,address=:address,telephone=:telephone,salary=:salary,characteristics=:characteristics,dependents_count=:dependentsCount"
				+ " WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
		return employee;
	}

}
