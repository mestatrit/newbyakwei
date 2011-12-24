package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpContact;

/**
 * 添加企业在线联系(qq)
 * 
 * @author akwei
 */
public interface CmpContactService {

	void createCmpContact(CmpContact cmpContact);

	void deleteCmpContact(long oid);

	void updateCmpContact(CmpContact cmpContact);

	CmpContact getCmpContact(long oid);

	List<CmpContact> getCmpContactListByCompanyId(long companyId);
}