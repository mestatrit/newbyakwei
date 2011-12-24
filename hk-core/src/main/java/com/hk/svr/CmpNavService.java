package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpNav;

public interface CmpNavService {

	/**
	 * 按照同一级别的最大顺序进行设置
	 * 
	 * @param cmpNav
	 *            2010-5-31
	 */
	void createCmpNav(CmpNav cmpNav);

	void updateCmpNav(CmpNav cmpNav);

	void updateCmpNavOrderflg(long oid, int orderflg);

	void updateCmpNavShowInHome(long oid, int showInHome);

	/**
	 * 删除导航，并删除导航之下的子导航，以及相应数据
	 * 
	 * @param oid
	 *            2010-5-18
	 */
	void deleteCmpNav(long oid);

	/**
	 * 获取导航数据集合，按照自定义的顺序排列
	 * 
	 * @param companyId
	 * @param nlevel
	 * @return
	 *         2010-5-17
	 */
	List<CmpNav> getCmpNavListByCompanyIdAndNlevel(long companyId, int nlevel);

	CmpNav getCmpNavByCompanyIdAndReffunc(long companyId, int reffunc);

	List<CmpNav> getCmpNavListByCompanyIdAndParentId(long companyId,
			long parentId);

	CmpNav getCmpNav(long oid);

	List<CmpNav> getCmpNavListByCompanyId(long companyId);

	/**
	 * 根据已经设置的其他导航的顺序来安排当前导航的顺序，顺序号为自动判断当前最大然后递增
	 * 
	 * @param companyId
	 * @param oid
	 * @param homepos 在首页的位置 {@link CmpNav#HOMEPOS_LEFT}
	 *            {@link CmpNav#HOMEPOS_MIDDLE} {@link CmpNav#HOMEPOS_RIGHT}
	 *            2010-5-18
	 */
	void setCmpNavShowInHome(long companyId, long oid, byte homepos);

	/**
	 * 获取在首页显示的导航集合
	 * 
	 * @param companyId
	 * @return
	 *         2010-5-14
	 */
	List<CmpNav> getCmpNavListByCompanyIdForHome(long companyId);

	/**
	 * 获得在首页显示的模块导航集合
	 * 
	 * @param companyId
	 * @param homepos 首页的位置 @see {@link CmpNav#HOMEPOS_MIDDLE}
	 *            {@link CmpNav#HOMEPOS_RIGHT}
	 * @return
	 *         2010-5-23
	 */
	List<CmpNav> getCmpNavListByCompanyIdForHome(long companyId, byte homepos);

	CmpNav getHomeCmpNav(long companyId);

	int countCmpNavByCompanyIdAndParentIdAndNlevel(long companyId,
			long parentId, byte nlevel);

	Map<Long, CmpNav> getCmpNavMapByCompanyIdAndInId(long companyId,
			List<Long> idList);

	List<CmpNav> getCmpNavListByCompanyIdAndInId(long companyId,
			List<Long> idList);
}