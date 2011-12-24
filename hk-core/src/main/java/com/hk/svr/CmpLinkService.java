package com.hk.svr;

import java.util.List;
import com.hk.bean.CmpLink;
import com.hk.bean.Company;

public interface CmpLinkService {
	void createCmpLink(long companyId, long linkCompanyId);

	CmpLink getCmpLink(long companyId, long linkCompanyId);

	List<Company> getLindCompanyList(long companyId, int begin, int size);

	void deleteCmpLink(long companyId, long linkCompanyId);
}