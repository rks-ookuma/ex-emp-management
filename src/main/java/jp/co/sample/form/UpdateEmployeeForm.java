package jp.co.sample.form;

import javax.validation.constraints.NotNull;

/**
 * 従業員更新時に使用するフォーム.
 * 
 * @author takahiro.okuma
 *
 */
public class UpdateEmployeeForm {

	/** 更新する従業員のID */
	private Integer id;
	/** 更新したい扶養人数 */
	@NotNull(message = "扶養人数を入力してください")
	private Integer dependentsCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}
}
