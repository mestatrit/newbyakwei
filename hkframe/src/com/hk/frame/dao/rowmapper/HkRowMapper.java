package com.hk.frame.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;

public abstract class HkRowMapper<T> implements RowMapper<T> {

	public abstract Class<T> getMapperClass();
}