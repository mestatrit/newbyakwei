package cactus.dao.query;

/**
 * delete时的参数对象 deleteById时,where、params参数无效
 * 
 * @author akwei
 */
public class DeleteParam extends Param {

	public DeleteParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public void setWhereAndParams(String where, Object[] params) {
		this.setWhere(where);
		this.setParams(params);
	}
}
