package com.hk.frame.dao.query2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

public class HkDaoSupport2 extends SimpleJdbcDaoSupport {

	public int[] batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
		try {
			return this.getJdbcTemplate().batchUpdate(sql, bpss);
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected Connection getCurrentConnection() {
		Connection con = this.getConnection();
		return con;
	}

	// protected String getLastChar(Number id) {
	// String ss = id + "";
	// if (ss.equals("0")) {
	// throw new IllegalArgumentException("Id is 0");
	// }
	// int len = ss.length();
	// if (len == 0) {
	// throw new IllegalArgumentException("Id is null");
	// }
	// if (len > 1) {
	// ss = ss.substring(ss.length() - 1, ss.length());
	// }
	// try {
	// Long.parseLong(ss);
	// }
	// catch (NumberFormatException e) {
	// throw new IllegalArgumentException("Id is less than 0");
	// }
	// return ss;
	// }
	//
	// protected String getModTableName(String tableName, Number id) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(tableName);
	// sb.append(this.getLastChar(id));
	// return sb.toString();
	// }
	public Number insert(String sql, Object[] values) {
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
				return (Number) rs.getObject(1);
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

	public <T> List<T> query(String sql, int begin, int size, RowMapper<T> rm,
			Object[] values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = this.getCurrentConnection();
		try {
			if (begin >= 0 && size > 0) {
				ps = con.prepareStatement(sql + " limit " + begin + "," + size);
			}
			else {
				ps = con.prepareStatement(sql);
			}
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
			}
			rs = ps.executeQuery();
			int rowNum = 0;
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(rm.mapRow(rs, rowNum++));
			}
			return list;
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

	public Number queryForNumber(String sql, Object[] values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = this.getCurrentConnection();
		try {
			ps = con.prepareStatement(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
			}
			rs = ps.executeQuery();
			int size = 0;
			Number res = 0;
			while (rs.next()) {
				res = (Number) rs.getObject(1);
				size++;
			}
			if (size == 0) {
				return res;
			}
			if (size > 1) {
				throw new IncorrectResultSizeDataAccessException(1, size);
			}
			return res;
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

	public <T> T queryForObject(String sql, RowMapper<T> rm, Object[] values) {
		List<T> list = this.query(sql, 0, 1, rm, values);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public int update(String sql, Object[] values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = this.getCurrentConnection();
		try {
			ps = con.prepareStatement(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
			}
			int rows = ps.executeUpdate();
			return rows;
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
}