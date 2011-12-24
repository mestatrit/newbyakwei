package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpJoinInApply;

/**
 * 加盟申请逻辑
 * 
 * @author akwei
 */
public interface CmpJoinInApplyService {

	void createCmpJoinInApply(CmpJoinInApply cmpJoinInApply);

	void deleteCmpJoinInApply(long oid);

	void setCmpJoinInApplyReaded(long oid, byte readed);

	List<CmpJoinInApply> getCmpJoinInApplyListByCompanyId(long companyId,
			int begin, int size);

	List<CmpJoinInApply> getCmpJoinInApplyListByCompanyIdAndReaded(
			long companyId, byte readed, int begin, int size);

	CmpJoinInApply getCmpJoinInApply(long oid);
}