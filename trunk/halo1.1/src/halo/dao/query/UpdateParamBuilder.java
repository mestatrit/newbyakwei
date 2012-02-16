package halo.dao.query;

import java.util.ArrayList;
import java.util.List;

public class UpdateParamBuilder {

	private final List<String> columnList = new ArrayList<String>();

	private final List<String> whereColumnList = new ArrayList<String>();

	private final List<Object> paramList = new ArrayList<Object>();

	private final UpdateParam updateParam = new UpdateParam();

	public void addKeyAndValue(String key, Object value) {
		updateParam.addKeyAndValue(key, value);
	}

	public void update(Class<?> clazz) {
		updateParam.setClazz(clazz);
	}

	public void set(String field, Object value) {
		columnList.add(field);
		paramList.add(value);
	}

	public void where(String field, Object value) {
		whereColumnList.add(field);
		paramList.add(value);
	}

	public UpdateParam create() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int last_idx = whereColumnList.size() - 1;
		for (String field : whereColumnList) {
			sb.append(field).append("=?");
			if (i < last_idx) {
				sb.append(" and ");
			}
		}
		updateParam.set(columnList.toArray(new String[columnList.size()]),
				sb.toString(), paramList.toArray());
		return updateParam;
	}
}