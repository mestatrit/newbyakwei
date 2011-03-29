package com.hk.frame.dao.query2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

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
	public Object insert(String sql, Object[] values) {
		this.log("insert sql [ " + sql + " ]");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = this.getCurrentConnection();
		try {
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (values != null) {
				int i = 1;
				for (Object value : values) {
					ps.setObject(i++, value);
				}
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getObject(1);
			}
			return 0;
		}
		catch (SQLException e) {
			JdbcUtils.closeStatement(ps);
			ps = null;
			DataSourceUtils.releaseConnection(con, getDataSource());
			con = null;
			e.printStackTrace();
			throw getExceptionTranslator().translate("StatementCallback", sql,
					e);
		}
		finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
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