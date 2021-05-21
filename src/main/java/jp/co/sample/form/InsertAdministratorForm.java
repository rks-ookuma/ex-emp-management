package jp.co.sample.form;

import javax.validation.constraints.NotBlank;

/**
 * 管理者登録時に使用するフォーム
 * 
 * @author takahiro.okuma
 *
 */
public class InsertAdministratorForm {

	/** 登録したい入力された名前 */
	@NotBlank(message = "氏名を入力してください")
	private String name;

	/** 登録したい入力されたメールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;

	/** 登録したい入力されたパスワード */
	@NotBlank(message = "パスワードを入力してください")
	private String password;

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
