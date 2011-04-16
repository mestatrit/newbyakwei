package iwant.dao.impl;

import iwant.bean.FollowProject;
import iwant.dao.FollowProjectDao;

import com.hk.frame.dao.query2.BaseDao;

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
}