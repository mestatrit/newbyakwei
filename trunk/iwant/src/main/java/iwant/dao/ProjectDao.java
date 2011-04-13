package iwant.dao;

import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface ProjectDao extends IDao<Project> {

	List<Project> getListByCatid(int catid, ActiveType activeType, int begin,
			int size);

	List<Project> getListByCdn(ProjectSearchCdn projectSearchCdn, int begin,
			int size);
}
