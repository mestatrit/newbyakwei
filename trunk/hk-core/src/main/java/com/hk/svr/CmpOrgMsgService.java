package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpOrgMsg;

/**
 * 机构留言相关逻辑
 * 
 * @author akwei
 */
public interface CmpOrgMsgService {

	void createCmpOrgMsg(CmpOrgMsg cmpOrgMsg);

	void deleteCmpOrgMsg(long companyId, long oid);

	CmpOrgMsg getCmpOrgMsg(long companyId, long oid);

	List<CmpOrgMsg> getCmpOrgMsgListByCompanyIdAndOrgId(long companyId,
			long orgId, int begin, int size);

	int countCmpOrgMsgByCompanyIdAndOrgId(long companyId, long orgId);
}