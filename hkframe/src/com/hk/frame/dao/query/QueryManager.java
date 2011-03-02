package com.hk.frame.dao.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.dao.HkDaoSupport;
import com.hk.frame.dao.query.impl.QueryImpl3;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.rowmapper.RowMapperUtil;
import com.hk.frame.util.DataUtil;

/**
 * 负责创建Query,配置rowMapper
 * 
 * @author yuanwei
 */
public class QueryManager {

	private boolean enableAsm;

	private Map<String, Cache> cacheMap;

	private HkDaoSupport hkDaoSupport;

	private Map<String, Partition> tablePartitionConfig;

	/**
	 * 表名称与表路由规则的对应
	 */
	private final Map<String, Partition> tableNamePartitionMap = new HashMap<String, Partition>();

	/**
	 * 类名称与sql信息类的key value对应
	 */
	private final Map<String, ObjectSqlData> objectSqlDataMap = new HashMap<String, ObjectSqlData>();

	private final Map<String, RowMapper<?>> rowMapperMap = new HashMap<String, RowMapper<?>>();

	private final Log logger = LogFactory.getLog(QueryManager.class);

	/**
	 * 默认的rowMapper路径，启动的时候，会从此路径下加载rowMapper类
	 */
	private String rowMapperPath;

	public void setRowMapperPath(String rowMapperPath) {
		this.rowMapperPath = rowMapperPath;
	}

	public void setTablePartitionConfig(
			Map<String, Partition> tablePartitionConfig) throws Exception {
		this.tablePartitionConfig = tablePartitionConfig;
		this.iniTobjectSqlDataMap();
	}

	/**
	 * 根据类名称返回类所对应的mapper类,例如:User -> UserMapper
	 * 
	 * @param <T>
	 * @param classLoader
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> RowMapper<T> getRowMapperFromClassName(ClassLoader classLoader,
			String className, ObjectSqlData objectSqlData)
			throws ClassNotFoundException {
		Class<?> mapperClazz = null;
		if (DataUtil.isEmpty(this.rowMapperPath)) {
			mapperClazz = RowMapperUtil.createRowMapperClass(objectSqlData);
			logger.info("创建ASM 动态Mapper [ " + mapperClazz.getName() + " ]");
		}
		else {
			try {
				mapperClazz = classLoader.loadClass(this.rowMapperPath
						+ "."
						+ className.substring(className.lastIndexOf(".") + 1,
								className.length()) + "Mapper");
			}
			catch (ClassNotFoundException e1) {
				if (this.enableAsm) {
					mapperClazz = RowMapperUtil
							.createRowMapperClass(objectSqlData);
					logger.info("创建ASM 动态Mapper [ " + mapperClazz.getName()
							+ " ]");
				}
				if (mapperClazz == null) {
					return null;
				}
			}
		}
		try {
			Object obj = mapperClazz.getConstructor().newInstance();
			return (RowMapper<T>) obj;
		}
		catch (Exception e) {
			throw new RuntimeException("mapper [ " + mapperClazz.getName()
					+ " ] no constructor was found");
		}
	}

	private void iniTobjectSqlDataMap() throws Exception {
		this.rowMapperMap.put(Long.class.getName(), longMapper);
		this.rowMapperMap.put(Integer.class.getName(), intMapper);
		this.rowMapperMap.put(String.class.getName(), stringMapper);
		// ClassLoader classLoader = QueryManager.class.getClassLoader();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		Set<String> set = tablePartitionConfig.keySet();
		Class<?> clazz = null;
		ObjectSqlData objectSqlData = null;
		for (String className : set) {
			clazz = classLoader.loadClass(className);
			objectSqlData = new ObjectSqlData(clazz);
			objectSqlDataMap.put(className, objectSqlData);
			tableNamePartitionMap.put(objectSqlData.getTableName(),
					tablePartitionConfig.get(className));
			try {
				RowMapper<?> mapper = this.getRowMapperFromClassName(
						classLoader, className, objectSqlData);
				if (mapper != null) {
					this.rowMapperMap.put(className, mapper);
				}
			}
			// catch (ClassNotFoundException e) {
			// logger.warn("mapper for [ " + className + " ] was not found");
			// }
			catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		return (RowMapper<T>) this.rowMapperMap.get(clazz.getName());
	}

	// public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
	// RowMapper<T> mapper = (RowMapper<T>) this.rowMapperMap.get(clazz
	// .getName());
	// if (mapper == null) {
	// throw new RuntimeException("mapper for [ " + clazz.getName()
	// + " ] was not found");
	// }
	// return mapper;
	// }
	public Partition getPartitionFromTableName(String tableName) {
		return tableNamePartitionMap.get(tableName);
	}

	/**
	 * @param className
	 *            类名称
	 * @return
	 */
	public ObjectSqlData getObjectSqlData(String className) {
		if (!objectSqlDataMap.containsKey(className)) {
			throw new RuntimeException("no table config [ " + className + " ]");
		}
		return objectSqlDataMap.get(className);
	}

	public Map<String, Partition> getTablePartitionConfig() {
		return tablePartitionConfig;
	}

	public void setCacheMap(Map<String, Cache> cacheMap) {
		this.cacheMap = cacheMap;
	}

	public Map<String, Cache> getCacheMap() {
		return cacheMap;
	}

	public Cache getCacheFromCacheMap(String key) {
		if (this.cacheMap != null)
			return this.cacheMap.get(key);
		return null;
	}

	public Query createQuery() {
		Query query = new QueryImpl3();
		query.setQueryManager(this);
		return query;
	}

	public void setHkDaoSupport(HkDaoSupport hkDaoSupport) {
		this.hkDaoSupport = hkDaoSupport;
	}

	public HkDaoSupport getHkDaoSupport() {
		return hkDaoSupport;
	}

	private static final RowMapper<Integer> intMapper = new RowMapper<Integer>() {

		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt(1);
		}
	};

	private static final RowMapper<Long> longMapper = new RowMapper<Long>() {

		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getLong(1);
		}
	};

	private static final RowMapper<String> stringMapper = new RowMapper<String>() {

		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(1);
		}
	};

	public void setEnableAsm(boolean enableAsm) {
		this.enableAsm = enableAsm;
	}
}