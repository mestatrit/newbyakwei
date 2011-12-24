package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.UserBoxOpen;
import com.hk.bean.UserBoxPrize;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeRemainException;
import com.hk.svr.box.exception.PrizeTotalCountException;

public interface BoxService {

	/**
	 * 审核箱子通过,创建箱子到总表,创建箱子到准备开箱表
	 * 
	 * @param boxId
	 */
	void createBox(long boxId) throws BoxKeyDuplicateException;

	void createBoxAndPrize(Box box, List<BoxPrize> boxPrizeList)
			throws BoxKeyDuplicateException, PrizeTotalCountException;

	/**
	 * 提交箱子，等待审核(修改箱子审核状态)
	 * 
	 * @param preBox
	 */
	void updateBoxForCheck(long boxId);

	/**
	 * 创建临时宝箱，完成物品添加后创建到prebox中
	 * 
	 * @param box
	 */
	void createTmpBox(Box box) throws BoxKeyDuplicateException;

	/**
	 * 创建宝箱奖品
	 * 
	 * @param boxPrize
	 */
	void createBoxPrize(BoxPrize boxPrize) throws PrizeTotalCountException;

	void createBoxPrize(List<BoxPrize> boxPrizeList)
			throws PrizeTotalCountException;

	/**
	 * 删除未通过审核的宝箱，同时删除奖品
	 * 
	 * @param boxId
	 */
	void deleteBox(long boxId);

	void checkbox(long boxId, byte checkflg);

	/**
	 * 删除宝箱物品
	 * 
	 * @param boxId
	 * @param prizeId
	 */
	void deleteBoxPrize(long prizeId);

	/**
	 * 从总记录表中获得box数据
	 * 
	 * @param boxId
	 * @return
	 */
	Box getBox(long boxId);

	Map<Long, Box> getBoxMapInId(String idStr);

	/**
	 * 获取宝箱物品列表
	 * 
	 * @param boxId
	 * @return
	 */
	List<BoxPrize> getBoxPrizeListByBoxId(long boxId);

	BoxPrize getBoxPrize(long prizeId);

	void updateBoxPrize(BoxPrize boxPrize) throws PrizeTotalCountException,
			PrizeRemainException;

	/**
	 * 开宝箱
	 * 
	 * @param userId
	 * @param boxId
	 * @return 开箱结果
	 */
	BoxOpenResult openBox2(long userId, long boxId);

	/**
	 * 发出审核意见，把宝箱从审核表中移到tmpbox，使用户重新修改信息
	 * 
	 * @param boxId
	 * @param content
	 */
	void editForPreBox(long boxId, String content);

	Box getBoxByBoxKey(String boxKey);

	/**
	 * 宝箱作废，关键词失效
	 * 
	 * @param boxId
	 */
	void stopBox(long boxId);

	void pauseBox(long boxId);

	void continueBox(long boxId);

	boolean updateBoxKey(long boxId, String boxKey);

	// List<Box> getBoxList(int begin, int size);
	List<UserBoxPrize> getUserBoxPrizeListByBoxId(long boxId, int begin,
			int size);

	List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndDrawflg(long boxId,
			byte drawflg, int begin, int size);

	List<UserBoxOpen> getUserBoxOpenListByBoxId(long boxId, int begin, int size);

	void updateBox(Box box) throws BoxKeyDuplicateException,
			PrizeCountMoreThanBoxCountException;

	List<Box> getBoxListByCdn(long userId, String name, byte checkflg,
			byte boxStatus, int begin, int size);

	List<Box> getNotBeginBoxList(long userId, String name, int begin, int size);

	List<Box> getCanOpenBoxList(long userId, String name, int begin, int size);

	/**
	 * 某城市的可以开的宝箱
	 * 
	 * @param cityId
	 * @param pinkflg -1时忽略次条件 可以是 推荐的宝箱 与置顶的宝箱
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-26
	 */
	List<Box> getCanOpenBoxListByCityId(long cityId, byte pinkflg, int begin,
			int size);

	/**
	 * 全球发行的宝箱
	 * 
	 * @param pinkflg -1时忽略次条件 可以是 推荐的宝箱 与置顶的宝箱
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-26
	 */
	List<Box> getCanOpenBoxListByNoCity(byte pinkflg, int begin, int size);

	List<Box> getCanOpenBoxListByCompanyId(long companyId, int begin, int size);

	List<Box> getCanOpenBoxListByCompanyIdForCmppink(long companyId, int begin,
			int size);

	int countCanOpenBox();

	int countCanOpenBoxByCityId(int cityId);

	/**
	 * 获得未过期的宝箱（包括暂停和作废的）
	 * 
	 * @param userId
	 * @param name
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-21
	 */
	List<Box> getInTimeBoxList(long userId, String name, int begin, int size);

	/**
	 * 获取联盟已经开始没有结束的宝箱
	 * 
	 * @param uid
	 * @param beginl
	 * @param size
	 * @return
	 */
	List<Box> getBoxListByUid(long uid, int begin, int size);

	List<Box> getBeginBoxList(long userId, String name, int begin, int size);

	List<Box> getEndBoxList(long userId, String name, int begin, int size);

	void updateUid(long companyId, long uid);

	/**
	 * 设置用户已经领奖，更新领奖标志，更新领奖时间
	 * 
	 * @param sysId
	 *            2010-4-18
	 */
	void setUserBoxPrizeDrawed(long sysId);

	UserBoxPrize getUserBoxPrize(long sysId);

	/**
	 * 用户根据序列号与暗号兑奖
	 * 
	 * @param boxId
	 * @param num
	 * @param pwd
	 * @return
	 *         2010-4-18
	 */
	UserBoxPrize getUserBoxPrizeByBoxIdAndNumAndPwd(long boxId, String num,
			String pwd);

	List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndNumAndPwd(long boxId,
			String num, String pwd, int begin, int size);

	void updateBoxPrizePath(long prizeId, String path);

	List<UserBoxPrize> getUserBoxPrizeListByUserId(long userId, int begin,
			int size);

	Map<Long, BoxPrize> getBoxPrizeMapInId(List<Long> idList);

	void updateBoxPinkFlg(long boxId, byte pinkflg);

	void updateBoxPinkFlgTop(long boxId);

	void deleteUserBoxPrize(long sysId);

	void updateBoxCmppink(long boxId, byte cmppink);

	List<Box> getBoxListByCompanyId(long companyId, int begin, int size);
}