package iwant.dao;

import iwant.bean.FollowProject;

import com.hk.frame.dao.query2.IDao;

public interface FollowProjectDao extends IDao<FollowProject> {

	FollowProject getByUseridAndProjectid(long userid, long projectid);
}
