package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.HkAd;
import com.hk.bean.HkAdView;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.HkAdService;
import com.hk.svr.pub.Err;

public class HkAdServiceImpl implements HkAdService {

	@Autowired
	private QueryManager manager;

	public void createHkAd(HkAd hkAd) {
		if (hkAd.getCreateTime() == null) {
			hkAd.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		long oid = query.insertObject(hkAd).longValue();
		hkAd.setOid(oid);
	}

	public void deleteHkAd(long oid) {
		Query query = this.manager.createQuery();
		query.delete(HkAdView.class, "adoid=?", new Object[] { oid });
		query.deleteById(HkAd.class, oid);
	}

	public List<HkAd> getHkAdByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(HkAd.class, "userid=?", new Object[] { userId },
				"oid desc", begin, size);
	}

	public HkAdView getHkAdViewByAdoidAndIpAndUdate(long adoid, String ip,
			int udate) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(HkAdView.class, "adoid=? and ip=? andudate=?",
				new Object[] { adoid, ip, udate });
	}

	public int updateHkAd(HkAd hkAd) {
		Query query = this.manager.createQuery();
		if (hkAd.getTotalViewCount() < hkAd.getViewCount()) {
			return Err.HKAD_TOTALVIEWCOUNT_LESSTHAN_VIEWCOUNT;
		}
		query.updateObject(hkAd);
		return Err.SUCCESS;
	}

	public void createHkAdView(HkAdView hkAdView) {
		Query query = this.manager.createQuery();
		query.insertObject(hkAdView);
	}

	public HkAd getHkAd(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(HkAd.class, oid);
	}

	public void updateUseflg(long oid, byte useflg) {
		Query query = this.manager.createQuery();
		query.addField("useflg", useflg);
		query.updateById(HkAd.class, oid);
	}

	public List<HkAd> getHkAdListByCityIdForUsefulNotInId(int cityId,
			byte showflg, List<Long> idList, int begin, int size) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from hkad where cityid=? and showflg=?");
		if (idList.size() > 0) {
			sql.append(" and oid not in(");
			for (Long l : idList) {
				sql.append(l).append(",");
			}
			sql.deleteCharAt(sql.length() - 1).append(")");
		}
		sql.append(" order by oid asc");
		return query.listBySql("ds1", sql.toString(), begin, size, HkAd.class,
				cityId, showflg);
	}

	public int countHkAdByCityIdForUsefulNotInId(int cityId, byte showflg,
			List<Long> idList) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select count(*) from hkad where cityid=? and showflg=?");
		if (idList.size() > 0) {
			sql.append(" and oid not in(");
			for (Long l : idList) {
				sql.append(l).append(",");
			}
			sql.deleteCharAt(sql.length() - 1).append(")");
		}
		return query.countBySql("ds1", sql.toString(), cityId, showflg);
	}

	public void viewHkAd(String viewerId, long adoid, String ip, int add) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int udate = Integer.valueOf(new StringBuilder(year).append(month)
				.append(date).toString());
		Query query = this.manager.createQuery();
		HkAdView hkAdView = query.getObjectEx(HkAdView.class,
				"viewerid=? and adoid=? and udate=?", new Object[] { viewerId,
						adoid, udate });
		// 用户当前没有访问过此广告
		if (hkAdView == null) {
			hkAdView = new HkAdView();
			hkAdView.setAdoid(adoid);
			hkAdView.setViewerId(viewerId);
			hkAdView.setIp(ip);
			hkAdView.setUcount(1);
			hkAdView.setUdate(udate);
			query.insertObject(hkAdView);
			// 广告浏览数+add
			// this.addViewCount(query, adoid, add);
		}
		// 用户访问过
		else {
			if (hkAdView.getUcount() < 3) {
				hkAdView.addUcount(add);
				query.addField("ucount", hkAdView.getUcount());
				query.update(HkAdView.class, "oid=? and ucount<=?",
						new Object[] { hkAdView.getOid(), 3 });
				// 广告浏览数+add
				// this.addViewCount(query, adoid, add);
			}
		}
		HkAd hkAd = this.getHkAd(adoid);
		if (hkAd != null) {// 已经超过数量，将暂停显示
			if (hkAd.getTotalViewCount() <= hkAd.getViewCount()) {
				hkAd.setViewCount(hkAd.getTotalViewCount());
				hkAd.setUseflg(HkAd.USEFLG_N);
				this.updateHkAd(hkAd);
			}
		}
	}

	public List<Long> getHkAdIdListByViewerIdAndUdateForViewOk(String viewerId,
			int udate, int ucount) {
		Query query = this.manager.createQuery();
		String sql = "select adoid from hkadview where viewerid=? and udate=? and ucount=?";
		return query.listBySqlEx("ds1", sql, Long.class, viewerId, udate,
				ucount);
	}
}