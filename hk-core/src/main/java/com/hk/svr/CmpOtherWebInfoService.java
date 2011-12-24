package com.hk.svr;

import com.hk.bean.CmpOtherWebInfo;

public interface CmpOtherWebInfoService {

	void createCmpOtherWebInfo(CmpOtherWebInfo cmpOtherWebInfo);

	void updateCmpOtherWebInfo(CmpOtherWebInfo cmpOtherWebInfo);

	CmpOtherWebInfo getCmpOtherWebInfo(long companyId);
}