package iwant.dao.impl;

import iwant.bean.FollowProject;
import iwant.dao.FollowProjectDao;
import cactus.dao.query.BaseDao;

public class FollowProjectDaoImpl extends BaseDao<FollowProject> implements
		FollowProjectDao {

	@Override
	public Class<FollowProject> getClazz() {
		return FollowProject.class;
	}

	@Override
	public FollowProject getByUseridAndProjectid(long userid, long projectid) {
		return this.getObject(null, "userid=? and projectid=?", new Object[] {
				userid, projectid });
	}

	@Override
	public void deleteByProjectid(long projectid) {
		this.delete(null, "projectid=?", new Object[] { projectid });
	}
}