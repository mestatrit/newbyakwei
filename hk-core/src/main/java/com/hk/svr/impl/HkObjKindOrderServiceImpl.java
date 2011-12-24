package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.bean.HkObjKindOrder;
import com.hk.bean.HkObjOrderDef;
import com.hk.bean.HkOrder;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CompanyService;
import com.hk.svr.HkObjKindOrderService;
import com.hk.svr.OrderDefService;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;
import com.hk.svr.pub.HkSvrUtil;

public class HkObjKindOrderServiceImpl implements HkObjKindOrderService {
	@Autowired
	QueryManager manager;

	@Autowired
	CompanyService companyService;

	@Autowired
	OrderDefService orderDefService;

	public boolean createHkObjKindOrder(HkObjKindOrder hkObjKindOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException {
		Company company = this.companyService.getCompany(hkObjKindOrder
				.getHkObjId());
		HkObjKindOrder o = this.getHkObjKindOrder(hkObjKindOrder.getKindId(),
				hkObjKindOrder.getHkObjId(), hkObjKindOrder.getCityId());
		if (o != null) {
			o.setCityId(hkObjKindOrder.getCityId());
			o.setMoney(hkObjKindOrder.getMoney());
			o.setPday(hkObjKindOrder.getPday());
			this.updateHkObjKindOrder(o);
			return true;
		}
		HkObjOrderDef hkObjOrderDef = this.orderDefService.getHkObjOrderDef(
				HkObjOrderDef.KIND_LEVEL_2, company.getKindId(), hkObjKindOrder
						.getCityId());
		int minmoney = 0;
		// 设置最低竞排价格
		if (hkObjOrderDef == null) {
			minmoney = HkSvrUtil.getMinOrderMoney();
		}
		else {
			minmoney = hkObjOrderDef.getMoney();
		}
		if (hkObjKindOrder.getMoney() < minmoney) {
			SmallerThanMinMoneyException e = new SmallerThanMinMoneyException(
					"must bigger than minmoney [ " + hkObjKindOrder.getMoney()
							+ " , " + minmoney + " ]");
			e.setMinmoney(minmoney);
			throw e;
		}
		int res = hkObjKindOrder.getResultMoney();// 计算酷币总量
		if (company.getMoney() < res) {// 余额不足扣除当前则异常抛出
			throw new NoEnoughMoneyException("no enough money [ "
					+ company.getMoney() + " , " + res + " ]");
		}
		this.companyService.addMoney(hkObjKindOrder.getHkObjId(),
				hkObjKindOrder.getMoney());
		hkObjKindOrder.setUtime(new Date());
		Query query = manager.createQuery();
		query.addField("oid", hkObjKindOrder.getOid());
		query.addField("kindid", hkObjKindOrder.getKindId());
		query.addField("hkobjid", hkObjKindOrder.getHkObjId());
		query.addField("cityid", hkObjKindOrder.getCityId());
		query.addField("money", hkObjKindOrder.getMoney());
		query.addField("stopflg", hkObjKindOrder.getStopflg());
		query.addField("utime", hkObjKindOrder.getUtime());
		query.addField("pday", hkObjKindOrder.getPday());
		query.addField("userid", hkObjKindOrder.getUserId());
		query.insert(HkObjKindOrder.class);
		return true;
	}

	public HkObjKindOrder getHkObjKindOrder(int kindId, long objId, int cityId) {
		Query query = manager.createQuery();
		return query.getObjectEx(HkObjKindOrder.class,
				"kindid=? and hkobjid=? and cityid=?", new Object[] { kindId,
						objId, cityId });
	}

	public boolean updateHkObjKindOrder(HkObjKindOrder hkObjKindOrder)
			throws NoEnoughMoneyException, SmallerThanMinMoneyException {
		Company company = this.companyService.getCompany(hkObjKindOrder
				.getHkObjId());
		HkObjKindOrder o = this.getHkObjKindOrder(hkObjKindOrder.getOid());
		/* ********************** 判断差额 **************************** */
		int amount = hkObjKindOrder.getMoney() - o.getMoney();
		if (amount > 0) {// 如果设置竞排价格大于当前价格，需要从余额中扣除当天差价
			if (company.getMoney() < amount) {// 余额不足扣除当前则异常抛出
				throw new NoEnoughMoneyException("no enough money [ "
						+ company.getMoney() + " , "
						+ hkObjKindOrder.getMoney() + " ]");
			}
			this.companyService.addMoney(hkObjKindOrder.getHkObjId(), -amount);
			company = this.companyService.getCompany(hkObjKindOrder
					.getHkObjId());
		}
		// 如果竞价或者期限改变，按照新竞价和新期限进行计算余额，并判断新竞价是否低于最低竞价
		if (hkObjKindOrder.getPday() != o.getPday()
				|| hkObjKindOrder.getMoney() != o.getMoney()) {
			// 先判断是竞价是否改变，
			if (hkObjKindOrder.getMoney() != o.getMoney()) {
				// 如果竞价改变判断是否低于最低竞价，
				int minmoney = 0;
				HkObjOrderDef hkObjOrderDef = this.orderDefService
						.getHkObjOrderDef(HkObjOrderDef.KIND_LEVEL_2,
								hkObjKindOrder.getKindId(), hkObjKindOrder
										.getCityId());
				if (hkObjOrderDef == null) {
					minmoney = HkSvrUtil.getMinOrderMoney();
				}
				else {
					minmoney = hkObjOrderDef.getMoney();
				}
				if (hkObjKindOrder.getMoney() < minmoney) {//
					SmallerThanMinMoneyException e = new SmallerThanMinMoneyException(
							"must bigger than minmoney [ "
									+ hkObjKindOrder.getMoney() + " , "
									+ minmoney + " ]");
					e.setMinmoney(minmoney);
					throw e;
				}
			}
			int res = hkObjKindOrder.getResultMoney();
			if (res > company.getMoney()) {// 超出总额
				throw new NoEnoughMoneyException(
						"no enough money for remain [ " + company.getMoney()
								+ " , " + res + " ]");
			}
		}
		/* ************************ 判断end ************************* */
		hkObjKindOrder.setUtime(new Date());
		Query query = manager.createQuery();
		query.addField("oid", hkObjKindOrder.getOid());
		query.addField("kindid", hkObjKindOrder.getKindId());
		query.addField("hkobjid", hkObjKindOrder.getHkObjId());
		query.addField("cityid", hkObjKindOrder.getCityId());
		query.addField("money", hkObjKindOrder.getMoney());
		query.addField("stopflg", hkObjKindOrder.getStopflg());
		query.addField("utime", hkObjKindOrder.getUtime());
		query.addField("pday", hkObjKindOrder.getPday());
		query.addField("userid", hkObjKindOrder.getUserId());
		query.update(HkObjKindOrder.class, "oid=?",
				new Object[] { hkObjKindOrder.getOid() });
		return true;
	}

	public HkObjKindOrder getHkObjKindOrder(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(HkObjKindOrder.class, oid);
	}

	public List<HkObjKindOrder> getHkObjKindOrderList(int kindId, int cityId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(HkObjKindOrder.class, "kindid=? and cityid=?",
				new Object[] { kindId, cityId }, "money desc,utime desc",
				begin, size);
	}

	public List<HkObjKindOrder> getHkObjKindOrderListByUserId(long userId,
			int cityId, int begin, int size) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.listEx(HkObjKindOrder.class, "userid=?",
					new Object[] { userId }, "oid desc", begin, size);
		}
		return query.listEx(HkObjKindOrder.class, "userid=? and cityid=?",
				new Object[] { userId, cityId }, "oid desc", begin, size);
	}

	public List<HkObjKindOrder> getHkObjKindOrderListByObjId(long objId,
			int cityId, int begin, int size) {
		Query query = manager.createQuery();
		if (cityId < 0) {
			return query.listEx(HkObjKindOrder.class, "hkobjid=?",
					new Object[] { objId }, "oid desc", begin, size);
		}
		return query.listEx(HkObjKindOrder.class, "hkobjid=? and cityid=?",
				new Object[] { objId, cityId }, "oid desc", begin, size);
	}

	private void updateStopflg(long oid, byte stopflg) {
		Query query = manager.createQuery();
		query.addField("stopflg", stopflg);
		query.update(HkObjKindOrder.class, "oid=?", new Object[] { oid });
	}

	public void run(long oid) {
		this.updateStopflg(oid, HkOrder.STOPFLG_N);
	}

	public void stop(long oid) {
		this.updateStopflg(oid, HkOrder.STOPFLG_Y);
	}
}