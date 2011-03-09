package com.hk.frame.dao.query2.mapper;

import java.lang.reflect.Field;
import java.util.List;

import com.hk.frame.dao.query2.SqlUpdateMqpper;

public class TestUserSqlUpdateMapper implements SqlUpdateMqpper<TestUser> {

	@Override
	public Object getIdParam(Field idField, TestUser t) {
		return t.getUserid();
	}

	@Override
	public Object[] getParamsForInsert(List<Field> fieldList, TestUser t) {
		Object[] objs = new Object[] { t.getUserid(), t.getNick(),
				t.getCreatetime() };
		return objs;
	}

	@Override
	public Object[] getParamsForUpdate(List<Field> fieldList, Field idField,
			TestUser t) {
		Object[] objs = new Object[] { t.getNick(), t.getCreatetime(),
				t.getUserid() };
		return objs;
	}
}
