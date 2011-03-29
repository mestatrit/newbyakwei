package com.hk.frame.dao.query2;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * mysql处理作为基础类，以此为扩展可以扩展到db2 oracle等
 * 
 * @author akwei
 */
public class MysqlDaoSupport extends BaseDaoSupport implements DaoSupport,
		DaoIdentifier {

	@Override
	public String getIdentifier() {
		return DaoIdentifier.IDENTIFIER_MYSQL;
	}

	@Override
	public <T> List<T> query(String sql, int begin, int size, RowMapper<T> rm,
			Object[] values) {
		if (begin >= 0 && size > 0) {
			return this.query(sql + " limit " + begin + "," + size, rm, values);
		}
		return this.query(sql, rm, values);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object[] values) {
		List<T> list = this.query(sql, 0, 1, rm, values);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}