package com.hk.svr;

import com.hk.bean.CmpSvrCnf;

public interface CmpSvrCnfService {

	void createCmpSvrCnf(CmpSvrCnf cmpSvrCnf);

	void updateCmpSvrCnf(CmpSvrCnf cmpSvrCnf);

	CmpSvrCnf getCmpSvrCnf(long companyId);
}