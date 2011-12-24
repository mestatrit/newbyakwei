package com.hk.svr;

import java.io.File;
import java.util.List;

import com.hk.bean.Badge;
import com.hk.bean.UserBadge;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;

/**
 * 徽章模块，对于徽章获取的规则定义
 * 
 * @author akwei
 */
public interface BadgeService {
	/**
	 * 创建徽章
	 * 
	 * @param badge
	 * @param file 图片文件
	 */
	void createBadge(Badge badge, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException;

	/**
	 * 删除徽章，但不删除徽章图片数据
	 * 
	 * @param badgeId
	 */
	void deleteBadge(long badgeId);

	/**
	 * 更新徽章使用状态
	 * 
	 * @param badgeId
	 * @param stopflg
	 */
	void updateStopflg(long badgeId, byte stopflg);

	/**
	 * 更新徽章数据包括规则
	 * 
	 * @param badge
	 * @param file 图片文件
	 */
	void updateBadge(Badge badge, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException;

	/**
	 * 用户没有获得的并且没有限制的徽章
	 * 
	 * @param idList
	 * @return
	 */
	List<Badge> getBadgeListForNoLimitNotInId(List<Long> idList);

	List<Badge> getBadgeListForSysLimitNotInId(List<Long> idList);

	/**
	 * 获得对于某个足迹的徽章集合
	 * 
	 * @param companyId
	 * @return
	 */
	List<Badge> getBadgeListByCompanyId(long companyId);

	/**
	 * 获得对于某个分类的徽章集合
	 * 
	 * @param kindId
	 * @return
	 */
	List<Badge> getBadgeListByKindId(long kindId);

	/**
	 * 获得对于某个子分类的徽章集合
	 * 
	 * @param childKindId
	 * @return
	 */
	List<Badge> getBadgeListByChildKindId(long childKindId);

	/**
	 * 获得对于某个足迹所加入的组的徽章
	 * 
	 * @param companyId
	 * @return
	 */
	List<Badge> getBadgeListByCompanyInGroup(long companyId);

	/**
	 * 获得徽章数据
	 * 
	 * @param badgeId
	 * @return
	 */
	Badge getBadge(long badgeId);

	List<Badge> getBadgeList(String name, int begin, int size);

	/**
	 * 创建用户徽章
	 * 
	 * @param userBadge
	 */
	void createUserBadge(UserBadge userBadge);

	/**
	 * 删除用户徽章
	 * 
	 * @param oid
	 */
	void deleteUserBadge(long oid);

	/**
	 * 获得用户获得的徽章
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<UserBadge> getUserBadgeListByUserId(long userId, int begin, int size);

	/**
	 * 获得用户已获得的无限制类型徽章
	 * 
	 * @param userId
	 * @return
	 */
	List<UserBadge> getUserBadgeListByUserIdAndNoLimit(long userId);

	/**
	 * 获得用户已获得的离散类型徽章
	 * 
	 * @param userId
	 * @return
	 */
	List<UserBadge> getUserBadgeListByUserIdAndSysLimit(long userId);

	List<UserBadge> getUserBadgeListByUserIdForLimit(long userId);

	UserBadge getUserBadge(long userId, long badgeId);

	UserBadge getUserBadge(long oid);

	List<Badge> getBadgeListInId(List<Long> idList);

	int countUserBadgeByUerId(long userId);

	List<Badge> getBadgeListByLimitflg(byte limitflg);
}
