package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ログイン時に使用するフォームクラス.
 * 
 * @author takahiro.okuma
 *
 */
public class LoginForm {

	/** ログインしたいアカウントのメールアドレス */
	@Email(message = "Emailの形式が不正です")
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;

	/** ログインしたいアカウントのパスワード */
	@NotBlank(message = "パスワードを入力してください")
	private String password;

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

	@Override
	public String toString() {
		return "LoginForm [mailAddress=" + mailAddress + ", password=" + password + "]";
	}

}
