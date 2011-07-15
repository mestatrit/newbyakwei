package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.PptidCreator;
import iwant.dao.PptidCreatorDao;

import org.springframework.stereotype.Component;

@Component("pptidCreatorDao")
public class PptidCreatorDaoImpl extends BaseDao<PptidCreator> implements
		PptidCreatorDao {

	@Override
	public Class<PptidCreator> getClazz() {
		return PptidCreator.class;
	}
}
