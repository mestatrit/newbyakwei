package halo.util.validator;

import halo.util.ClassInfo;
import halo.util.ClassInfoFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ObjectValidator<T> {

	private T t;

	private ValidatorCreator validatorCreator;

	private String tmp_key;

	private String tmp_subExpr;

	private String tmp_message;

	private final Map<String, String> exprMap = new LinkedHashMap<String, String>();

	public void addExpr(String fieldName, String expr) {
		exprMap.put(fieldName, expr);
	}

	public String exec() {
		@SuppressWarnings("unchecked")
		ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory.getClassInfo(t
				.getClass());
		Set<Entry<String, String>> set = exprMap.entrySet();
		Field field;
		Object value;
		Validator validator;
		for (Entry<String, String> e : set) {
			field = classInfo.getField(e.getKey());
			try {
				value = field.get(t);
				this.parseExpr(e.getValue());
				validator = this.validatorCreator.getValidator(tmp_key);
				if (!validator.exec(this.tmp_subExpr, value)) {
					return this.tmp_message;
				}
			}
			catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		return null;
	}

	public List<String> execBatch() {
		List<String> list = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory.getClassInfo(t
				.getClass());
		Set<Entry<String, String>> set = exprMap.entrySet();
		Field field;
		Object value;
		Validator validator;
		for (Entry<String, String> e : set) {
			field = classInfo.getField(e.getKey());
			try {
				value = field.get(t);
				this.parseExpr(e.getValue());
				validator = this.validatorCreator.getValidator(tmp_key);
				if (!validator.exec(this.tmp_subExpr, value)) {
					list.add(this.tmp_message);
				}
			}
			catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		return list;
	}

	private void parseExpr(String expr) {
		int idx0 = expr.indexOf("{");
		this.tmp_key = expr.substring(0, idx0);
		int idx1 = expr.indexOf("}");
		this.tmp_subExpr = expr.substring(idx0 + 1, idx1);
		this.tmp_message = expr.substring(idx1 + 1);
	}
}
