package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.UseridCreator;
import iwant.dao.UseridCreatorDao;

import org.springframework.stereotype.Component;

@Component("useridCreatorDao")
public class UseridCreatorDaoImpl extends BaseDao<UseridCreator> implements
		UseridCreatorDao {

	@Override
	public Class<UseridCreator> getClazz() {
		return UseridCreator.class;
	}
}
