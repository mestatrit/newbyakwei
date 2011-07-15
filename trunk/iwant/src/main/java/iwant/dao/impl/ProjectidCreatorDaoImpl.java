package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectidCreatorDao;

import org.springframework.stereotype.Component;

@Component("projectidCreatorDao")
public class ProjectidCreatorDaoImpl extends BaseDao<ProjectidCreator>
		implements ProjectidCreatorDao {

	@Override
	public Class<ProjectidCreator> getClazz() {
		return ProjectidCreator.class;
	}
}
