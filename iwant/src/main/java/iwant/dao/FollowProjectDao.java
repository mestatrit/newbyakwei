package iwant.dao;

import iwant.bean.FollowProject;

import com.dev3g.cactus.dao.query.IDao;

public interface FollowProjectDao extends IDao<FollowProject> {

	FollowProject getByUseridAndProjectid(long userid, long projectid);

	void deleteByProjectid(long projectid);
}
