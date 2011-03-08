package com.hk.frame.dao.query2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 把class进行分析，存储class 对应的 sql相关信息
 * 
 * @author akwei
 */
public class ObjectSqlInfo<T> {

	private RowMapper<T> rowMapper;

	private SqlUpdateMqpper<T> sqlUpdateMqpper;

	private Class<T> clazz;

	/**
	 * sql所有字段的数组
	 */
	private String[] columns;

	/**
	 * sql进行update时，需要更新的字段数组
	 */
	private String[] columnsForUpdate;

	/**
	 * 逻辑表名称
	 */
	private String tableName;

	/**
	 * id字段属性
	 */
	private Field idField;

	/**
	 * id列名
	 */
	private String idColumn;

	/**
	 * 除id字段的其他字段信息集合
	 */
	private final List<Field> fieldList = new ArrayList<Field>();

	/**
	 * 所有字段信息集合
	 */
	private final List<Field> allfieldList = new ArrayList<Field>();

	/**
	 * sql insert 语句中字段部分例如(column1,column2 .....) values(?,?,?...)
	 */
	private String sql_insert_columns;

	/**
	 * sql update 语句中更新字段部分，例如:set column1=?,column2=?......
	 */
	private String sql_update_columns;

	/**
	 * 属性名称对应的列名称(列名称小写后成为sql字段名称)filed:column
	 */
	private final Map<String, String> fieldColumnMap = new HashMap<String, String>();

	/**
	 * 创建对象，初始化数据
	 * 
	 * @param clazz
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public ObjectSqlInfo(Class<T> clazz) throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		this.clazz = clazz;
		Table table = clazz.getAnnotation(Table.class);
		if (table == null) {
			throw new RuntimeException("tableName not set [ " + clazz.getName()
					+ " ]");
		}
		this.tableName = table.name();
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			this.analyze(f);
		}
		// 不建议此写法，建议直接使用@Id
		if (idColumn == null || idColumn.length() == 0) {
			idColumn = table.id();
		}
		this.buildAllColumns();
		this.buildSql_insert_columns();
		this.buildSql_update_columns();
		this.buildMapper();
	}

	private void analyze(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			field.setAccessible(true);
			fieldList.add(field);
			allfieldList.add(field);
			// 设置属性与sql字段的名称对应关系，默认是属性的名称与字段名相等，除非自定义Column中的name
			if (column.value().equals("")) {
				fieldColumnMap.put(field.getName(), field.getName()
						.toLowerCase());
			}
			else {
				fieldColumnMap.put(field.getName(), column.value());
			}
		}
		else {
			// 定义的sql中的 id 字段,id字段也属于Column
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				field.setAccessible(true);
				allfieldList.add(field);
				this.setIdField(field);
				if (id.name().equals("")) {
					this.setIdColumn(field.getName().toLowerCase());
					fieldColumnMap.put(field.getName(), field.getName()
							.toLowerCase());
				}
				else {
					this.setIdColumn(id.name());
					fieldColumnMap.put(field.getName(), id.name());
				}
			}
		}
	}

	/**
	 * 生成sql_insert_columns
	 */
	private void buildSql_insert_columns() {
		StringBuilder sb = new StringBuilder("(");
		StringBuilder sb2 = new StringBuilder(" values(");
		for (Field f : this.allfieldList) {
			sb.append(this.getColumn(f.getName()));
			sb.append(",");
			sb2.append("?,");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb2.length() > 0) {
			sb2.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		sb2.append(")");
		sb.append(sb2);
		this.sql_insert_columns = sb.toString();
	}

	/**
	 * 生成sql_updatre_columns
	 */
	private void buildSql_update_columns() {
		StringBuilder sb = new StringBuilder("set ");
		for (Field f : this.fieldList) {
			sb.append(this.getColumn(f.getName()));
			sb.append("=?,");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		this.sql_update_columns = sb.toString();
	}

	public String getColumn(String fieldName) {
		return fieldColumnMap.get(fieldName);
	}

	@SuppressWarnings("unchecked")
	private void buildMapper() throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class<T> clazz = RowMapperCreater.createRowMapperClass(this);
		Object obj = clazz.getConstructor().newInstance();
		this.rowMapper = (RowMapper<T>) obj;
	}

	private void buildAllColumns() {
		columns = new String[this.allfieldList.size()];
		int i = 0;
		for (Field f : this.allfieldList) {
			columns[i] = this.getColumn(f.getName());
			i++;
		}
		i = 0;
		columnsForUpdate = new String[this.fieldList.size()];
		for (Field f : this.fieldList) {
			columnsForUpdate[i] = this.getColumn(f.getName());
			i++;
		}
	}

	/**
	 * 获得id字段
	 * 
	 * @return
	 */
	public Field getIdField() {
		return idField;
	}

	public void setIdField(Field idField) {
		this.idField = idField;
	}

	/**
	 * 获得数据库对应的id字段
	 * 
	 * @return
	 */
	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	/**
	 * 获得除去id的所有字段
	 * 
	 * @return
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}

	/**
	 * 获得逻辑表名
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获得所有字段
	 * 
	 * @return
	 */
	public List<Field> getAllfieldList() {
		return allfieldList;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public String getSql_insert_columns() {
		return sql_insert_columns;
	}

	public String getSql_update_columns() {
		return sql_update_columns;
	}

	public RowMapper<T> getRowMapper() {
		return rowMapper;
	}

	public SqlUpdateMqpper<T> getSqlUpdateMqpper() {
		return sqlUpdateMqpper;
	}

	/**
	 * 获得sql所有字段
	 * 
	 * @return
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * 获得sqlforupdate的所有字段
	 * 
	 * @return
	 */
	public String[] getColumnsForUpdate() {
		return columnsForUpdate;
	}
}