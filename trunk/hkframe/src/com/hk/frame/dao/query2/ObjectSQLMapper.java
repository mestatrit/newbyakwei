package com.hk.frame.dao.query2;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectSQLMapper<T> {

	Object[] getParamsForInsert(T t) throws SQLException;

	Object[] getParamsForUpdate(T t) throws SQLException;

	T mapRow(ResultSet rs, int i) throws SQLException;
}
