package cactus.dao.query;

import java.util.Map;

/**
 * 数据分区分析器，通过此分析器可以分析表所在的具体的数据库与数据表名称
 * 
 * @author akwei
 */
public abstract class DbPartitionHelper {

	protected String get01(Number id) {
		if (id.equals(0)) {
			throw new IllegalArgumentException("Id is 0");
		}
		long v = id.longValue();
		long res = v % 2;
		if (res == 0) {
			return "0";
		}
		return "1";
	}

	protected String getLastChar(Number id) {
		if (id.equals(0)) {
			throw new IllegalArgumentException("Id is 0");
		}
		String ss = String.valueOf(id);
		int len = ss.length();
		if (len == 1) {
			return ss;
		}
		ss = ss.substring(ss.length() - 1, ss.length());
		return ss;
	}

	/**
	 * 根据内容进行分析，创建表的分区信息
	 * 
	 * @param name
	 *            逻辑表名称，也将会成为表的别名
	 * @param ctxMap
	 *            上下文信息存储
	 * @return
	 */
	public abstract PartitionTableInfo parse(String name,
			Map<String, Object> ctxMap);
}
