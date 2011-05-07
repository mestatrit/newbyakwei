package iwant.dao.impl;

import iwant.bean.UseridCreator;
import iwant.dao.UseridCreatorDao;
import cactus.dao.query.BaseDao;

public class UseridCreatorDaoImpl extends BaseDao<UseridCreator> implements
		UseridCreatorDao {

	@Override
	public Class<UseridCreator> getClazz() {
		return UseridCreator.class;
	}
}
