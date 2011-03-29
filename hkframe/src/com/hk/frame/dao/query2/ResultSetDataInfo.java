package com.hk.frame.dao.query2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.dao.annotation.Column;

/**
 * 对于数据查询结果的集合
 * 
 * @author akwei
 * @param <T>
 */
public class ResultSetDataInfo<T> {

	/**
	 * 所有字段的集合
	 */
	private final List<Field> fieldList = new ArrayList<Field>();

	/**
	 * 字段与数据库列对应的map信息
	 */
	private final Map<String, String> fieldColumnMap = new HashMap<String, String>();

	/**
	 * 数据结果映射类
	 */
	private Class<T> clazz;

	/**
	 * 数据结果映射mapper
	 */
	private RowMapper<T> rowMapper;

	/**
	 * 获得结果集字段的集合
	 * 
	 * @return
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}

	/**
	 * 结果集映射对象
	 * 
	 * @return
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * 获得结果集的对应mapper
	 * 
	 * @return
	 */
	public RowMapper<T> getRowMapper() {
		return rowMapper;
	}

	/**
	 * 根据类创建结果集分析信息
	 * 
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public ResultSetDataInfo(Class<T> clazz) {
		this.clazz = clazz;
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			this.analyze(f);
		}
		ResultSetDataRowMapperCreater creater = new ResultSetDataRowMapperCreater(
				Thread.currentThread().getContextClassLoader());
		Class<T> mapperClazz = creater.createRowMapperClass(this);
		try {
			Object obj = mapperClazz.getConstructor().newInstance();
			this.rowMapper = (RowMapper<T>) obj;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对类进行分析，获得映射的字段对应数据表的列信息
	 * 
	 * @param field
	 */
	private void analyze(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			field.setAccessible(true);
			fieldList.add(field);
			if (column.value().equals("")) {
				fieldColumnMap.put(field.getName(), field.getName()
						.toLowerCase());
			}
			else {
				fieldColumnMap.put(field.getName(), column.value());
			}
		}
	}

	/**
	 * 获得数据库对应的列
	 * 
	 * @param fieldName
	 *            java对象的字段名称
	 * @return
	 */
	public String getColumn(String fieldName) {
		return fieldColumnMap.get(fieldName);
	}
}