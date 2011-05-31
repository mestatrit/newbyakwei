package iwant.dao.impl;

import org.springframework.stereotype.Component;

import iwant.bean.NoticeidCreator;
import iwant.dao.NoticeidCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("noticeidCreatorDao")
public class NoticeidCreatorDaoImpl extends BaseDao<NoticeidCreator> implements
		NoticeidCreatorDao {

	@Override
	public Class<NoticeidCreator> getClazz() {
		return NoticeidCreator.class;
	}
}
