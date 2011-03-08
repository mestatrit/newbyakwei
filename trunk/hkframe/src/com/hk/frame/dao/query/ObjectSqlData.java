package com.hk.frame.dao.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 把class进行分析，存储class 对应的 sql相关信息
 * 
 * @author akwei
 */
public class ObjectSqlData {

	private Class<?> clazz;

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
	 * 属性名称对应的列名称(列名称小写后成为sql字段名称)filed:column
	 */
	private final Map<String, String> fieldColumnMap = new HashMap<String, String>();

	public ObjectSqlData(Class<?> clazz) {
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

	public String getColumn(String fieldName) {
		return fieldColumnMap.get(fieldName);
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

	public Class<?> getClazz() {
		return clazz;
	}
}
