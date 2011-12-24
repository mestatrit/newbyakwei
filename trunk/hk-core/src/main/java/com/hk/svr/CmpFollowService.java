package com.hk.svr;

import java.util.List;
import com.hk.bean.CmpFollow;

public interface CmpFollowService {
	void createCmpFollow(long userId, long companyId);

	void deleteCmpFollw(long userId, long companyId);

	List<CmpFollow> getCmpFollowListByCompanyId(long companyId, int begin,
			int size);

	List<CmpFollow> getCmpFollowListByUserId(long userId, int begin, int size);

	CmpFollow getCmpFollow(long userId, long companyId);
}