package com.hk.svr;

import java.util.List;
import com.hk.bean.Impression;

public interface ImpressionService {
	/**
	 * 对一个人只能评价一次
	 * 
	 * @param impression
	 * @return
	 */
	boolean createImpression(Impression impression);

	void updateImpression(Impression impression);

	void deleteImpression(long oid);

	List<Impression> getImpressionListByProuserId(long prouserId, int begin,
			int size);

	Impression getImpression(long senderId, long prouserId);

	Impression getImpression(long oid);
}