package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administractor;

/**
 * 管理者テーブルを操作するリポジトリ.
 * 
 * @author takahiro.okuma
 *
 */
@Repository
public class AdministractorRepository {

	/** SQLの発行に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 管理者テーブル名 */
	private static final String TABLE_ADMINISTRACTORS = "administrators";

	/** 管理者テーブルの情報をドメインに変換するROW_MAPPER */
	private RowMapper<Administractor> ADMINISTRACTOR_ROW_MAPPER = (rs, i) -> {

		Administractor administractor = new Administractor();
		administractor.setId(rs.getInt("id"));
		administractor.setName(rs.getString("name"));
		administractor.setMailAddress(rs.getString("mail_address"));
		administractor.setPassword(rs.getString("password"));

		return administractor;
	};

	/**
	 * 管理者DBに新規登録.
	 * 
	 * @param administractor 登録したい管理者ドメイン
	 * @return ID採番済の管理者ドメイン
	 */
	public Administractor save(Administractor administractor) {

		String sql = "INSERT INTO " + TABLE_ADMINISTRACTORS + " (name,mail_address,password) "
				+ " VALUES (:name,:mailAddress,:password);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administractor);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(sql, param, keyHolder, keyColumnNames);
		administractor.setId(keyHolder.getKey().intValue());

		return administractor;

	}

	/**
	 * メールアドレスとパスワードがDBに存在するか確認する.
	 * 
	 * @param inMailAddress メールアドレス
	 * @param inPassword    パスワード
	 * @return 存在すればそのアカウント情報、存在しなければnull
	 */
	public Administractor findByNameAndPassword(String mailAddress, String password) {

		String sql = "SELECT id,name,mail_address,password FROM " + TABLE_ADMINISTRACTORS
				+ " WHERE mail_address=:mailAddress AND password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);

		try {
			Administractor administractor = template.queryForObject(sql, param, ADMINISTRACTOR_ROW_MAPPER);
			return administractor;

		} catch (Exception e) {
			return null;
		}

	}

}
