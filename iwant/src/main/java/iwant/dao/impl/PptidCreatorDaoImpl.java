package iwant.dao.impl;

import iwant.bean.PptidCreator;
import iwant.dao.PptidCreatorDao;
import cactus.dao.query.BaseDao;

public class PptidCreatorDaoImpl extends BaseDao<PptidCreator> implements
		PptidCreatorDao {

	@Override
	public Class<PptidCreator> getClazz() {
		return PptidCreator.class;
	}
}
