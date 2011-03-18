package com.hk.frame.dao.query2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.datasource.DataSourceStatus;

/**
 * 对应多种数据库的daoSupport切换使用
 * 
 * @author fire9
 */
public class MultiDaoSupport implements DaoSupport {

	private Map<String, DaoSupport> daoSupportMap;

	private final List<DaoSupport> daoSupportList = new ArrayList<DaoSupport>();

	public void setDaoSupportMap(Map<String, DaoSupport> daoSupportMap) {
		this.daoSupportMap = daoSupportMap;
		Collection<DaoSupport> values = this.daoSupportMap.values();
		daoSupportList.addAll(values);
	}

	private DaoSupport getDaoSupport() {
		String flg = DataSourceStatus.getCurrentDaoSupportFlg();
		if (flg == null) {
			return this.daoSupportList.get(0);
		}
		return this.daoSupportMap.get(flg);
	}

	@Override
	public Object insert(String sql, Object[] values) {
		return this.getDaoSupport().insert(sql, values);
	}

	@Override
	public <T> List<T> query(String sql, int begin, int size, RowMapper<T> rm,
			Object[] values) {
		return this.getDaoSupport().query(sql, begin, size, rm, values);
	}

	@Override
	public Number queryForNumber(String sql, Object[] values) {
		return this.getDaoSupport().queryForNumber(sql, values);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object[] values) {
		return this.getDaoSupport().queryForObject(sql, rm, values);
	}

	@Override
	public int update(String sql, Object[] values) {
		return this.getDaoSupport().update(sql, values);
	}
}