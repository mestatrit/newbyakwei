package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.NoticeidCreator;
import iwant.dao.NoticeidCreatorDao;

import org.springframework.stereotype.Component;

@Component("noticeidCreatorDao")
public class NoticeidCreatorDaoImpl extends BaseDao<NoticeidCreator> implements
		NoticeidCreatorDao {

	@Override
	public Class<NoticeidCreator> getClazz() {
		return NoticeidCreator.class;
	}
}
