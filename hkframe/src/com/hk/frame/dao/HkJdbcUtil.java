package com.hk.frame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class HkJdbcUtil {

	public static Number insertObject(DataSource dataSource, String sql,
			Object... values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = HkJdbcUtil.getConnection(dataSource);
		try {
			ps = con.prepareStatement(sql);
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
			HkJdbcUtil.closeStatement(ps);
			ps = null;
			HkJdbcUtil.releaseConnection(con, dataSource);
			con = null;
			throw new RuntimeException(e);
		}
		finally {
			HkJdbcUtil.closeResultSet(rs);
			HkJdbcUtil.closeStatement(ps);
			HkJdbcUtil.releaseConnection(con, dataSource);
		}
	}

	public static <T> List<T> query(DataSource dataSource, String sql,
			int begin, int size, ParameterizedRowMapper<T> rm, Object... values) {
		return query(dataSource, sql + " limit " + begin + "," + size, rm,
				values);
	}

	public static <T> List<T> query(DataSource dataSource, String sql,
			ParameterizedRowMapper<T> rm, Object... values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = getConnection(dataSource);
		try {
			ps = con.prepareStatement(sql);
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
			HkJdbcUtil.closeStatement(ps);
			ps = null;
			HkJdbcUtil.releaseConnection(con, dataSource);
			con = null;
			throw new RuntimeException(e);
		}
		finally {
			HkJdbcUtil.closeResultSet(rs);
			HkJdbcUtil.closeStatement(ps);
			HkJdbcUtil.releaseConnection(con, dataSource);
		}
	}

	public static Number queryForNumber(DataSource dataSource, String sql,
			Object... values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = HkJdbcUtil.getConnection(dataSource);
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
			HkJdbcUtil.closeStatement(ps);
			ps = null;
			HkJdbcUtil.releaseConnection(con, dataSource);
			con = null;
			throw new RuntimeException(e);
		}
		finally {
			HkJdbcUtil.closeResultSet(rs);
			HkJdbcUtil.closeStatement(ps);
			HkJdbcUtil.releaseConnection(con, dataSource);
		}
	}

	public static <T> T queryForObject(DataSource dataSource, String sql,
			ParameterizedRowMapper<T> rm, Object... values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = HkJdbcUtil.getConnection(dataSource);
		try {
			ps = con.prepareStatement(sql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
			}
			rs = ps.executeQuery();
			int rowNum = 0;
			T t = null;
			while (rs.next()) {
				t = rm.mapRow(rs, rowNum++);
			}
			if (rowNum == 0) {
				return null;
			}
			if (rowNum > 1) {
				throw new IncorrectResultSizeDataAccessException(1, rowNum);
			}
			return t;
		}
		catch (SQLException e) {
			HkJdbcUtil.closeStatement(ps);
			ps = null;
			HkJdbcUtil.releaseConnection(con, dataSource);
			con = null;
			throw new RuntimeException(e);
		}
		finally {
			HkJdbcUtil.closeResultSet(rs);
			HkJdbcUtil.closeStatement(ps);
			HkJdbcUtil.releaseConnection(con, dataSource);
		}
	}

	public static int update(DataSource dataSource, String sql,
			Object... values) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = HkJdbcUtil.getConnection(dataSource);
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
			HkJdbcUtil.closeStatement(ps);
			ps = null;
			HkJdbcUtil.releaseConnection(con, dataSource);
			con = null;
			throw new RuntimeException(e);
		}
		finally {
			HkJdbcUtil.closeResultSet(rs);
			HkJdbcUtil.closeStatement(ps);
			HkJdbcUtil.releaseConnection(con, dataSource);
		}
	}

	/**
	 * @param dataSource
	 */
	public static Connection getConnection(DataSource dataSource) {
		return null;
	}

	/**
	 * @param con
	 * @param dataSource
	 */
	public static void releaseConnection(Connection con, DataSource dataSource) {
	}

	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}