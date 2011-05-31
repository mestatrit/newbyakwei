package iwant.dao.impl;

import org.springframework.stereotype.Component;

import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectidCreatorDao;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("projectidCreatorDao")
public class ProjectidCreatorDaoImpl extends BaseDao<ProjectidCreator>
		implements ProjectidCreatorDao {

	@Override
	public Class<ProjectidCreator> getClazz() {
		return ProjectidCreator.class;
	}
}
