package com.hk.frame.dao.query2;

import java.util.List;

/**
 * 操作数据的工具类
 * 
 * @author akwei
 */
public class HkQuery extends HkDaoSupport2 {

	// private static Map<String, String> sqlCacheMap = new HashMap<String,
	// String>();
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

	protected String buildSelectColumns(
			PartitionTableInfo[] partitionTableInfos, String[][] columns) {
		StringBuilder sb = new StringBuilder("select ");
		int k1 = 0;
		int k2 = 0;
		for (int i = 0; i < columns.length; i++) {
			k1 = i;
			k2 = columns[i].length - 1;
		}
		for (int i = 0; i < partitionTableInfos.length; i++) {
			for (int k = 0; k < columns[i].length; k++) {
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

	protected String buildInsert(PartitionTableInfo partitionTableInfo,
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

	protected String buildUpdate(PartitionTableInfo partitionTableInfo,
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

	protected String buildDelete(PartitionTableInfo partitionTableInfo) {
		return "delete from " + partitionTableInfo.getTableName();
	}

	// /**
	// * 创建真正的where orderby sql语句,把表别名的占位符进行替换
	// *
	// * @param partitionTableInfos
	// * 表信息
	// * @param whereAndOrder
	// * 未被替换的 where 与 order by sql
	// * @return
	// */
	// protected String buildWhere(PartitionTableInfo[] partitionTableInfos,
	// String whereAndOrder) {
	// String _whereAndOrder = whereAndOrder;
	// for (int i = 0; i < partitionTableInfos.length; i++) {
	// _whereAndOrder = _whereAndOrder.replaceAll((i + 1) + "\\.",
	// partitionTableInfos[i].getAliasName() + ".");
	// }
	// return _whereAndOrder;
	// }
	// protected String buildOrder(PartitionTableInfo[] partitionTableInfos,
	// String order) {
	// String _order = order;
	// for (int i = 0; i < partitionTableInfos.length; i++) {
	// _order = _order.replaceAll((i + 1) + "\\.", partitionTableInfos[i]
	// .getAliasName()
	// + ".");
	// }
	// return _order;
	// }
	protected String buildFrom(PartitionTableInfo[] partitionTableInfos) {
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

	protected String getCountSQL(PartitionTableInfo[] partitionTableInfos,
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
		return this.queryForNumber(
				this.getCountSQL(partitionTableInfos, where), params)
				.intValue();
	}

	protected String getListSQL(PartitionTableInfo[] partitionTableInfos,
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
			int begin, int size, ObjectSQLMapper<T> mapper) {
		return this.query(this.getListSQL(partitionTableInfos, columns, where,
				order), begin, size, mapper, params);
	}

	protected String getObjectSQL(PartitionTableInfo[] partitionTableInfos,
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
			ObjectSQLMapper<T> mapper) {
		return this.queryForObject(this.getObjectSQL(partitionTableInfos,
				columns, where, order), mapper, params);
	}

	protected String getInsertSQL(PartitionTableInfo partitionTableInfo,
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
	public Number insert(PartitionTableInfo partitionTableInfo,
			String[] columns, Object[] params) {
		return this.insert(this.getInsertSQL(partitionTableInfo, columns),
				params);
	}

	protected String getUpdateSQL(PartitionTableInfo partitionTableInfo,
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
		return this.update(this
				.getUpdateSQL(partitionTableInfo, columns, where), params);
	}

	protected String getDeleteSQL(PartitionTableInfo partitionTableInfo,
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
		return this
				.update(this.getDeleteSQL(partitionTableInfo, where), params);
	}
}
