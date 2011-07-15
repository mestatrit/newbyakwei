package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.FollowProject;
import iwant.dao.FollowProjectDao;

import org.springframework.stereotype.Component;

@Component("followProjectDao")
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