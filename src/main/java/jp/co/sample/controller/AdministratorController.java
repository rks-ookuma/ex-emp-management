package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administractor;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.form.UpdateAdminForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者関連機能の処理の制御を行うコントローラー.
 * 
 * @author takahiro.okuma
 *
 */
@Controller
@RequestMapping("/admin")
public class AdministratorController {

	@Autowired
	private HttpSession session;

	/**
	 * 管理者登録フォームの値の保持.
	 * 
	 * @return 管理者登録フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	@ModelAttribute
	public UpdateAdminForm setUpUpdateAdminForm() {
		return new UpdateAdminForm();
	}

	/**
	 * ログインフォームの値の保持.
	 * 
	 * @return ログインフォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/** 管理者関連機能の業務処理を行うクラス */
	@Autowired
	private AdministratorService service;

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return ログイン画面のHTML
	 */
	@RequestMapping("")
	public String index() {
		return "administrator/login";
	}

	/**
	 * 管理者登録画面に遷移する.
	 * 
	 * @return 管理者登録画面のHTML
	 */
	@RequestMapping("/toPageInsert")
	public String toPageInsert() {

		return "administrator/insert";
	}

	/**
	 * 管理者の新規登録をする.
	 * 
	 * @param insertAdministratorForm 管理者登録時に使用するフォームする
	 * @param result                  入力値チェックのエラー群
	 * @return ログイン画面のHTML
	 */
	@RequestMapping("/registorAdmin")
	public String registorAdmin(@Validated InsertAdministratorForm insertAdministratorForm, BindingResult result) {

		if (result.hasErrors()) {
			return "administrator/insert";
		}
		Administractor administractor = new Administractor();
		BeanUtils.copyProperties(insertAdministratorForm, administractor);
		administractor = service.registerAdmin(administractor);

		return "administrator/login";
	}

	/**
	 * ログイン処理を制御する.
	 * 
	 * @param loginForm ログイン時に使用するフォーム
	 * @param result    入力値チェックのエラー軍
	 * @param model     失敗時に入れるリクエストスコープ
	 * @return 成功すれば従業員一覧画面、失敗すれば再度ログイン画面
	 */
	@RequestMapping("/loginAdmin")
	public String loginAdmin(@Validated LoginForm loginForm, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "administrator/login";
		}

		Administractor administractor = service.loginAdmin(loginForm.getMailAddress(), loginForm.getPassword());

		if (administractor == null) {
			// アカウント情報が間違っている
			model.addAttribute("failure", "メールアドレスまたはパスワードが間違っています");
			return "administrator/login";
		}

		session.setAttribute("loginAcount", administractor);
		session.setAttribute("administratorName", administractor.getName());

		return "forward:/employee";

	}

	/**
	 * 管理者アカウントをセッションから削除する（ログアウト）.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logoutAdmin")
	public String logoutAdmin() {
		session.removeAttribute("loginAcount");
		return "forward:/admin";
	}

	/**
	 * 管理者情報の編集画面に遷移する.
	 * 
	 * @param model リクエストスコープ
	 * @return 管理者情報の編集画面
	 */
	@RequestMapping("/toAdminEdit")
	public String toAdminEdit(Model model) {
		UpdateAdminForm updateAdminForm = new UpdateAdminForm();
		Administractor administractor = (Administractor) session.getAttribute("loginAcount");
		updateAdminForm.setId(administractor.getId());
		updateAdminForm.setName(administractor.getName());
		updateAdminForm.setMailAddress(administractor.getMailAddress());
		model.addAttribute("updateAdminForm", updateAdminForm);
		return "employee/adminEdit";
	}

	/**
	 * 管理者情報を更新する.
	 * 
	 * @param updateAdminForm 管理者情報の更新の際に利用するフォーム
	 * @param result          入力値チェックのエラー群
	 * @param model           リクエストスコープ
	 * @return 失敗すれば管理者編集画面、成功すれば従業員一覧画面
	 */
	@RequestMapping("/updateAdmin")
	public String updateAdmin(@Validated UpdateAdminForm updateAdminForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "employee/adminEdit";
		}
		Administractor nowAdministractor = (Administractor) session.getAttribute("loginAcount");
		if (!nowAdministractor.getPassword().equals(updateAdminForm.getPastPassword())) {
			model.addAttribute("failure", "パスワードが間違っています");
			return "employee/adminEdit";
		}
		if (!updateAdminForm.getUpdatePassword().equals(updateAdminForm.getCheckUpdatePassword())) {
			model.addAttribute("checkFailure", "確認用パスワードと一致しませんでした");
			return "employee/adminEdit";
		}

		Administractor administractor = new Administractor();
		administractor.setId(updateAdminForm.getId());
		administractor.setName(updateAdminForm.getName());
		administractor.setMailAddress(updateAdminForm.getMailAddress());
		administractor.setPassword(updateAdminForm.getUpdatePassword());
		service.update(administractor);

		session.setAttribute("loginAcount", administractor);

		return "redirect:/employee";
	}

}
