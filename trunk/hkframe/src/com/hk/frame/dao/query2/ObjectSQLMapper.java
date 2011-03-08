package com.hk.frame.dao.query2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 废弃
 * 
 * @author fire9
 * @param <T>
 */
@Deprecated
public interface ObjectSQLMapper<T> {

	Object[] getParamsForInsert(List<String> columns, T t) throws SQLException;

	Object[] getParamsForUpdate(List<String> columns, T t) throws SQLException;

	T mapRow(ResultSet rs, int i) throws SQLException;
}
