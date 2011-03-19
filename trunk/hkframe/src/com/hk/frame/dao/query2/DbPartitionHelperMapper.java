package com.hk.frame.dao.query2;

import java.util.Map;

public interface DbPartitionHelperMapper<T> {

	Map<String, Object> getCtxMap(ObjectSqlInfo<T> objectSqlInfo, T t);
}
