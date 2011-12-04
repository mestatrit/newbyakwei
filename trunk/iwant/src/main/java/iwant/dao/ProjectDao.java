package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;

import java.util.List;

public interface ProjectDao extends IDao<Project> {

	List<Project> getListByCatid(int catid, ActiveType activeType, int begin,
			int size);

	List<Project> getListByCdn(ProjectSearchCdn projectSearchCdn, int begin,
			int size);

	List<Project> getListByDid(int did, int begin, int size);

	void updateOrder_flg(long projectid, int order_flg);
}
