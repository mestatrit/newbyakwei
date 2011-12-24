package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpStudyKind;

/**
 * 专业分类
 * 
 * @author akwei
 */
public interface CmpStudyKindService {

	/**
	 * 如果parentId>0，则更新父类中子类标志，最多只能有3级，添加3级以上的分类将不会成功
	 * 
	 * @param cmpOrgStudyKind
	 *            2010-7-12
	 */
	void createCmpStudyKind(CmpStudyKind cmpOrgStudyKind);

	void updateCmpStudyKind(CmpStudyKind cmpOrgStudyKind);

	/**
	 * 当删除专业分类时，最好把以前此分类下的招生简章中专业id置为0(暂不设置)
	 * 
	 * @param companyId
	 * @param kindId
	 *            2010-7-12
	 */
	void deleteCmpStudyKind(long companyId, long kindId);

	CmpStudyKind getCmpStudyKind(long companyId, long kindId);

	/**
	 * @param companyId
	 * @param parentId
	 * @param name 为空时，不参与查询,不为空时，为模糊搜索
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-12
	 */
	List<CmpStudyKind> getCmpStudyKindListByCompanyIdAndParentIdEx(
			long companyId, long parentId, String name, int begin, int size);

	/**
	 * @param companyId
	 * @param parentId
	 * @param name 为空时，不参与查询,不为空时，为模糊搜索
	 * @return
	 *         2010-7-13
	 */
	int countCmpStudyKindByCompanyIdAndParentIdEx(long companyId,
			long parentId, String name);

	Map<Long, CmpStudyKind> getCmpStudyKindByCompanyIdInId(long companyId,
			List<Long> idList);
}