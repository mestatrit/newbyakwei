package demo.svr.dao;

import org.springframework.beans.factory.annotation.Autowired;

import cactus.dao.query.BaseParam;
import cactus.dao.query.HkObjQuery;
import cactus.dao.query.QueryParam;
import cactus.dao.query.UpdateParam;

public abstract class BaseDao<T> {

	@Autowired
	private HkObjQuery hkObjQuery;

	public abstract String getKey();

	public abstract Class<T> getClazz();

	public BaseParam createBaseParam(Object value) {
		return hkObjQuery.createBaseParam(getKey(), value);
	}

	public QueryParam createQueryParam(Object value) {
		return hkObjQuery.createQueryParam(getKey(), value);
	}

	public UpdateParam createUpdateParam(Object value) {
		return hkObjQuery.createUpdateParam(getKey(), value);
	}
}
