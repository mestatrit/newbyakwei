package iwant.dao.impl;

import iwant.bean.ProjectFans;
import iwant.dao.ProjectFansDao;

import com.hk.frame.dao.query2.BaseDao;

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
}