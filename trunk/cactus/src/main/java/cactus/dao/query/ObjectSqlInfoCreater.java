package cactus.dao.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 初始化类与数据表的对应关系配置信息，存储类与数据表的映射对象的数据
 * 
 * @author akwei
 */
public class ObjectSqlInfoCreater {

	/**
	 * class名称为key
	 */
	private final Map<String, ObjectSqlInfo<?>> objectSqlInfoMap = new HashMap<String, ObjectSqlInfo<?>>();

	/**
	 * 类名与分析类的名称组合。格式为：className;helperClassName;
	 */
	private List<String> infos;

	private List<TableCnf> tableCnfList;

	public void setTableCnfList(List<TableCnf> tableCnfList) {
		this.tableCnfList = tableCnfList;
	}

	public void setInfos(List<String> infos) throws ClassNotFoundException {
		this.infos = infos;
		this.afterPropertiesSet();
	}

	public List<String> getInfos() {
		return infos;
	}

	@SuppressWarnings("unchecked")
	public <T> ObjectSqlInfo<T> getObjectSqlInfo(Class<T> clazz) {
		ObjectSqlInfo<T> o = (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz
				.getName());
		if (o == null) {
			throw new RuntimeException("no ObjectSqlInfo for [ "
					+ clazz.getName() + " ]");
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		ObjectSqlInfo<T> o = (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz
				.getName());
		if (o != null) {
			return o.getRowMapper();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T, D, E> void afterPropertiesSet() throws ClassNotFoundException {
		ObjectSqlInfo<E> objectSqlInfo = null;
		for (TableCnf cnf : this.tableCnfList) {
			objectSqlInfo = new ObjectSqlInfo(cnf);
			objectSqlInfoMap.put(cnf.getClassName(), objectSqlInfo);
		}
	}
}