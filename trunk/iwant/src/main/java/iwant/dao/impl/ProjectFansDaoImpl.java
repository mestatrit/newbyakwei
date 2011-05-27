package iwant.dao.impl;

import iwant.bean.ProjectFans;
import iwant.dao.ProjectFansDao;

import java.util.List;

import com.dev3g.cactus.dao.query.BaseDao;

public class ProjectFansDaoImpl extends BaseDao<ProjectFans> implements
		ProjectFansDao {

	@Override
	public Class<ProjectFans> getClazz() {
		return ProjectFans.class;
	}

	@Override
	public ProjectFans getByUseridAndProjectid(long userid, long projectid) {
		return this.getObject(null, "userid=? and projectid=?", new Object[] {
				userid, projectid });
	}

	@Override
	public void deleteByProjectid(long projectid) {
		this.delete(null, "projectid=?", new Object[] { projectid });
	}

	@Override
	public List<ProjectFans> getListByProjectid(long projectid, int begin,
			int size) {
		return this.getList(null, "projectid=?", new Object[] { projectid },
				"sysid desc", begin, size);
	}
}