package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Project;
import iwant.bean.enumtype.ActiveType;

import java.util.List;
import java.util.Map;

public interface ProjectDao extends IDao<Project> {

	List<Project> getListByCatid(int catid, ActiveType activeType, int begin,
			int size);

	List<Project> getListByCdn(ProjectSearchCdn projectSearchCdn, int begin,
			int size);

	Map<Long, Project> getMapInId(List<Long> idList);

	List<Project> getListByCatidAndDid(int catid, int did, int begin, int size);
}
