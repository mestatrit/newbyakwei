package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpFrLink;

public interface CmpFrLinkService {

	void createCmpFrLink(CmpFrLink cmpFrLink);

	void updateCmpFrLink(CmpFrLink cmpFrLink);

	void deleteCmpFrLink(long linkId);

	List<CmpFrLink> getCmpFrLinkListByCompanyId(long companyId);

	CmpFrLink getCmpFrLink(long linkId);
}