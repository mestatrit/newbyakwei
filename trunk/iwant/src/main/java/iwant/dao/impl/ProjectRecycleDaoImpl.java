package iwant.dao.impl;

import iwant.bean.ProjectRecycle;
import iwant.dao.ProjectRecycleDao;

import com.hk.frame.dao.query2.BaseDao;

public class ProjectRecycleDaoImpl extends BaseDao<ProjectRecycle> implements
		ProjectRecycleDao {

	@Override
	public Class<ProjectRecycle> getClazz() {
		return ProjectRecycle.class;
	}
}