package halo.util.validator;

import halo.util.ClassInfo;
import halo.util.ClassInfoFactory;
import halo.util.JsonObj;
import halo.util.JsonUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ObjectValidator {

	private Object instance;

	private ValidatorCreator validatorCreator;

	private String tmp_key;

	private String tmp_message;

	private JsonObj jsonObj;

	private final Map<String, String> exprMap = new LinkedHashMap<String, String>();

	public <T> ObjectValidator(T instance, ValidatorCreator validatorCreator) {
		super();
		this.instance = instance;
		this.validatorCreator = validatorCreator;
	}

	public void addExpr(String fieldName, String expr) {
		exprMap.put(fieldName, expr);
	}

	public <T> ErrResult exec() {
		@SuppressWarnings("unchecked")
		ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory
				.getClassInfo(instance.getClass());
		Set<Entry<String, String>> set = exprMap.entrySet();
		Field field;
		Object value;
		Validator validator;
		for (Entry<String, String> e : set) {
			field = classInfo.getField(e.getKey());
			try {
				value = field.get(instance);
				this.parseExpr(e.getValue());
				validator = this.validatorCreator.getValidator(tmp_key);
				if (!validator.exec(this.jsonObj, value)) {
					return new ErrResult(e.getKey(), this.tmp_message);
				}
			}
			catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		return null;
	}

	public <T> List<ErrResult> execBatch() {
		List<ErrResult> list = new ArrayList<ErrResult>();
		@SuppressWarnings("unchecked")
		ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory
				.getClassInfo(instance.getClass());
		Set<Entry<String, String>> set = exprMap.entrySet();
		Field field;
		Object value;
		Validator validator;
		for (Entry<String, String> e : set) {
			field = classInfo.getField(e.getKey());
			try {
				value = field.get(instance);
				this.parseExpr(e.getValue());
				validator = this.validatorCreator.getValidator(tmp_key);
				if (!validator.exec(this.jsonObj, value)) {
					list.add(new ErrResult(e.getKey(), this.tmp_message));
				}
			}
			catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		return list;
	}

	private void parseExpr(String expr) {
		int idx = expr.indexOf("{");
		this.tmp_key = expr.substring(0, idx);
		String json = expr.substring(idx);
		this.jsonObj = JsonUtil.getJsonObj(json);
		this.tmp_message = this.jsonObj.getString("msg");
	}
}
