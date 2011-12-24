package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.bean.HkObjKeyTagOrder;
import com.hk.bean.HkObjKeyTagOrderDef;
import com.hk.bean.KeyTag;
import com.hk.bean.KeyTagSearchInfo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.HkObjKeyTagOrderService;
import com.hk.svr.KeyTagService;
import com.hk.svr.OrderDefService;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;
import com.hk.svr.pub.HkSvrUtil;

public class HkObjKeyTagOrderServiceImpl implements HkObjKeyTagOrderService {
	@Autowired
	QueryManager manager;

	@Autowired
	CompanyService companyService;

	@Autowired
	OrderDefService orderDefService;

	@Autowired
	KeyTagService keyTagService;

	public void calculate(HkObjKeyTagOrder hkObjKeyTagOrder) {
		if (hkObjKeyTagOrder.isStop()) {
			return;
		}
		if (hkObjKeyTagOrder.getPday() <= 0) {
			return;
		}
		Company company = this.companyService.getCompany(hkObjKeyTagOrder
				.getHkObjId());
		if (hkObjKeyTagOrder.getMoney() > company.getMoney()) {// 余额不足
			return;
		}
		this.companyService.addMoney(hkObjKeyTagOrder.getHkObjId(),
				-hkObjKeyTagOrder.getMoney());
		Query query = manager.createQuery();
		query.addField("pday", hkObjKeyTagOrder.getPday() - 1);
		query.update(HkObjKeyTagOrder.class, "oid=?",
				new Object[] { hkObjKeyTagOrder.getOid() });
	}

	public void addKeyTagSerachCount(long tagId, int amount) {
		Query query = manager.createQuery();
		query.addField("searchcount", "add", amount);
		query.update(KeyTag.class, "tagid=?", new Object[] { amount });
	}

	public void setKeyTagSearchCountInfo(long tagId) {
		Query query = manager.createQuery();
		query.addField("searchcount", "add", 1);
		query.update(KeyTag.class, "tagid=?", new Object[] { tagId });
		// 按月统计
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		KeyTagSearchInfo o = query.getObjectEx(KeyTagSearchInfo.class,
				"tagid=? and year=? and month=?", new Object[] { tagId, year,
						month });
		if (o != null) {// 如果有此数据就更新
			query.addField("searchcount", "add", 1);
			query.update(KeyTagSearchInfo.class, "oid=?", new Object[] { o
					.getOid() });
		}
		else {
			o = new KeyTagSearchInfo();
			o.setMonth(month);
			o.setYear(year);
			o.setTagId(tagId);
			o.setSearchCount(1);
			query.addField("tagid", o.getTagId());
			query.addField("year", o.getYear());
			query.addField("month", o.getMonth());
			query.addField("searchcount", o.getSearchCount());
			query.insert(KeyTagSearchInfo.class);
		}
	}

	public List<KeyTagSearchInfo> getKeyTagSearchInfoListByYearAndMonth(
			int year, int month, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(KeyTagSearchInfo.class, "year=? and month=?",
				new Object[] { year, month }, "searchcount desc", begin, size);
	}

	public int countHkObjKeyTagOrderListByTagIdAndCityId(long tagId, int cityId) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.count(HkObjKeyTagOrder.class, "tagid=?",
					new Object[] { tagId });
		}
		return query.count(HkObjKeyTagOrder.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId });
	}

	public List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByTagIdAndCityId(
			long tagId, int cityId, int begin, int size) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.listEx(HkObjKeyTagOrder.class, "tagid=?",
					new Object[] { tagId }, "money desc", begin, size);
		}
		return query.listEx(HkObjKeyTagOrder.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId }, "money desc", begin, size);
	}

	public HkObjKeyTagOrder getHkObjKeyTagOrder(long tagId, long objId,
			int cityId) {
		Query query = manager.createQuery();
		return query.getObjectEx(HkObjKeyTagOrder.class,
				"tagid=? and hkobjid=? and cityid=?", new Object[] { tagId,
						objId, cityId });
	}

	public boolean createHkObjKeyTagOrder(HkObjKeyTagOrder hkObjKeyTagOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException {
		keyTagService.createHkObjKeyTag(hkObjKeyTagOrder.getTagId(),
				hkObjKeyTagOrder.getHkObjId());
		if (this.isDuplicate(hkObjKeyTagOrder)) {
			return this.updateHkObjKeyTagOrder(hkObjKeyTagOrder);
		}
		// 查看最小竞价
		int minmoney = 0;
		HkObjKeyTagOrderDef hkObjKeyTagOrderDef = this.orderDefService
				.getHkObjKeyTagOrderDef(hkObjKeyTagOrder.getTagId(),
						hkObjKeyTagOrder.getCityId());
		if (hkObjKeyTagOrderDef == null) {
			minmoney = HkSvrUtil.getMinOrderMoney();
		}
		else {
			minmoney = hkObjKeyTagOrderDef.getMoney();
		}
		if (hkObjKeyTagOrder.getMoney() < minmoney) {// 低于最小竞价
			SmallerThanMinMoneyException e = new SmallerThanMinMoneyException(
					"must bigger than minmoney [ "
							+ hkObjKeyTagOrder.getMoney() + " , " + minmoney
							+ " ]");
			e.setMinmoney(minmoney);
			throw e;
		}
		Company company = this.companyService.getCompany(hkObjKeyTagOrder
				.getHkObjId());
		if (company.getMoney() < hkObjKeyTagOrder.getMoney()
				* hkObjKeyTagOrder.getPday()) {// 余额不足
			throw new NoEnoughMoneyException("no enough money [ "
					+ company.getMoney() + " , " + hkObjKeyTagOrder.getMoney()
					* hkObjKeyTagOrder.getPday() + " ]");
		}
		this.companyService.addMoney(hkObjKeyTagOrder.getHkObjId(),
				-hkObjKeyTagOrder.getMoney());
		hkObjKeyTagOrder.setUtime(new Date());
		Query query = manager.createQuery();
		query.addField("tagid", hkObjKeyTagOrder.getTagId());
		query.addField("hkobjid", hkObjKeyTagOrder.getHkObjId());
		query.addField("cityid", hkObjKeyTagOrder.getCityId());
		query.addField("money", hkObjKeyTagOrder.getMoney());
		query.addField("stopflg", hkObjKeyTagOrder.getStopflg());
		query.addField("pday", hkObjKeyTagOrder.getPday());
		query.addField("utime", hkObjKeyTagOrder.getUtime());
		query.addField("userid", hkObjKeyTagOrder.getUserId());
		long id = query.insert(HkObjKeyTagOrder.class).longValue();
		hkObjKeyTagOrder.setOid(id);
		return true;
	}

	public HkObjKeyTagOrder getHkObjKeyTagOrder(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(HkObjKeyTagOrder.class, oid);
	}

	public List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByObjId(long objId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(HkObjKeyTagOrder.class, "hkobjid=?",
				new Object[] { objId }, "oid desc", begin, size);
	}

	public List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByUserId(long userId,
			int cityId, int begin, int size) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.listEx(HkObjKeyTagOrder.class, "userid=?",
					new Object[] { userId }, "oid desc", begin, size);
		}
		return query.listEx(HkObjKeyTagOrder.class, "userid=? and cityid=?",
				new Object[] { userId, cityId }, "oid desc", begin, size);
	}

	public List<HkObjKeyTagOrder> getHkObjKeyTagOrderListByObjId(long objId,
			int cityId, int begin, int size) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.listEx(HkObjKeyTagOrder.class, "hkobjid=?",
					new Object[] { objId }, "oid desc", begin, size);
		}
		return query.listEx(HkObjKeyTagOrder.class, "hkobjid=? and cityid=?",
				new Object[] { objId, cityId }, "oid desc", begin, size);
	}

	public List<HkObjKeyTagOrder> getHkObjKeyTagOrderListForOrder(long tagId,
			int cityId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(HkObjKeyTagOrder.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId }, "money desc,utime asc", begin,
				size);
	}

	public void run(long oid) {
		this.setStopflg(oid, HkObjKeyTagOrder.STOPFLG_N);
	}

	public void stop(long oid) {
		this.setStopflg(oid, HkObjKeyTagOrder.STOPFLG_Y);
	}

	private void setStopflg(long oid, byte stopflg) {
		Query query = manager.createQuery();
		query.addField("stopflg", stopflg);
		query.update(HkObjKeyTagOrder.class, "oid=?", new Object[] { oid });
	}

	private boolean isDuplicate(HkObjKeyTagOrder hkObjKeyTagOrder) {
		Query query = manager.createQuery();
		if (query.count(HkObjKeyTagOrder.class,
				"hkobjid=? and tagid=? and cityid=?", new Object[] {
						hkObjKeyTagOrder.getHkObjId(),
						hkObjKeyTagOrder.getTagId(),
						hkObjKeyTagOrder.getCityId() }) > 0) {
			return true;
		}
		return false;
	}

	public boolean updateHkObjKeyTagOrder(HkObjKeyTagOrder hkObjKeyTagOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException {
		Query query = manager.createQuery();
		HkObjKeyTagOrder o = query.getObjectById(HkObjKeyTagOrder.class,
				hkObjKeyTagOrder.getOid());
		if (DataUtil.isSameDay(new Date(), o.getUtime())) {// 已经更新过
			return false;
		}
		Company company = this.companyService.getCompany(hkObjKeyTagOrder
				.getHkObjId());
		/* ********************** 计算方式与导航一样 **************************** */
		/* ********************** 判断差额 **************************** */
		int amount = hkObjKeyTagOrder.getMoney() - o.getMoney();
		if (amount > 0) {// 如果设置竞排价格大于当前价格，需要从余额中扣除当天差价
			if (company.getMoney() < amount) {// 余额不足扣除当前则异常抛出
				throw new NoEnoughMoneyException("no enough money [ "
						+ company.getMoney() + " , "
						+ hkObjKeyTagOrder.getMoney() + " ]");
			}
			this.companyService
					.addMoney(hkObjKeyTagOrder.getHkObjId(), -amount);
			company = this.companyService.getCompany(hkObjKeyTagOrder
					.getHkObjId());
		}
		// 如果竞价或者期限改变，按照新竞价和新期限进行计算余额，并判断新竞价是否低于最低竞价
		if (hkObjKeyTagOrder.getPday() != o.getPday()
				|| hkObjKeyTagOrder.getMoney() != o.getMoney()) {
			// 先判断是竞价是否改变，
			if (hkObjKeyTagOrder.getMoney() != o.getMoney()) {
				// 如果竞价改变判断是否低于最低竞价，
				int minmoney = 0;
				HkObjKeyTagOrderDef hkObjKeyTagOrderDef = this.orderDefService
						.getHkObjKeyTagOrderDef(hkObjKeyTagOrder.getTagId(),
								hkObjKeyTagOrder.getCityId());
				if (hkObjKeyTagOrderDef == null) {
					minmoney = HkSvrUtil.getMinOrderMoney();
				}
				else {
					minmoney = hkObjKeyTagOrderDef.getMoney();
				}
				if (hkObjKeyTagOrder.getMoney() < minmoney) {//
					SmallerThanMinMoneyException e = new SmallerThanMinMoneyException(
							"must bigger than minmoney [ "
									+ hkObjKeyTagOrder.getMoney() + " , "
									+ minmoney + " ]");
					e.setMinmoney(minmoney);
					throw e;
				}
			}
			int res = hkObjKeyTagOrder.getPday() * hkObjKeyTagOrder.getMoney();
			if (res > company.getMoney()) {// 超出酷币总额
				throw new NoEnoughMoneyException(
						"no enough money for remain [ " + company.getMoney()
								+ " , " + res + " ]");
			}
		}
		/* ************************ 判断end ************************* */
		hkObjKeyTagOrder.setUtime(new Date());
		query.addField("tagid", hkObjKeyTagOrder.getTagId());
		query.addField("hkobjid", hkObjKeyTagOrder.getHkObjId());
		query.addField("cityid", hkObjKeyTagOrder.getCityId());
		query.addField("money", hkObjKeyTagOrder.getMoney());
		query.addField("stopflg", hkObjKeyTagOrder.getStopflg());
		query.addField("pday", hkObjKeyTagOrder.getPday());
		query.addField("utime", hkObjKeyTagOrder.getUtime());
		query.addField("userid", hkObjKeyTagOrder.getUserId());
		query.update(HkObjKeyTagOrder.class, "oid=?",
				new Object[] { hkObjKeyTagOrder.getOid() });
		return true;
	}
}
