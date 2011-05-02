package cactus.dao.query;

/**
 * 更新时参数的对象表示方式，完全是为了程序易读<br/>
 * 
 * @author akwei
 */
public class UpdateParam extends Param {

	private String[] updateColumns;

	public UpdateParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public String[] getUpdateColumns() {
		return updateColumns;
	}

	/**
	 * 设置需要更新的列(与数据库字段相同)
	 * 
	 * @param updateColumns
	 */
	public void setUpdateColumns(String[] updateColumns) {
		this.updateColumns = updateColumns;
	}
}
