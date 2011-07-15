package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.MainPpt;

import java.util.List;

public interface MainPptDao extends IDao<MainPpt> {

	List<MainPpt> getListOrderedByCatidAndDid(int catid, int did, int begin,
			int size);

	int countByCdn(MainPptSearchCdn mainPptSearchCdn);

	List<MainPpt> getListOrderedByCdn(MainPptSearchCdn mainPptSearchCdn,
			int begin, int size);

	void deleteByProjectid(long projectid);

	MainPpt getByProjectid(long projectid);
}
