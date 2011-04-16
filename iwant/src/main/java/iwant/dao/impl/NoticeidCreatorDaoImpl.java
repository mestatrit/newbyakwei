package iwant.dao.impl;

import iwant.bean.NoticeidCreator;
import iwant.dao.NoticeidCreatorDao;

import com.hk.frame.dao.query2.BaseDao;

public class NoticeidCreatorDaoImpl extends BaseDao<NoticeidCreator> implements
		NoticeidCreatorDao {

	@Override
	public Class<NoticeidCreator> getClazz() {
		return NoticeidCreator.class;
	}
}
