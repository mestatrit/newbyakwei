package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpFuncRef;

/**
 * 企业可具有的特殊功能操作
 * 
 * @author akwei
 */
public interface CmpFuncService {

	/**
	 * 对于一个功能，每个企业只能有一条记录，重复的添加记录不会成功
	 * 
	 * @param cmpFuncRef
	 *            2010-8-13
	 */
	void createCmpFuncRef(CmpFuncRef cmpFuncRef);

	void updateCmpFuncRef(CmpFuncRef cmpFuncRef);

	void deleteCmpFuncRef(long companyId, long oid);

	CmpFuncRef getCmpFuncRef(long companyId, long oid);

	List<CmpFuncRef> getCmpFuncRefListByCompanyId(long companyId);
}