package iwant.dao.impl;

import iwant.bean.UseridCreator;
import iwant.dao.UseridCreatorDao;

import com.hk.frame.dao.query2.BaseDao;

public class UseridCreatorDaoImpl extends BaseDao<UseridCreator> implements
		UseridCreatorDao {

	@Override
	public Class<UseridCreator> getClazz() {
		return UseridCreator.class;
	}
}
