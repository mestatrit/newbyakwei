package com.hk.frame.dao.query2;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.datasource.DataSourceStatus;

/**
 * 操作数据的工具类。调用daosupport进行数据库处理
 * 
 * @author akwei
 */
public class HkQuery {

	private DaoSupport daoSupport;

	public void setDaoSupport(DaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	public DaoSupport getDaoSupport() {
		return daoSupport;
	}

	/**
	 * 创建 select count sql片段<br/>
	 * 例如：参数为userid,则返回select count(userid)。参数为null，返回select count(*)
	 * 
	 * @param columns
	 *            需要count 的字段，可以为null
	 * @return
	 */
	protected String buildSelectCount(String columns) {
		StringBuilder sb = new StringBuilder("select count(");
		if (columns == null) {
			sb.append(columns);
		}
		else {
			sb.append("*");
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 产生需要查询的列sql片段。<br/>
	 * 例如：查询user表的userid,nick，就会自动添加别名返回 select user.userid,user.nick
	 * 
	 * @param partitionTableInfos
	 * @param columns
	 * @return
	 */
	public String buildSelectColumns(PartitionTableInfo[] partitionTableInfos,
			String[][] columns) {
		StringBuilder sb = new StringBuilder("select ");
		int k1 = 0;
		int k2 = 0;
		for (int i = 0; i < columns.length; i++) {
			k1 = i;
			k2 = columns[i].length - 1;
		}
		for (int i = 0; i < partitionTableInfos.length; i++) {
			for (int k = 0; k < columns[i].length; k++) {
				sb.append(partitionTableInfos[i].getAliasName()).append(".");
				sb.append(columns[i][k]);
				if (i == k1 && k == k2) {
				}
				else {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 产生insert sql
	 * 
	 * @param partitionTableInfo表信息
	 * @param columns
	 *            需要插入的列
	 * @return
	 */
	public String buildInsert(PartitionTableInfo partitionTableInfo,
			String[] columns) {
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(partitionTableInfo.getTableName());
		sb.append("(");
		for (int i = 0; i < columns.length; i++) {
			sb.append(columns[i]);
			if (i != columns.length - 1) {
				sb.append(",");
			}
		}
		sb.append(") values(");
		for (int i = 0; i < columns.length; i++) {
			sb.append("?");
			if (i != columns.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 产生 update sql 片段<br/>
	 * 例如：user表更新update user set userid=?,nick=?
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @param columns
	 *            需要更新的列
	 * @return
	 */
	public String buildUpdate(PartitionTableInfo partitionTableInfo,
			String[] columns) {
		StringBuilder sb = new StringBuilder("update ");
		sb.append(partitionTableInfo.getTableName());
		sb.append(" set ");
		for (int i = 0; i < columns.length; i++) {
			sb.append(columns[i]).append("=?");
			if (i != columns.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 产生delete sql片段<br/>
	 * 例如：delete from user
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @return
	 */
	public String buildDelete(PartitionTableInfo partitionTableInfo) {
		return "delete from " + partitionTableInfo.getTableName();
	}

	/**
	 * 产生 from sql 片段(主要提供给查询使用)。表别名可与表名相同<br/>
	 * 例如from user user。from user0 user,member1 member
	 * 
	 * @param partitionTableInfos
	 * @return
	 */
	public String buildFrom(PartitionTableInfo[] partitionTableInfos) {
		StringBuilder sb = new StringBuilder(" from ");
		for (int i = 0; i < partitionTableInfos.length; i++) {
			sb.append(partitionTableInfos[i].getTableName());
			sb.append(" ");
			sb.append(partitionTableInfos[i].getAliasName());
			if (i < partitionTableInfos.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 产生count sql<br/>
	 * 例如：select count(*) from user。select count(*) from user user,member member
	 * where user.userid=member.userid
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param where
	 *            查询条件
	 * @return sql 语句
	 */
	public String getCountSQL(PartitionTableInfo[] partitionTableInfos,
			String where) {
		StringBuilder sb = new StringBuilder(this.buildSelectCount("*"));
		sb.append(this.buildFrom(partitionTableInfos));
		if (where != null) {
			sb.append(" where ");
			sb.append(where);
		}
		return sb.toString();
	}

	/**
	 * 根据条件获得统计数字
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param where
	 *            where sql表达式,没有可为null
	 * @param params
	 *            没有可为null
	 * @return
	 */
	public int count(PartitionTableInfo[] partitionTableInfos, String where,
			Object[] params) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().queryForNumber(
				this.getCountSQL(partitionTableInfos, where), params)
				.intValue();
	}

	/**
	 * 产生查询集合的sql
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            查询列
	 * @param where
	 *            查询条件，没有可为null
	 * @param order
	 *            排序条件，没有可为null
	 * @return sql语句
	 */
	public String getListSQL(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, String order) {
		StringBuilder sb = new StringBuilder(this.buildSelectColumns(
				partitionTableInfos, columns));
		sb.append(this.buildFrom(partitionTableInfos));
		if (where != null) {
			sb.append(" where ");
			sb.append(where);
		}
		if (order != null) {
			sb.append(" order by ");
			sb.append(order);
		}
		return sb.toString();
	}

	/**
	 * 返回符合条件的数据集合
	 * 
	 * @param <T>
	 *            返回类型
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            返回的表的字段是全名，例如: user.userid
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @param order
	 *            order 表达式(没有order关键字)，没有可为null
	 * @param begin
	 *            记录开始位置，当begin<0时，获取所有数据，不受size限制
	 * @param size
	 *            记录获取的最多数量，当size<=0时，获取所有数据，不受size限制
	 * @param mapper
	 *            返回值的组装对象
	 * @return
	 */
	public <T> List<T> queryList(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().query(
				this.getListSQL(partitionTableInfos, columns, where, order),
				begin, size, mapper, params);
	}

	/**
	 * 产生查询单条记录的sql语句
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            查询的列
	 * @param where
	 *            查询条件
	 * @param order
	 *            排序条件
	 * @return 完整的sql语句
	 */
	public String getObjectSQL(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, String order) {
		StringBuilder sb = new StringBuilder(this.buildSelectColumns(
				partitionTableInfos, columns));
		sb.append(this.buildFrom(partitionTableInfos));
		if (where != null) {
			sb.append(" where ");
			sb.append(where);
		}
		if (order != null) {
			sb.append(" order by ");
			sb.append(order);
		}
		return sb.toString();
	}

	/**
	 * 返回符合条件的单个对象
	 * 
	 * @param <T>
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            要获取的字段
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @param order
	 *            order 表达式(没有order关键字)，没有可为null
	 * @param mapper
	 *            返回值的组装对象
	 * @return
	 */
	public <T> T queryObject(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			RowMapper<T> mapper) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().queryForObject(
				this.getObjectSQL(partitionTableInfos, columns, where, order),
				mapper, params);
	}

	public String getInsertSQL(PartitionTableInfo partitionTableInfo,
			String[] columns) {
		StringBuilder sb = new StringBuilder(this.buildInsert(
				partitionTableInfo, columns));
		return sb.toString();
	}

	/**
	 * 创建数据
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @param columns
	 *            插入的字段
	 * @param params
	 *            字段对应的参数
	 * @return
	 */
	public Object insert(PartitionTableInfo partitionTableInfo,
			String[] columns, Object[] params) {
		DataSourceStatus.setCurrentDsName(partitionTableInfo.getDatabaseName());
		return this.getDaoSupport().insert(
				this.getInsertSQL(partitionTableInfo, columns), params);
	}

	public String getUpdateSQL(PartitionTableInfo partitionTableInfo,
			String[] columns, String where) {
		StringBuilder sb = new StringBuilder(this.buildUpdate(
				partitionTableInfo, columns));
		if (where != null) {
			sb.append(" where ");
			sb.append(where);
		}
		return sb.toString();
	}

	/**
	 * 根据条件更新数据
	 * 
	 * @param partitionTableInfo
	 * @param columns
	 *            需要更新的字段
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @return
	 */
	public int update(PartitionTableInfo partitionTableInfo, String[] columns,
			String where, Object[] params) {
		DataSourceStatus.setCurrentDsName(partitionTableInfo.getDatabaseName());
		return this.getDaoSupport().update(
				this.getUpdateSQL(partitionTableInfo, columns, where), params);
	}

	public String getDeleteSQL(PartitionTableInfo partitionTableInfo,
			String where) {
		StringBuilder sb = new StringBuilder(this
				.buildDelete(partitionTableInfo));
		if (where != null) {
			sb.append(" where ");
			sb.append(where);
		}
		return sb.toString();
	}

	/**
	 * 根据条件删除数据
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @return
	 */
	public int delete(PartitionTableInfo partitionTableInfo, String where,
			Object[] params) {
		DataSourceStatus.setCurrentDsName(partitionTableInfo.getDatabaseName());
		return this.getDaoSupport().update(
				this.getDeleteSQL(partitionTableInfo, where), params);
	}
}
