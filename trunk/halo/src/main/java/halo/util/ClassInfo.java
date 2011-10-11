package halo.util;

import java.lang.reflect.Field;

/**
 * 
 * @author akwei
 */
public class ClassInfo<T> {

	private Class<T> clazz;

	private Field[] fields;

	public ClassInfo(Class<T> clazz) {
		this.clazz = clazz;
		this.fields = clazz.getDeclaredFields();
		for (Field field : this.fields) {
			field.setAccessible(true);
		}
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public Field[] getFields() {
		return fields;
	}
}
