package demo.svr.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.dao.query2.BaseParam;
import com.hk.frame.dao.query2.HkObjQuery;
import com.hk.frame.dao.query2.QueryParam;
import com.hk.frame.dao.query2.UpdateParam;

public abstract class BaseDao<T> {

	@Autowired
	private HkObjQuery hkObjQuery;

	public abstract String getKey();

	public abstract Class<T> getClazz();

	public BaseParam createBaseParam(Object value) {
		return hkObjQuery.createBaseParam(getClazz(), getKey(), value);
	}

	public QueryParam createQueryParam(Object value) {
		return hkObjQuery.createQueryParam(getClazz(), getKey(), value);
	}

	public UpdateParam createUpdateParam(Object value) {
		return hkObjQuery.createUpdateParam(getClazz(), getKey(), value);
	}
}
