package iwant.dao.impl;

import iwant.bean.Project;
import iwant.dao.ProjectDao;

import com.hk.frame.dao.query2.BaseDao;

public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

	@Override
	public Class<Project> getClazz() {
		return Project.class;
	}
}
