package iwant.dao.impl;

import iwant.bean.NoticeidCreator;
import iwant.dao.NoticeidCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

public class NoticeidCreatorDaoImpl extends BaseDao<NoticeidCreator> implements
		NoticeidCreatorDao {

	@Override
	public Class<NoticeidCreator> getClazz() {
		return NoticeidCreator.class;
	}
}
