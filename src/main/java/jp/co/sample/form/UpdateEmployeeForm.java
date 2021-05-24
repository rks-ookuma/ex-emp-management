package jp.co.sample.form;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
	/** 更新したい名前 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** 更新したい画像 */
	@NotEmpty(message = "画像を選択してください")
	private String image;
	/** 更新したい性別 */
	@NotEmpty(message = "性別を選択してください")
	private String gender;
	/** 更新したい入社日 */
	@NotNull(message = "入社日を選択してください")
	// FIXME: String で受け取るようにする
	private Date hireDate;
	/** 更新したいメールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;
	/** 更新したい郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	private String zipCode;
	/** 更新したい住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 更新したい電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	private String telephone;
	/** 更新したい給料 */
	@NotNull(message = "給料を入力してください")
	private Integer salary;
	/** 更新したい特性・性格 */
	@NotBlank(message = "特性を入力してください")
	private String characteristics;
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
		return "UpdateEmployeeForm [id=" + id + ", name=" + name + ", image=" + image + ", gender=" + gender
				+ ", hireDate=" + hireDate + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address="
				+ address + ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
				+ ", dependentsCount=" + dependentsCount + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
}
