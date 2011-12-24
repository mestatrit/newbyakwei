package com.hk.svr;

import com.hk.bean.CmpSmsPort;
import com.hk.svr.company.exception.NoAvailableCmpSmsPortException;

public interface CmpSmsPortService {
	void batchCreateCmpSmsPort(int portLen, int size);

	CmpSmsPort createAvailableCmpSmsPort(long companyId)
			throws NoAvailableCmpSmsPortException;

	void updateCmpSmsPort(CmpSmsPort cmpSmsPort);

	CmpSmsPort getCmpSmsPortByPort(String port);

	CmpSmsPort getCmpSmsPortByCompanyId(long companyId);

	int countAvailableCmpSmsPort();
}