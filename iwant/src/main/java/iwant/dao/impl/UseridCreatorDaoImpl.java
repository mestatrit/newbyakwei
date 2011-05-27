package iwant.dao.impl;

import iwant.bean.UseridCreator;
import iwant.dao.UseridCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

public class UseridCreatorDaoImpl extends BaseDao<UseridCreator> implements
		UseridCreatorDao {

	@Override
	public Class<UseridCreator> getClazz() {
		return UseridCreator.class;
	}
}
