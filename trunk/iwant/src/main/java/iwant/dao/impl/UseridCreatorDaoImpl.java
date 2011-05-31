package iwant.dao.impl;

import org.springframework.stereotype.Component;

import iwant.bean.UseridCreator;
import iwant.dao.UseridCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("useridCreatorDao")
public class UseridCreatorDaoImpl extends BaseDao<UseridCreator> implements
		UseridCreatorDao {

	@Override
	public Class<UseridCreator> getClazz() {
		return UseridCreator.class;
	}
}
