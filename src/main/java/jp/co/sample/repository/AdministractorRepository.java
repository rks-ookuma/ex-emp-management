package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administractor;

@Repository
public class AdministractorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 管理者テーブル名 */
	private static final String TABLE_ADMINISTRACTOR = "administrators";

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

		String sql = "INSERT INTO " + TABLE_ADMINISTRACTOR + " (name,mail_address,password) "
				+ " VALUES (:name,:mailAddress,:password);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administractor);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(sql, param, keyHolder, keyColumnNames);
		administractor.setId(keyHolder.getKey().intValue());

		return administractor;

	}

}
