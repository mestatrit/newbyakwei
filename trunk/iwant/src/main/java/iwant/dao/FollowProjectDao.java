package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.FollowProject;

public interface FollowProjectDao extends IDao<FollowProject> {

	FollowProject getByUseridAndProjectid(long userid, long projectid);

	void deleteByProjectid(long projectid);
}
