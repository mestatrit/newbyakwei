package halo.dao.query;

/**
 * 更新时参数的对象表示方式，完全是为了程序易读<br/>
 * 
 * @author akwei
 */
public class UpdateParam extends Param {

	public UpdateParam(String key, Object value) {
		super(key, value);
	}

	private Class<?> clazz;

	private String[] updateColumns;

	private String where;

	private Object[] params;

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getWhere() {
		return where;
	}

	public Object[] getParams() {
		return params;
	}

	public String[] getUpdateColumns() {
		return updateColumns;
	}

	/**
	 * @param updateColumns
	 *            设置需要更新的列(与数据库字段相同)
	 * @param where
	 *            设置sql的where条件
	 * @param params
	 *            设置条件对应的参数
	 */
	public void init(String[] updateColumns, String where, Object[] params) {
		this.updateColumns = updateColumns;
		this.where = where;
		this.params = params;
	}
}
