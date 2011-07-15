package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.ProjectFans;

import java.util.List;

public interface ProjectFansDao extends IDao<ProjectFans> {

	ProjectFans getByUseridAndProjectid(long userid, long projectid);

	void deleteByProjectid(long projectid);

	List<ProjectFans> getListByProjectid(long projectid, int begin, int size);
}