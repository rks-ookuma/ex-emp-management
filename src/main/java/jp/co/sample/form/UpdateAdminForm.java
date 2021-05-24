package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 管理者情報を更新する際に利用するフォーム.
 * 
 * @author takahiro_okuma
 *
 */
public class UpdateAdminForm {

	/** 更新する管理者ドメインのID */
	private Integer id;
	/** 更新したい名前 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** 更新したいメールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "Emailの形式が不正です")
	private String mailAddress;
	/** 現在のパスワード */
	@NotBlank(message = "現在のパスワードを入力してください")
	private String pastPassword;
	/** 更新後のパスワード */
	@NotBlank(message = "変更後のパスワードを入力してください")
	private String updatePassword;
	/** 更新後のパスワードを確認するためのパスワード */
	@NotBlank(message = "変更後のパスワードをもう一度入力してください")
	private String checkUpdatePassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPastPassword() {
		return pastPassword;
	}

	public void setPastPassword(String pastPassword) {
		this.pastPassword = pastPassword;
	}

	public String getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	public String getCheckUpdatePassword() {
		return checkUpdatePassword;
	}

	public void setCheckUpdatePassword(String checkUpdatePassword) {
		this.checkUpdatePassword = checkUpdatePassword;
	}

	@Override
	public String toString() {
		return "UpdateAdminForm [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", pastPassword="
				+ pastPassword + ", updatePassword=" + updatePassword + ", checkUpdatePassword=" + checkUpdatePassword
				+ "]";
	}

}
