package iwant.dao;

import iwant.bean.ProjectFans;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface ProjectFansDao extends IDao<ProjectFans> {

	ProjectFans getByUseridAndProjectid(long userid, long projectid);

	void deleteByProjectid(long projectid);

	List<ProjectFans> getListByProjectid(long projectid, int begin, int size);
}