package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sample.domain.Administractor;
import jp.co.sample.repository.AdministractorRepository;

/**
 * 管理者関連機能の業務処理を行うクラス.
 * 
 * @author takahiro.okuma
 *
 */
@Service
public class AdministratorService {

	@Autowired
	private AdministractorRepository repository;

	/**
	 * 管理者を新規登録する.
	 * 
	 * @param administractor 登録したい管理者ドメイン
	 * @return 自動採番されたIDが入った管理者ドメイン
	 */
	public Administractor registerAdmin(Administractor administractor) {
		Administractor administractorInId = repository.save(administractor);
		return administractorInId;
	}

}
