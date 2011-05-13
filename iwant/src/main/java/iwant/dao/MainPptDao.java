package iwant.dao;

import iwant.bean.MainPpt;

import java.util.List;

import cactus.dao.query.IDao;

public interface MainPptDao extends IDao<MainPpt> {

	List<MainPpt> getListOrderedByCatidAndDid(int catid, int did, int begin,
			int size);

	int countByCdn(MainPptSearchCdn mainPptSearchCdn);

	List<MainPpt> getListOrderedByCdn(MainPptSearchCdn mainPptSearchCdn,
			int begin, int size);

	void deleteByProjectid(long projectid);

	MainPpt getByProjectid(long projectid);
}
