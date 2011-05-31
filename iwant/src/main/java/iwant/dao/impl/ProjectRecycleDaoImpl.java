package iwant.dao.impl;

import iwant.bean.ProjectRecycle;
import iwant.dao.ProjectRecycleDao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("projectRecycleDao")
public class ProjectRecycleDaoImpl extends BaseDao<ProjectRecycle> implements
		ProjectRecycleDao {

	@Override
	public Class<ProjectRecycle> getClazz() {
		return ProjectRecycle.class;
	}

	@Override
	public List<ProjectRecycle> getList(int begin, int size) {
		return this.getList(null, null, null, "projectid asc", begin, size);
	}
}