package iwant.dao;

import iwant.bean.ProjectFans;

import com.hk.frame.dao.query2.IDao;

public interface ProjectFansDao extends IDao<ProjectFans> {

	ProjectFans getByUseridAndProjectid(long userid, long projectid);
}