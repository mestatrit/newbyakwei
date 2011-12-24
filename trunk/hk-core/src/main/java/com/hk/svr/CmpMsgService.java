package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpMsg;

/**
 * 企业留言相关逻辑
 * 
 * @author akwei
 */
public interface CmpMsgService {

	void createCmpMsg(CmpMsg cmpMsg);

	void deleteCmpMsg(long oid);

	CmpMsg getCmpMsg(long oid);

	List<CmpMsg> getCmpMsgListByCompanyId(long companyId, int begin, int size);

	List<CmpMsg> getCmpMsgListByCompanyIdForCmppink(long companyId, int begin,
			int size);

	void updateCmpMsgCmppink(long oid, byte cmppink);
}