package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.ProjectRecycle;

import java.util.List;

public interface ProjectRecycleDao extends IDao<ProjectRecycle> {

	List<ProjectRecycle> getList(int begin, int size);
}