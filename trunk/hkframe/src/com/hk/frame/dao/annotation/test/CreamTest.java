package com.hk.frame.dao.annotation.test;

import java.lang.reflect.Field;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.P;

@Table(name = "cream", id = "id")
public class CreamTest {
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {
		Cream cream = new Cream();
		cream.setId(111);
		cream.setName("与阿尼万恶");
		cream.setUserId("sdfsdf");
		long begin = System.currentTimeMillis();
		Field[] fs = cream.getClass().getDeclaredFields();
		P.println(cream.getClass().getName());
		for (Field f : fs) {
			f.setAccessible(true);
			// Column column = f.getAnnotation(Column.class);
			// if (column != null) {
			// if (column.value().equals("")) {
			// // P.println(f.getName().toLowerCase());
			// }
			// else {
			// // P.println(column.value());
			// }
			// }
			Object value = f.get(cream);
			if (value == null) {
				P.println("null");
			}
			else {
				P.println(value);
			}
		}
		long end = System.currentTimeMillis();
		P.println(end - begin);
	}
}