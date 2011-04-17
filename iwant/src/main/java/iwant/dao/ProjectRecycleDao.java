package iwant.dao;

import iwant.bean.ProjectRecycle;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface ProjectRecycleDao extends IDao<ProjectRecycle> {

	List<ProjectRecycle> getList(int begin, int size);
}