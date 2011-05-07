package iwant.dao;

import iwant.bean.ProjectRecycle;

import java.util.List;

import cactus.dao.query.IDao;

public interface ProjectRecycleDao extends IDao<ProjectRecycle> {

	List<ProjectRecycle> getList(int begin, int size);
}