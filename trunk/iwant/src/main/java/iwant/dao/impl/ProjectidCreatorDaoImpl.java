package iwant.dao.impl;

import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectidCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

public class ProjectidCreatorDaoImpl extends BaseDao<ProjectidCreator>
		implements ProjectidCreatorDao {

	@Override
	public Class<ProjectidCreator> getClazz() {
		return ProjectidCreator.class;
	}
}
