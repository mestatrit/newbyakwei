package com.hk.frame.dao.query2;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * 操作数据的工具类
 * 
 * @author akwei
 */
public class HkQuery {

	// private static Map<String, String> sqlCacheMap = new HashMap<String,
	// String>();
	/**
	 * 数据库操作类
	 */
	private HkDaoSupport2 hkDaoSupport2;

	public void setHkDaoSupport2(HkDaoSupport2 hkDaoSupport2) {
		this.hkDaoSupport2 = hkDaoSupport2;
	}

	private String buildSelectCount(String columns) {
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

	private String buildSelectColumns(PartitionTableInfo[] partitionTableInfos,
			String[][] columns) {
		StringBuilder sb = new StringBuilder("select ");
		int columns_len = 0;
		for (int i = 0; i < columns.length; i++) {
			columns_len += columns[i].length;
		}
		int last_columns_idx = columns.length;
		for (int i = 0; i < partitionTableInfos.length; i++) {
			for (int k = 0; k < columns[i].length; k++) {
				sb.append(partitionTableInfos[i].getTableName()).append(".")
						.append(columns[i][k]);
				if (i == partitionTableInfos.length - 1
						&& k == last_columns_idx) {
					sb.append(" ");
				}
				else {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	private String buildInsert(PartitionTableInfo partitionTableInfo,
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

	private String buildUpdate(PartitionTableInfo partitionTableInfo,
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

	private String buildDelete(PartitionTableInfo partitionTableInfo) {
		return "delete from " + partitionTableInfo.getTableName();
	}

	/**
	 * 创建真正的where orderby sql语句,把表别名的占位符进行替换
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param whereAndOrder
	 *            未被替换的 where 与 order by sql
	 * @return
	 */
	private String buildWhere(PartitionTableInfo[] partitionTableInfos,
			String whereAndOrder) {
		String _whereAndOrder = whereAndOrder;
		int sum = partitionTableInfos.length;
		for (int i = 1; i <= sum; i++) {
			_whereAndOrder = _whereAndOrder.replaceAll(i + "\\.",
					partitionTableInfos[i].getTableName());
		}
		return _whereAndOrder;
	}

	private String buildOrder(PartitionTableInfo[] partitionTableInfos,
			String order) {
		String _order = order;
		int sum = partitionTableInfos.length;
		for (int i = 1; i <= sum; i++) {
			_order = _order.replaceAll(i + "\\.", partitionTableInfos[i]
					.getTableName());
		}
		return _order;
	}

	private String buildFrom(PartitionTableInfo[] partitionTableInfos) {
		StringBuilder sb = new StringBuilder(" from ");
		for (int i = 0; i < partitionTableInfos.length; i++) {
			sb.append(partitionTableInfos[i].getTableName());
			if (i < partitionTableInfos.length - 1) {
				sb.append(",");
			}
			else {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * 根据条件获得统计数字.如果是多表关联查询，表的顺序为partitionTableInfo中的顺序。第一张表别名代号为1，第二张表别名代号为2,
	 * 依次类推<br/>
	 * 
	 * @param partitionTableInfos
	 *            多个对象表示进行表关联的查询<br/>
	 * @param where
	 *            where sql表达式,没有可为null
	 * @param params
	 *            没有可为null
	 * @return
	 */
	public int count(PartitionTableInfo[] partitionTableInfos, String where,
			Object[] params) {
		StringBuilder sb = new StringBuilder(this.buildSelectCount("*"));
		sb.append(" where ");
		sb.append(this.buildWhere(partitionTableInfos, where));
		return this.hkDaoSupport2.queryForNumber(sb.toString(), params)
				.intValue();
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
	public <T> List<T> list(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		StringBuilder sb = new StringBuilder(this.buildSelectColumns(
				partitionTableInfos, columns));
		sb.append(this.buildFrom(partitionTableInfos));
		if (where != null) {
			sb.append(" where ");
			sb.append(this.buildWhere(partitionTableInfos, where));
		}
		if (order != null) {
			sb.append(" order by ");
			sb.append(this.buildOrder(partitionTableInfos, order));
		}
		return this.hkDaoSupport2.query(sb.toString(), begin, size, mapper,
				params);
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
	public <T> T object(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			RowMapper<T> mapper) {
		StringBuilder sb = new StringBuilder(this.buildSelectColumns(
				partitionTableInfos, columns));
		sb.append(this.buildFrom(partitionTableInfos));
		if (where != null) {
			sb.append(" where ");
			sb.append(this.buildWhere(partitionTableInfos, where));
		}
		if (order != null) {
			sb.append(" order by ");
			sb.append(this.buildOrder(partitionTableInfos, order));
		}
		return this.hkDaoSupport2.queryForObject(sb.toString(), mapper, params);
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
	public Number insert(PartitionTableInfo partitionTableInfo,
			String[] columns, Object[] params) {
		StringBuilder sb = new StringBuilder(this.buildInsert(
				partitionTableInfo, columns));
		return this.hkDaoSupport2.insertObject(sb.toString(), params);
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
		StringBuilder sb = new StringBuilder(this.buildUpdate(
				partitionTableInfo, columns));
		if (where != null) {
			sb.append(" where ");
			sb.append(this.buildWhere(
					new PartitionTableInfo[] { partitionTableInfo }, where));
		}
		return this.hkDaoSupport2.update(sb.toString(), params);
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
		StringBuilder sb = new StringBuilder(this
				.buildDelete(partitionTableInfo));
		if (where != null) {
			sb.append(" where ");
			sb.append(this.buildWhere(
					new PartitionTableInfo[] { partitionTableInfo }, where));
		}
		return this.hkDaoSupport2.update(sb.toString(), params);
	}
}
