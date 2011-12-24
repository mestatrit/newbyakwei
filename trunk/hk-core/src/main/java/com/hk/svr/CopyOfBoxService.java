package com.hk.svr;

import java.util.List;
import java.util.Map;
import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.UserBoxOpen;
import com.hk.bean.UserBoxPrize;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.BoxNotExistException;
import com.hk.svr.box.exception.BoxOpenOutException;
import com.hk.svr.box.exception.BoxPauseException;
import com.hk.svr.box.exception.BoxStopException;
import com.hk.svr.box.exception.BoxTimOutException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.box.exception.UserOpenLimitException;

public interface CopyOfBoxService {
	/**
	 * 审核箱子通过,创建箱子到总表,创建箱子到准备开箱表
	 * 
	 * @param boxId
	 */
	void createBox(long boxId) throws BoxKeyDuplicateException;

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

	// void createBoxPrizeForBox(BoxPrize boxPrize)
	// throws PrizeTotalCountException;
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
	void deleteBoxPrize(long boxId, long prizeId);

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

	BoxPrize getBoxPrize(long boxId, long prizeId);

	void updateBoxPrize(BoxPrize boxPrize) throws PrizeTotalCountException;

	// /**
	// * 获取正在进行中的宝箱列表
	// *
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OpenBox> getOpenBoxList(int begin, int size);
	// List<OpenBox> getOpenBoxListByBoxName(String name, int begin, int size);
	// List<OpenBox> getPauseOpenBoxList(int begin, int size);
	// List<OpenBox> getPauseOpenBoxListByBoxName(String name, int begin, int
	// size);
	// List<OpenBox> getUnOpenBoxList(int begin, int size);
	//
	// List<OpenBox> getUnOpenBoxListByBoxName(String name, int begin, int
	// size);
	// List<OpenBox> getCanProcessBoxListInOpenBox(int size);
	// PreBox getPreBox(long boxId);
	// /**
	// * 获取等待审核的宝箱列表
	// *
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<PreBox> getPreBoxList(int begin, int size);
	//
	// /**
	// * @param name 为null 忽略此条件
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<PreBox> getPreBoxListByBoxName(String name, int begin, int size);
	// TmpBox getTmpBox(long boxId);
	/**
	 * 开宝箱
	 * 
	 * @param userId
	 * @param boxId
	 * @param serviceUser 如果是未注册用户就是用客服默认用户id
	 * @param userMobile 未注册用户手机号码
	 * @return
	 * @throws BoxOpenOutException
	 * @throws BoxNotExistException
	 * @throws BoxTimOutException
	 * @throws BoxStopException
	 * @throws BoxPauseException
	 * @throws UserOpenLimitException
	 */
	UserBoxPrize openBox(long userId, long boxId, boolean serviceUser,
			String userMobile, String ip) throws BoxOpenOutException,
			BoxNotExistException, BoxTimOutException, BoxStopException,
			BoxPauseException, UserOpenLimitException;

	// /**
	// * 若宝箱到期，从openbox中删除
	// *
	// * @param boxId
	// */
	// void processOverdueOpenBox(long boxId);
	// void updatePreBox(PreBox box) throws BoxKeyDuplicateException;
	/**
	 * 发出审核意见，把宝箱从审核表中移到tmpbox，使用户重新修改信息
	 * 
	 * @param boxId
	 * @param content
	 */
	void editForPreBox(long boxId, String content);

	// /**
	// * 正在审核的宝箱
	// *
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<PreBox> getPreBoxListByUserId(long userId, int begin, int size);
	//
	// List<PreBox> getPreBoxListByUserIdAndBoxName(long userId, String name,
	// int begin, int size);
	// /**
	// * 正在使用的宝箱
	// *
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OpenBox> getOpenBoxListByUserId(long userId, int begin, int size);
	//
	// List<OpenBox> getOpenBoxListByUserIdAndBoxName(long userId, String name,
	// int begin, int size);
	// /**
	// * 所有状态正常,未开始的宝箱
	// *
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OpenBox> getNotBeginOpenBoxList(int begin, int size);
	//
	// List<OpenBox> getNotBeginOpenBoxListByBoxName(String name, int begin,
	// int size);
	// /**
	// * 包括所有状态的未开始宝箱
	// *
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OpenBox> getNotBeginOpenBoxListByUserId(long userId, int begin,
	// int size);
	//
	// List<OpenBox> getNotBeginOpenBoxListByUserIdAndBoxName(long userId,
	// String name, int begin, int size);
	// /**
	// * 往期宝箱
	// *
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OverBox> getOverBoxListByUserId(long userId, int begin, int size);
	//
	// /**
	// * @param name 为null可忽略此条件
	// * @param userId
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<OverBox> getOverBoxListByUserIdAndBoxName(String name, long userId,
	// int begin, int size);
	// OpenBox getOpenBox(long boxId);
	Box getBoxByBoxKey(String boxKey);

	void stopBox(long boxId);

	void pauseBox(long boxId);

	void continueBox(long boxId);

	void updateBoxKey(long boxId, String boxKey)
			throws BoxKeyDuplicateException;

	List<Box> getBoxList(int begin, int size);

	// /**
	// * 所有已经开始的和结束的宝箱
	// *
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<Box> getBeginBoxList(long userId, int begin, int size);
	// List<Box> getBoxListByUserId(long userId, int begin, int size);
	// /**
	// * userId所有已经开始的宝箱
	// *
	// * @param begin
	// * @param size
	// * @return
	// */
	// List<Box> getBeginBoxListByUserId(long userId, int begin, int size);
	List<UserBoxPrize> getUserBoxPrizeListByBoxId(long boxId, int begin,
			int size);

	List<UserBoxOpen> getUserBoxOpenListByBoxId(long boxId, int begin, int size);

	void updateBox(Box box) throws BoxKeyDuplicateException,
			PrizeCountMoreThanBoxCountException;

	List<Box> getBoxListByCdn(long userId, String name, byte checkflg,
			byte boxStatus, int begin, int size);

	List<Box> getNotBeginBoxList(long userId, String name, int begin, int size);

	List<Box> getBeginAndNotEndBoxList(long userId, String name, int begin,
			int size);

	List<Box> getBeginBoxList(long userId, String name, int begin, int size);

	List<Box> getEndBoxList(long userId, String name, int begin, int size);
}