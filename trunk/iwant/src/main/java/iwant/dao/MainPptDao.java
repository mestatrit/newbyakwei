package iwant.dao;

import iwant.bean.MainPpt;
import iwant.bean.enumtype.ActiveType;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface MainPptDao extends IDao<MainPpt> {

	List<MainPpt> getListOrderedByCatid(int catid, ActiveType activeType,
			int begin, int size);

	int countByCdn(MainPptSearchCdn mainPptSearchCdn);

	List<MainPpt> getListOrderedByCdn(MainPptSearchCdn mainPptSearchCdn,
			int begin, int size);
}
