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

	public List<Field> getFieldList() {
		return fieldList;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public RowMapper<T> getRowMapper() {
		return rowMapper;
	}

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

	public String getColumn(String fieldName) {
		return fieldColumnMap.get(fieldName);
	}
}