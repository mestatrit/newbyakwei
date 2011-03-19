package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.datasource.DataSourceStatus;

/**
 * 对应多种数据库的daoSupport切换使用。数据源必须表明前缀，这样可与daosupport对应
 * 
 * @author akwei
 */
public class MultiDaoSupport implements DaoSupport {

	/**
	 * 配置的多数据库的daosupport
	 */
	private final Map<String, DaoSupport> daoSupportMap = new HashMap<String, DaoSupport>();

	/**
	 * 配置的多数据库的daosupport集合
	 */
	private List<DaoSupport> daoSupportList;

	/**
	 * 把实现DaoIdentifier 接口的类装入map中，便于根据标识获取相应数据库类型的daosupport
	 * 
	 * @param daoSupportList
	 */
	public void setDaoSupportList(List<DaoSupport> daoSupportList) {
		this.daoSupportList = daoSupportList;
		for (DaoSupport daoSupport : daoSupportList) {
			if (daoSupport instanceof DaoIdentifier) {
				DaoIdentifier id = (DaoIdentifier) daoSupport;
				String identifier = id.getIdentifier();
				this.daoSupportMap.put(identifier, daoSupport);
			}
		}
	}

	public String getCurrentDaoSupportIdentifier() {
		String dsName = DataSourceStatus.getCurrentDsName();
		int idx = dsName.indexOf('_');
		if (idx == -1) {
			return null;
		}
		return dsName.substring(0, idx);
	}

	/**
	 * 根据当前环境设定的数据源特征返回相应的daosupport
	 * 
	 * @return
	 */
	private DaoSupport getDaoSupport() {
		String identifier = this.getCurrentDaoSupportIdentifier();
		if (identifier == null) {
			return this.daoSupportList.get(0);
		}
		return this.daoSupportMap.get(identifier);
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