package iwant.dao.impl;

import iwant.bean.PptidCreator;
import iwant.dao.PptidCreatorDao;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("pptidCreatorDao")
public class PptidCreatorDaoImpl extends BaseDao<PptidCreator> implements
		PptidCreatorDao {

	@Override
	public Class<PptidCreator> getClazz() {
		return PptidCreator.class;
	}
}
