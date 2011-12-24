package com.hk.svr.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.CmdData;
import com.hk.bean.CmpUtil;
import com.hk.bean.SeqBox;
import com.hk.bean.TmpBoxInfo;
import com.hk.bean.UserBoxOpen;
import com.hk.bean.UserBoxPrize;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.CmdDataService;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeRemainException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.Err;

public class BoxServiceImpl implements BoxService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private CmdDataService cmdDataService;

	public void checkbox(long boxId, byte checkflg) {
		Query query = this.manager.createQuery();
		query.addField("checkflg", checkflg);
		query.updateById(Box.class, boxId);
	}

	public synchronized void continueBox(long boxId) {
		this.updateBoxStatus(boxId, Box.BOX_STATUS_NORMAL);
	}

	public synchronized void createBox(long boxId)
			throws BoxKeyDuplicateException {
		this.checkbox(boxId, Box.CHECKFLG_CHECKOK);
	}

	public void createBoxAndPrize(Box box, List<BoxPrize> boxPrizeList)
			throws BoxKeyDuplicateException, PrizeTotalCountException {
		this.createTmpBox(box);
		for (BoxPrize boxPrize : boxPrizeList) {
			boxPrize.setBoxId(box.getBoxId());
		}
		this.createBoxPrize(boxPrizeList);
	}

	public void createBoxPrize(BoxPrize boxPrize)
			throws PrizeTotalCountException {
		Box box = this.getBox(boxPrize.getBoxId());
		if (box == null) {
			return;
		}
		int remainBoxCount = box.getTotalCount() - box.getOpenCount();
		if (remainBoxCount < boxPrize.getPcount()) {
			throw new PrizeTotalCountException("prize count [ "
					+ boxPrize.getPcount() + " ] more than box totalcount [ "
					+ box.getTotalCount() + " ]");
		}
		List<BoxPrize> list = this.getBoxPrizeListByBoxId(box.getBoxId());
		int sum = boxPrize.getPcount();
		for (BoxPrize o : list) {
			sum = o.getRemain() + sum;
		}
		if (sum > remainBoxCount) {
			throw new PrizeTotalCountException("total prize count [ " + sum
					+ " ] more than box totalcount [ " + box.getTotalCount()
					+ " ]");
		}
		this.createBoxPrizeData(boxPrize);
	}

	public void createBoxPrize(List<BoxPrize> boxPrizeList)
			throws PrizeTotalCountException {
		for (BoxPrize boxPrize : boxPrizeList) {
			this.createBoxPrize(boxPrize);
		}
	}

	private void createBoxPrizeData(BoxPrize boxPrize) {
		boxPrize.setRemain(boxPrize.getPcount());
		Query query = this.manager.createQuery();
		long prizeId = query.insertObject(boxPrize).longValue();
		boxPrize.setPrizeId(prizeId);
	}

	public void createBoxPrizeForBox(BoxPrize boxPrize)
			throws PrizeTotalCountException {
		Box box = this.getBox(boxPrize.getBoxId());
		if (box == null) {
			return;
		}
		if (box.getTotalCount() - box.getOpenCount() < boxPrize.getPcount()) {
			throw new PrizeTotalCountException("prize count [ "
					+ boxPrize.getPcount() + " ] more than box totalcount [ "
					+ box.getTotalCount() + " ]");
		}
		List<BoxPrize> list = this.getBoxPrizeListByBoxId(box.getBoxId());
		int sum = boxPrize.getPcount();
		for (BoxPrize o : list) {
			sum = o.getRemain() + sum;
		}
		if (sum > box.getTotalCount()) {
			throw new PrizeTotalCountException("total remain prize count [ "
					+ sum + " ] more than box total remain count [ "
					+ box.getTotalCount() + " ]");
		}
		this.createBoxPrizeData(boxPrize);
	}

	public synchronized void createTmpBox(Box box)
			throws BoxKeyDuplicateException {
		Query query = this.manager.createQuery();
		query.addField("createtime", new Date());
		long boxId = query.insert(SeqBox.class).longValue();// 获得宝箱自增id
		box.setBoxId(boxId);
		box.setBoxStatus(Box.BOX_STATUS_NORMAL);
		box.setCheckflg(Box.CHECKFLG_UNCHECK);
		box.setCreateTime(new Date());
		box.setCmppinkTime(box.getCreateTime());
		box.setPinkflg(Box.PINKFLG_N);
		if (box.isVirtual()) {
			box.setOtherPrizeflg(Box.OTHERPRIZEFLG_N);
		}
		query.insertObject(box);
		this.updateBoxKey(boxId, box.getBoxKey());
	}

	private void createUserBoxOpen(UserBoxOpen userBoxOpen) {
		Query query = this.manager.createQuery();
		userBoxOpen.setSysId(query.insertObject(userBoxOpen).longValue());
	}

	public void deleteBox(long boxId) {
		Query query = this.manager.createQuery();
		query.deleteById(Box.class, boxId);
		// 删除奖品
		query.delete(BoxPrize.class, "boxid=?", new Object[] { boxId });
	}

	public void deleteBoxPrize(long prizeId) {
		Query query = this.manager.createQuery();
		int count = query.count(UserBoxPrize.class, "prizeid=?",
				new Object[] { prizeId });
		if (count == 0) {
			query.deleteById(BoxPrize.class, prizeId);
		}
	}

	public synchronized void editForPreBox(long boxId, String content) {
		Query query = this.manager.createQuery();
		this.checkbox(boxId, Box.CHECKFLG_TMP);
		if (content != null) {
			query.addField("boxid", boxId);
			query.addField("content", content);
			query.insert(TmpBoxInfo.class);
		}
	}

	private BoxPrize getAwardPrize(long boxId, int remain) {
		boolean isAward = false;
		Query query = this.manager.createQuery();
		List<BoxPrize> list = query.listEx(BoxPrize.class,
				"boxid=? and remain>?", new Object[] { boxId, 0 });
		int prizeSum = 0;
		for (BoxPrize o : list) {
			prizeSum += o.getRemain();
		}
		if (prizeSum == 0) {// 奖品已经被抽走,没有任何物品
			return null;
		}
		else if (prizeSum < remain) {// 如果奖品剩余数量小于宝箱剩余数量,进行概率计算
			int g = (remain / prizeSum) + 1;// 按照比例,缩为(1/n)+1个宝箱的比例
			Random random = new Random();
			int awardNum = random.nextInt(g);// 生成当前中将数字
			Random sel = new Random();
			int selNum = sel.nextInt(g);// 生成当前抽中数字
			if (awardNum == selNum) {
				isAward = true;// 中奖操作
			}
		}
		else if (prizeSum == remain) {// 如果剩余的宝箱数量等于奖品剩余数量,则剩下的抽中都是奖品,中奖算法相同
			isAward = true;
		}
		if (isAward) {// 中奖处理
			Random prizeRand = new Random();
			int n = list.size() - 1;
			int prizeIdx = -1;
			if (n > 0) {
				prizeIdx = prizeRand.nextInt(n);
			}
			else {
				prizeIdx = 0;
			}
			if (prizeIdx > -1) {
				return list.get(prizeIdx);// 中奖id赋值
			}
		}
		return null;
	}

	public int countCanOpenBox() {
		Date d = new Date();
		Query query = this.manager.createQuery();
		return query.count(Box.class,
				"boxstatus=? and checkflg=? and begintime<=? and endtime>?",
				new Object[] { Box.BOX_STATUS_NORMAL, Box.CHECKFLG_CHECKOK, d,
						d });
	}

	public int countCanOpenBoxByCityId(int cityId) {
		Date d = new Date();
		Query query = this.manager.createQuery();
		return query
				.count(
						Box.class,
						"boxstatus=? and checkflg=? and cityid=? and begintime<=? and endtime>?",
						new Object[] { Box.BOX_STATUS_NORMAL,
								Box.CHECKFLG_CHECKOK, cityId, d, d });
	}

	public List<Box> getCanOpenBoxList(long userId, String name, int begin,
			int size) {
		StringBuilder sql = new StringBuilder(
				"select * from box where boxstatus=? and checkflg=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Box.BOX_STATUS_NORMAL);
		olist.add(Box.CHECKFLG_CHECKOK);
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" and begintime<=? and endtime>? order by boxid desc");
		Date d = new Date();
		olist.add(d);
		olist.add(d);
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	public List<Box> getCanOpenBoxListByCityId(long cityId, byte pinkflg,
			int begin, int size) {
		if (pinkflg < 0) {
			return this.getCanOpenBoxListByCityId(cityId, begin, size);
		}
		return this.getCanOpenBoxListByCityIdAndPinkflg(cityId, pinkflg, begin,
				size);
	}

	public List<Box> getCanOpenBoxListByCompanyId(long companyId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query.listEx(Box.class,
				"companyid=? and boxstatus=? and begintime<=? and endtime>?",
				new Object[] { companyId, Box.BOX_STATUS_NORMAL, d, d },
				"boxid desc", begin, size);
	}

	public List<Box> getCanOpenBoxListByCompanyIdForCmppink(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query
				.listEx(
						Box.class,
						"companyid=? and cmppink=? and boxstatus=? and begintime<=? and endtime>?",
						new Object[] { companyId, CmpUtil.CMPPINK_Y,
								Box.BOX_STATUS_NORMAL, d, d },
						"cmppinktime desc", begin, size);
	}

	private List<Box> getCanOpenBoxListByCityIdAndPinkflg(long cityId,
			byte pinkflg, int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query
				.listEx(
						Box.class,
						"cityid=? and pinkflg=? and boxstatus=? and checkflg=? and begintime<=? and endtime>?",
						new Object[] { cityId, pinkflg, Box.BOX_STATUS_NORMAL,
								Box.CHECKFLG_CHECKOK, d, d }, "boxid desc",
						begin, size);
	}

	private List<Box> getCanOpenBoxListByCityId(long cityId, int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query
				.listEx(
						Box.class,
						"cityid=? and boxstatus=? and checkflg=? and begintime<=? and endtime>?",
						new Object[] { cityId, Box.BOX_STATUS_NORMAL,
								Box.CHECKFLG_CHECKOK, d, d }, "boxid desc",
						begin, size);
	}

	public List<Box> getCanOpenBoxListByNoCity(byte pinkflg, int begin, int size) {
		if (pinkflg < 0) {
			return this.getCanOpenBoxListByNoCity(begin, size);
		}
		return this.getCanOpenBoxListByPinkflgAndNoCity(pinkflg, begin, size);
	}

	private List<Box> getCanOpenBoxListByPinkflgAndNoCity(byte pinkflg,
			int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query
				.listEx(
						Box.class,
						"cityid=? and pinkflg=? and boxstatus=? and checkflg=? and begintime<=? and endtime>?",
						new Object[] { 0, pinkflg, Box.BOX_STATUS_NORMAL,
								Box.CHECKFLG_CHECKOK, d, d }, "boxid desc",
						begin, size);
	}

	private List<Box> getCanOpenBoxListByNoCity(int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query
				.listEx(
						Box.class,
						"cityid=? and boxstatus=? and checkflg=? and begintime<=? and endtime>?",
						new Object[] { 0, Box.BOX_STATUS_NORMAL,
								Box.CHECKFLG_CHECKOK, d, d }, "boxid desc",
						begin, size);
	}

	public List<Box> getInTimeBoxList(long userId, String name, int begin,
			int size) {
		StringBuilder sql = new StringBuilder(
				"select * from box where checkflg=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Box.CHECKFLG_CHECKOK);
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" and begintime<=? and endtime>? order by boxid desc");
		Date d = new Date();
		olist.add(d);
		olist.add(d);
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	public List<Box> getBeginBoxList(long userId, String name, int begin,
			int size) {
		StringBuilder sql = new StringBuilder(
				"select * from box where boxstatus=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Box.BOX_STATUS_NORMAL);
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" and begintime<? order by boxid desc");
		Date d = new Date();
		olist.add(d);
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	// public void updateBoxKey(long boxId, String boxKey)
	// throws BoxKeyDuplicateException {
	// if (boxKey != null) {
	// Box o = this.getBox(boxId);
	// if (!boxKey.equals(o.getBoxKey())) {
	// this.checkBoxKey(boxKey);
	// }
	// }
	// Query query = this.manager.createQuery();
	// query.addField("boxkey", boxKey);
	// query.updateById(Box.class, boxId);
	// }
	public Box getBox(long boxId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Box.class, boxId);
	}

	public Box getBoxByBoxKey(String boxKey) {
		Date date = new Date();
		Query query = this.manager.createQuery();
		query.setTable(Box.class);
		query.where("boxkey=? and begintime<? and endtime>? and boxstatus=?");
		query.setParam(boxKey).setParam(date).setParam(date).setParam(
				Box.BOX_STATUS_NORMAL);
		return query.getObject(Box.class);
	}

	public List<Box> getBoxListByCdn(long userId, String name, byte checkflg,
			byte boxStatus, int begin, int size) {
		StringBuilder sql = new StringBuilder("select * from box where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (checkflg > -1) {
			sql.append(" and checkflg=?");
			olist.add(checkflg);
		}
		if (boxStatus > -1) {
			sql.append(" and boxstatus=?");
			olist.add(boxStatus);
		}
		sql.append(" order by boxid desc");
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	public List<Box> getBoxListByUid(long uid, int begin, int size) {
		Query query = this.manager.createQuery();
		Date d = new Date();
		return query.listEx(Box.class, "uid=? and begintime<=? and endtime>?",
				new Object[] { uid, d, d }, "boxid desc", begin, size);
	}

	public Map<Long, Box> getBoxMapInId(String idStr) {
		StringBuilder sql = new StringBuilder(
				"select * from box where boxid in (");
		sql.append(idStr).append(")");
		Query query = this.manager.createQuery();
		List<Box> list = query.listBySqlEx("ds1", sql.toString(), Box.class);
		Map<Long, Box> map = new HashMap<Long, Box>();
		for (Box o : list) {
			map.put(o.getBoxId(), o);
		}
		return map;
	}

	public BoxPrize getBoxPrize(long prizeId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(BoxPrize.class, prizeId);
	}

	public List<BoxPrize> getBoxPrizeListByBoxId(long boxId) {
		Query query = this.manager.createQuery();
		return query.listEx(BoxPrize.class, "boxid=?", new Object[] { boxId },
				"prizeid asc");
	}

	public List<Box> getEndBoxList(long userId, String name, int begin, int size) {
		StringBuilder sql = new StringBuilder("select * from box where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" and endtime<? order by boxid desc");
		Date d = new Date();
		olist.add(d);
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	public List<Box> getNotBeginBoxList(long userId, String name, int begin,
			int size) {
		StringBuilder sql = new StringBuilder(
				"select * from box where checkflg=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Box.CHECKFLG_CHECKOK);
		if (userId > 0) {
			sql.append(" and userid=?");
			olist.add(userId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" and begintime>? order by boxid desc");
		olist.add(new Date());
		Query query = this.manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Box.class, olist);
	}

	public List<UserBoxOpen> getUserBoxOpenListByBoxId(long boxId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(UserBoxOpen.class);
		query.where("boxid=?").setParam(boxId).orderByDesc("sysid");
		return query.list(begin, size, UserBoxOpen.class);
	}

	public List<UserBoxPrize> getUserBoxPrizeListByBoxId(long boxId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBoxPrize.class, "boxid=?",
				new Object[] { boxId }, "sysid desc", begin, size);
	}

	public List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndDrawflg(long boxId,
			byte drawflg, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBoxPrize.class, "boxid=? and drawflg=?",
				new Object[] { boxId, drawflg }, "prizeid asc", begin, size);
	}

	public List<UserBoxPrize> getUserBoxPrizeListByUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBoxPrize.class, "userid=?",
				new Object[] { userId }, "sysid desc", begin, size);
	}

	private boolean validateWhenOpen(Box box, long userId,
			BoxOpenResult boxOpenResult) {
		if (box == null) {
			return false;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_STOP) {
			boxOpenResult.setErrorCode(Err.BOX_STOP);
			return false;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_PAUSE) {
			boxOpenResult.setErrorCode(Err.BOX_PAUSE);
			return false;
		}
		int remain = box.getTotalCount() - box.getOpenCount();
		if (remain == 0) {
			boxOpenResult.setErrorCode(Err.BOX_OPENOUT);
			return false;
		}
		if (box.getEndTime().getTime() < System.currentTimeMillis()) {
			boxOpenResult.setErrorCode(Err.BOX_EXPIRED);
			return false;
		}
		int validateResult = this.validatePretype(box, userId);
		if (validateResult != 0) {
			boxOpenResult.setErrorCode(validateResult);
			return false;
		}
		return true;
	}

	public BoxOpenResult openBox2(long userId, long boxId) {
		BoxOpenResult boxOpenResult = new BoxOpenResult();
		Date date = new Date();
		Box box = this.getBox(boxId);
		if (!this.validateWhenOpen(box, userId, boxOpenResult)) {
			return boxOpenResult;
		}
		int validateResult = this.validatePretype(box, userId);
		if (validateResult != 0) {
			boxOpenResult.setErrorCode(validateResult);
			return boxOpenResult;
		}
		int remain = box.getTotalCount() - box.getOpenCount();
		BoxPrize award = this.getAwardPrize(boxId, remain);// 中奖的物品id
		// 更新宝箱开启数量
		box.setOpenCount(box.getOpenCount() + 1);
		Query query = this.manager.createQuery();
		query.addField("opencount", box.getOpenCount());
		if (box.getOpenCount() == box.getTotalCount()) {
			query.addField("boxstatus", Box.BOX_STATUS_PAUSE);
		}
		query.updateById(Box.class, boxId);
		long awardPrizeId = 0;
		if (award != null) {// 奖品表
			awardPrizeId = award.getPrizeId();
			// 更新奖品剩余
			query.addField("remain", award.getRemain() - 1);
			query.update(BoxPrize.class, "boxid=? and prizeid=?", new Object[] {
					boxId, award.getPrizeId() });
			boxOpenResult.setUserBoxPrize(this
					.createUserBoxPrize(award, userId));
			boxOpenResult.setErrorCode(0);
		}
		// 开箱表
		UserBoxOpen userBoxOpen = new UserBoxOpen();
		userBoxOpen.setBoxId(boxId);
		userBoxOpen.setUserId(userId);
		userBoxOpen.setCreateTime(date);
		userBoxOpen.setPrizeId(awardPrizeId);
		this.createUserBoxOpen(userBoxOpen);
		return boxOpenResult;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("mmss");

	/**
	 * 随即生成序列号和密码
	 * 
	 * @param boxId
	 * @param prizeId
	 * @param userId
	 * @return
	 *         2010-4-16
	 */
	private UserBoxPrize createUserBoxPrize(BoxPrize boxPrize, long userId) {
		long boxId = boxPrize.getBoxId();
		long prizeId = boxPrize.getPrizeId();
		UserBoxPrize userBoxPrize = new UserBoxPrize();
		userBoxPrize.setUserId(userId);
		userBoxPrize.setCreateTime(new Date());
		userBoxPrize.setDrawTime(userBoxPrize.getCreateTime());
		userBoxPrize.setPrizeId(prizeId);
		userBoxPrize.setBoxId(boxId);
		userBoxPrize.setDrawflg(UserBoxPrize.DRAWFLG_N);
		Query query = this.manager.createQuery();
		userBoxPrize.setSysId(query.insertObject(userBoxPrize).longValue());
		if (boxPrize.isUseSignal()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(userBoxPrize.getCreateTime());
			String pwd = new StringBuilder(sdf.format(userBoxPrize
					.getCreateTime())).append(DataUtil.getRandom(2)).toString();
			String v = String.valueOf(userBoxPrize.getSysId());
			String num = new StringBuilder(DataUtil.getRandom(2)).append(
					v.substring(v.length() - 4)).toString();
			userBoxPrize.setPrizeNum(num);
			userBoxPrize.setPrizePwd(pwd);
			query.updateObject(userBoxPrize);
		}
		return userBoxPrize;
	}

	public static void main(String[] args) {
		String v = "1234";
		P.println(v.substring(v.length() - 4));
	}

	public synchronized void pauseBox(long boxId) {
		this.updateBoxStatus(boxId, Box.BOX_STATUS_PAUSE);
	}

	public synchronized void stopBox(long boxId) {
		this.updateBoxStatus(boxId, Box.BOX_STATUS_STOP);
		this.updateBoxKey(boxId, null);
	}

	public void updateBox(Box box) throws BoxKeyDuplicateException,
			PrizeCountMoreThanBoxCountException {
		Query query = this.manager.createQuery();
		int premian = query.sum("remain", BoxPrize.class, "boxid=?",
				new Object[] { box.getBoxId() }).intValue();
		int boxremain = box.getTotalCount() - box.getOpenCount();
		if (boxremain < premian) {
			throw new PrizeCountMoreThanBoxCountException("prize remain [ "
					+ premian + " ],box remain [ " + boxremain + " ]");
		}
		query.updateObject(box);
		if (!this.updateBoxKey(box.getBoxId(), box.getBoxKey())) {
			throw new BoxKeyDuplicateException(" key duplicate [ "
					+ box.getBoxKey() + " ]");
		}
	}

	public synchronized void updateBoxForCheck(long boxId) {
		Query query = this.manager.createQuery();
		query.addField("checkflg", Box.CHECKFLG_UNCHECK);
		query.updateById(Box.class, boxId);
	}

	public boolean updateBoxKey(long boxId, String boxKey) {
		if (!DataUtil.isEmpty(boxKey)) {
			Box o = this.getBox(boxId);
			CmdData cmdData = null;
			// 查看是否已经存在宝箱的数据
			cmdData = this.cmdDataService.getCmdDataByOidAndOtype(boxId,
					CmdData.OTYPE_BOX);
			if (cmdData == null) {// 不存在宝箱数据
				// insert
				cmdData = new CmdData();
				cmdData.setEndflg(CmdData.ENDFLG_Y);
				cmdData.setEndTime(o.getEndTime());
				cmdData.setName(boxKey);
				cmdData.setOid(boxId);
				cmdData.setOtype(CmdData.OTYPE_BOX);
				if (this.cmdDataService.createCmdData(cmdData)) {
					this.updateBoxKeyData(boxId, boxKey);
					return true;
				}
				return false;// 存在重复指令
			}
			cmdData.setEndTime(o.getEndTime());
			cmdData.setName(boxKey);
			if (this.cmdDataService.updateCmdData(cmdData)) {
				this.updateBoxKeyData(boxId, boxKey);
				return true;
			}
			return false;
		}
		this.updateBoxKeyData(boxId, null);
		CmdData cmdData = this.cmdDataService.getCmdDataByOidAndOtype(boxId,
				CmdData.OTYPE_BOX);
		if (cmdData != null) {
			this.cmdDataService.deleteCmdData(cmdData.getCmdId());
			return true;
		}
		return true;
	}

	private void updateBoxKeyData(long boxId, String boxKey) {
		Query query = this.manager.createQuery();
		query.addField("boxkey", boxKey);
		query.updateById(Box.class, boxId);
	}

	public void updateBoxPrize(BoxPrize boxPrize)
			throws PrizeTotalCountException, PrizeRemainException {
		BoxPrize dbObj = this.getBoxPrize(boxPrize.getPrizeId());
		// 查看添加或减少多少
		int add = boxPrize.getPcount() - dbObj.getPcount();
		boxPrize.setRemain(dbObj.getRemain() + add);
		Box box = this.getBox(boxPrize.getBoxId());
		if (box == null) {
			return;
		}
		List<BoxPrize> list = this.getBoxPrizeListByBoxId(box.getBoxId());
		for (BoxPrize o : list) {
			if (o.getPrizeId() == boxPrize.getPrizeId()) {
				list.remove(o);
				break;
			}
		}
		int sum = boxPrize.getRemain();
		for (BoxPrize o : list) {
			sum = o.getRemain() + sum;
		}
		int remain = box.getTotalCount() - box.getOpenCount();
		if (sum > remain) {
			throw new PrizeTotalCountException("total prize count [ " + sum
					+ " ] more than box totalcount [ " + box.getTotalCount()
					+ " ]");
		}
		if (add < 0) {
			// 如果减少的数量比剩余数量还少，就不能更新
			int add_abs = -add;
			if (add_abs > dbObj.getRemain()) {
				throw new PrizeRemainException("remain count out of size [ "
						+ dbObj.getRemain() + " ]");
			}
		}
		Query query = this.manager.createQuery();
		query.updateObject(boxPrize);
	}

	public void updateBoxStatus(long boxId, byte boxStatus) {
		Query query = this.manager.createQuery();
		query.addField("boxstatus", boxStatus);
		query.updateById(Box.class, boxId);
	}

	public void updateUid(long companyId, long uid) {
		Query query = this.manager.createQuery();
		query.addField("uid", uid);
		query.update(Box.class, "companyid=?", new Object[] { companyId });
	}

	private int validatePretype(Box box, long userId) {
		Query query = this.manager.createQuery();
		if (box.getPretype() > 0) {
			Date pt = new Date();
			// 检查开箱频率限制
			long p = pt.getTime()
					- BoxPretypeUtil.getBoxPretypeTime(box.getPretype());
			pt.setTime(p);
			int count = query.count(UserBoxOpen.class,
					"boxid=? and userid=? and createtime>?", new Object[] {
							box.getBoxId(), userId, pt });
			if (count >= box.getPrecount()) {
				return Err.BOX_OUT_OF_LIMIT;
			}
		}
		return 0;
	}

	public void setUserBoxPrizeDrawed(long sysId) {
		UserBoxPrize userBoxPrize = this.getUserBoxPrize(sysId);
		if (userBoxPrize != null) {
			userBoxPrize.setDrawflg(UserBoxPrize.DRAWFLG_Y);
			userBoxPrize.setDrawTime(new Date());
			Query query = this.manager.createQuery();
			query.updateObject(userBoxPrize);
		}
	}

	public UserBoxPrize getUserBoxPrize(long sysId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserBoxPrize.class, sysId);
	}

	public UserBoxPrize getUserBoxPrizeByBoxIdAndNumAndPwd(long boxId,
			String num, String pwd) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserBoxPrize.class,
				"boxid=? and prizenum=? and prizepwd=?", new Object[] { boxId,
						num, pwd });
	}

	public void updateBoxPrizePath(long prizeId, String path) {
		Query query = this.manager.createQuery();
		query.addField("path", path);
		query.updateById(BoxPrize.class, prizeId);
	}

	public Map<Long, BoxPrize> getBoxPrizeMapInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		List<BoxPrize> list = query.listInField(BoxPrize.class, null, null,
				"prizeid", idList, null);
		Map<Long, BoxPrize> map = new HashMap<Long, BoxPrize>();
		for (BoxPrize o : list) {
			map.put(o.getPrizeId(), o);
		}
		return map;
	}

	public void updateBoxPinkFlg(long boxId, byte pinkflg) {
		Query query = this.manager.createQuery();
		query.addField("pinkflg", pinkflg);
		query.updateById(Box.class, boxId);
	}

	public void updateBoxPinkFlgTop(long boxId) {
		Box box = this.getBox(boxId);
		Query query = this.manager.createQuery();
		query.addField("pinkflg", Box.PINKFLG_N);
		if (box.getCityId() > 0) {
			// 撤销地区置顶
			query.update(Box.class, "cityid=? and pinkflg=?", new Object[] {
					box.getCityId(), Box.PINKFLG_TOP });
		}
		else {
			// 撤销全球置顶
			query.update(Box.class, "cityid=? and pinkflg=?", new Object[] { 0,
					Box.PINKFLG_TOP });
		}
		this.updateBoxPinkFlg(boxId, Box.PINKFLG_TOP);
	}

	public void deleteUserBoxPrize(long sysId) {
		Query query = this.manager.createQuery();
		query.deleteById(UserBoxPrize.class, sysId);
	}

	public void updateBoxCmppink(long boxId, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.addField("cmppinktime", new Date());
		query.updateById(Box.class, boxId);
	}

	public List<Box> getBoxListByCompanyId(long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Box.class, "companyid=?",
				new Object[] { companyId }, "boxid desc", begin, size);
	}

	public List<UserBoxPrize> getUserBoxPrizeListByBoxIdAndNumAndPwd(
			long boxId, String num, String pwd, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBoxPrize.class,
				"boxid=? and prizenum=? and prizepwd=?", new Object[] { boxId,
						num, pwd }, "sysid desc", begin, size);
	}
}