package com.hk.svr.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.*;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpSmsPortService;
import com.hk.svr.CompanyService;
import com.hk.svr.IpCityService;
import com.hk.svr.ZoneService;
import com.hk.svr.company.exception.NoAvailableCmpSmsPortException;
import com.hk.svr.pub.HkDataCompositor;
import com.hk.svr.pub.ImageConfig;
import com.hk.svr.pub.ZoneUtil;

public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CmpSmsPortService cmpSmsPortService;

	private final Log log = LogFactory.getLog(CompanyServiceImpl.class);

	private String idxDir;

	private String cmpTagRefDir;

	public void setCmpTagRefDir(String cmpTagRefDir) {
		this.cmpTagRefDir = cmpTagRefDir;
	}

	public void setIdxDir(String idxDir) {
		this.idxDir = idxDir;
	}

	public synchronized void createCompany(Company company, String ip) {
		company.setParentKindId(CompanyKindUtil.getParentKindId(company
				.getKindId()));
		Date date = new Date();
		Query query = manager.createQuery();
		HkObj hkObj = new HkObj();
		hkObj.setName(company.getName());
		hkObj.setCheckflg(HkObj.COMPANYSTATUS_UNCHECK);
		hkObj.setKindId(company.getKindId());
		this.createHkObj(hkObj);
		company.setCompanyId(hkObj.getObjId());
		company.setCompanyStatus(Company.COMPANYSTATUS_UNCHECK);
		company.setCreateTime(date);
		query.insertObject(company);
		this.buildCmpZoneInfo(company.getPcityId());
		// CompanyFeed feed = new CompanyFeed();
		// feed.setCompanyId(company.getCompanyId());
		// feed.setUserId(company.getCreaterId());
		// feed.setCreateTime(date);
		// if (ip != null) {
		// this.createCompanyFeed(feed, ip);
		// }
		this.createCompanyAward(company.getCompanyId(), company.getCreaterId());
		// if (company.getKindId() > 0) {
		// this.companyKindService.updateCompanyKindCmpCount(company
		// .getKindId());
		// }
	}

	private void createHkObj(HkObj hkObj) {
		Query query = manager.createQuery();
		query.addField("objid", hkObj.getObjId());
		query.addField("name", hkObj.getName());
		query.addField("checkflg", hkObj.getCheckflg());
		query.addField("kindId", hkObj.getKindId());
		long objId = query.insert(HkObj.class).longValue();
		hkObj.setObjId(objId);
	}

	private void updateHkObj(HkObj hkObj) {
		Query query = manager.createQuery();
		query.addField("name", hkObj.getName());
		query.addField("checkflg", hkObj.getCheckflg());
		query.addField("kindId", hkObj.getKindId());
		query.update(HkObj.class, "objid=?", new Object[] { hkObj.getObjId() });
	}

	private void createCompanyAward(long companyId, long createrId) {
		CompanyAward companyAward = new CompanyAward();
		companyAward.setCompanyId(companyId);
		companyAward.setCreaterId(createrId);
		companyAward.setAwardStatus(CompanyAward.AWARDSTATUS_N);
		Query query = manager.createQuery();
		query.addField("companyid", companyAward.getCompanyId());
		query.addField("createrid", companyAward.getCreaterId());
		query.addField("awardstatus", companyAward.getAwardStatus());
		query.addField("money", companyAward.getMoney());
		query.addField("awardhkb", companyAward.getAwardhkb());
		query.insert(CompanyAward.class);
	}

	@SuppressWarnings("unused")
	private void createCompanyFeed(CompanyFeed companyFeed, String ip) {
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		int cityId = 0;
		int rangeId = 0;
		if (range != null) {
			cityId = range.getCityId();
			rangeId = range.getRangeId();
		}
		companyFeed.setCityId(cityId);
		companyFeed.setRangeId(rangeId);
		Query query = manager.createQuery();
		query.addField("userid", companyFeed.getUserId());
		query.addField("companyid", companyFeed.getCompanyId());
		query.addField("cityid", companyFeed.getCityId());
		query.addField("rangeid", companyFeed.getRangeId());
		query.addField("createtime", companyFeed.getCreateTime());
		query.insert(CompanyFeed.class);
	}

	public synchronized void updateCompany(Company company) {
		company.setParentKindId(CompanyKindUtil.getParentKindId(company
				.getKindId()));
		Query query = manager.createQuery();
		query.updateObject(company);
		// 更新hkobj总表
		HkObj obj = this.getHkObj(company.getCompanyId());
		obj.setName(company.getName());
		obj.setKindId(company.getKindId());
		if (company.getCompanyStatus() >= Company.COMPANYSTATUS_CHECKED) {
			obj.setCheckflg(HkObj.COMPANYSTATUS_CHECKED);
		}
		else {
			obj.setCheckflg(HkObj.COMPANYSTATUS_CHECKFAIL);
		}
	}

	public HkObj getHkObj(long objId) {
		Query query = manager.createQuery();
		return query.getObjectById(HkObj.class, objId);
	}

	public void createCompanyReview(CompanyReview companyReview) {
		if (companyReview.getCreateTime() == null) {
			companyReview.setCreateTime(new Date());
		}
		// 如果用户评论没有打分，则调出用户以前打过的分更新评论中的打分值
		if (companyReview.getScore() == 0) {
			CompanyUserScore o = this.getCompanyUserScore(companyReview
					.getCompanyId(), companyReview.getUserId());
			if (o != null) {
				companyReview.setScore(o.getScore());
			}
		}
		Query query = manager.createQuery();
		query.addField("labaid", companyReview.getLabaId());
		query.addField("userid", companyReview.getUserId());
		query.addField("companyid", companyReview.getCompanyId());
		query.addField("score", companyReview.getScore());
		query.addField("content", companyReview.getContent());
		query.addField("longcontent", companyReview.getLongContent());
		query.addField("createtime", companyReview.getCreateTime());
		query.addField("sendfrom", companyReview.getSendFrom());
		query.addField("checkflg", companyReview.getCheckflg());
		query.insert(CompanyReview.class);
		if (companyReview.getScore() != 0) {
			this.gradeCompany(companyReview.getUserId(), companyReview
					.getCompanyId(), companyReview.getScore());
		}
		this.updateCompanyReviewCount(companyReview.getCompanyId());
		this.createUserCmpReview(companyReview.getUserId(), companyReview
				.getLabaId(), companyReview.getCompanyId());
	}

	public CompanyUserScore getCompanyUserScore(long companyId, long userId) {
		Query query = manager.createQuery();
		query.setTable(CompanyUserScore.class);
		query.where("companyid=? and userid=?").setParam(companyId).setParam(
				userId);
		return query.getObject(CompanyUserScore.class);
	}

	private void updateCompanyReviewCount(long companyId) {
		Query query = manager.createQuery();
		query.setTable(CompanyReview.class);
		query.where("companyid=?").setParam(companyId);
		int count = query.count();// 统计评论数量
		query.setTable(Company.class);
		query.addField("reviewcount", count);
		query.where("companyid=?").setParam(companyId);
		query.update();
	}

	public void updateCompanyReview(CompanyReview companyReview) {
		Query query = manager.createQuery();
		query.setTable(CompanyReview.class);
		query.addField("userid", companyReview.getUserId());
		query.addField("companyid", companyReview.getCompanyId());
		query.addField("score", companyReview.getScore());
		query.addField("content", companyReview.getContent());
		query.addField("longcontent", companyReview.getLongContent());
		query.addField("createtime", companyReview.getCreateTime());
		query.addField("checkflg", companyReview.getCheckflg());
		query.where("labaid=?").setParam(companyReview.getLabaId());
		query.update();
		if (companyReview.getScore() > 0) {
			this.gradeCompany(companyReview.getUserId(), companyReview
					.getCompanyId(), companyReview.getScore());
		}
	}

	private void createCompanyUserScore(CompanyUserScore companyUserScore) {
		Query query = manager.createQuery();
		query.setTable(CompanyUserScore.class);
		query.where("userid=? and companyid=?").setParam(
				companyUserScore.getUserId()).setParam(
				companyUserScore.getCompanyId());
		CompanyUserScore o = query.getObject(CompanyUserScore.class);
		if (o == null) {// 如果没有评分，创建评分
			query.addField("userid", companyUserScore.getUserId());
			query.addField("companyid", companyUserScore.getCompanyId());
			query.addField("score", companyUserScore.getScore());
			query.insert(CompanyUserScore.class);
		}
		else {// 更新评分
			query.setTable(CompanyUserScore.class);
			query.addField("userid", companyUserScore.getUserId());
			query.addField("companyid", companyUserScore.getCompanyId());
			query.addField("score", companyUserScore.getScore());
			query.where("userid=? and companyid=?").setParam(
					companyUserScore.getUserId()).setParam(
					companyUserScore.getCompanyId());
			query.update();
		}
	}

	public void gradeCompany(long userId, long companyId, int score) {
		CompanyUserScore o = new CompanyUserScore();
		o.setUserId(userId);
		o.setCompanyId(companyId);
		o.setScore(score);
		this.createCompanyUserScore(o);
		// 更新企业用户评分值和系统评分值
		Query query = manager.createQuery();
		List<CompanyUserScore> list = query.listEx(CompanyUserScore.class,
				"companyid=?", new Object[] { companyId });
		int totalScore = 0;// 计算总分
		int sysScore = 0;// 计算系统总分
		int totalVote = list.size();
		for (CompanyUserScore companyUserScore : list) {
			totalScore += companyUserScore.getScore();
			// sysScore += CompanyScoreConfig.getCompanyScoreConfig(
			// companyUserScore.getScore()).getSysScore();
		}
		query.setTable(Company.class);
		query.addField("totalscore", totalScore);
		query.addField("totalvote", totalVote);
		query.addField("sysscore", sysScore);
		query.where("companyid=?").setParam(companyId);
		query.update();
		// 更新用户评论中的打分
		query.setTable(CompanyReview.class);
		query.addField("score", score);
		query.where("userid=? and companyid=?").setParam(userId).setParam(
				companyId);
		query.update();
	}

	public Company getCompany(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(Company.class, companyId);
	}

	public void deleteCompanyReview(long labaId) {
		CompanyReview o = this.getCompanyReview(labaId);
		if (o == null) {
			return;
		}
		Query query = manager.createQuery();
		query.deleteById(CompanyReview.class, labaId);
		this.updateCompanyReviewCount(o.getCompanyId());
		// 更新usercmpreview
		CompanyReview o2 = query.getObject(CompanyReview.class,
				"companyid=? and userid=?", new Object[] { o.getCompanyId(),
						o.getUserId() }, "labaid desc");
		if (o2 == null) {
			this.deleteUserCmpReview(o.getUserId(), o.getCompanyId());
		}
		else {
			this.createUserCmpReview(o.getUserId(), o2.getLabaId(), o
					.getCompanyId());
		}
	}

	public CompanyReview getCompanyReview(long labaId) {
		Query query = manager.createQuery();
		return query.getObjectById(CompanyReview.class, labaId);
	}

	public List<CompanyReview> getCompanyReviewListByCompanyId(long companyId,
			int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyReview.class);
		query.where("companyid=?").setParam(companyId);
		query.orderByDesc("labaid");
		return query.list(begin, size, CompanyReview.class);
	}

	public List<CompanyReview> getCompanyReviewListByCompanyId(long companyId,
			byte checkflg, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyReview.class, "companyid=? and checkflg=?",
				new Object[] { companyId, checkflg }, "labaid desc", begin,
				size);
	}

	public List<CompanyReview> getCompanyReviewListByCompanyIdNoUser(
			long companyId, long noUserId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyReview.class);
		query.where("companyid=? and userid !=?").setParam(companyId).setParam(
				noUserId);
		query.orderByDesc("labaid");
		return query.list(begin, size, CompanyReview.class);
	}

	public Company getCompanyByName(String name) {
		Query query = manager.createQuery();
		query.setTable(Company.class);
		query.where("name=?").setParam(name);
		return query.getObject(Company.class);
	}

	public List<CompanyFeed> getCompanyFeedListByCity(int cityId, int begin,
			int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyFeed.class);
		query.where("cityid=?").setParam(cityId);
		query.orderByDesc("feedid");
		return query.list(begin, size, CompanyFeed.class);
	}

	public List<CompanyFeed> getCompanyFeedList(int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyFeed.class);
		query.orderByDesc("feedid");
		return query.list(begin, size, CompanyFeed.class);
	}

	public Map<Long, Company> getCompanyMapInId(List<Long> idList) {
		Map<Long, Company> map = new HashMap<Long, Company>();
		if (idList.size() == 0) {
			return map;
		}
		Query query = manager.createQuery();
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from company where companyid in ("
				+ sb.toString() + ")";
		List<Company> list = query.listBySql("ds1", sql, 0, idList.size(),
				Company.class);
		for (Company c : list) {
			map.put(c.getCompanyId(), c);
		}
		return map;
	}

	public List<Company> getCompanyListByCircleId(int circleId, int begin,
			int size) {
		Query query = manager.createQuery();
		String sql = "select c.* from company c,companybizcircle b where b.circleid=? and c.companyid=b.companyid order by b.companyid desc";
		return query
				.listBySql("ds1", sql, begin, size, Company.class, circleId);
	}

	public List<CompanyBizCircle> getCompanyBizCircleByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CompanyBizCircle.class);
	}

	public List<BizCircle> getBizCircleByCompanyId(long companyId) {
		Query query = manager.createQuery();
		String sql = "select b.* from bizcircle b,companybizcircle c where b.circleid=c.circleid and c.companyid=?";
		return query.listBySqlEx("ds1", sql, BizCircle.class, companyId);
	}

	public void createCompanyUserStatus(long companyId, long userId, byte status) {
		Query query = manager.createQuery();
		CompanyUserStatus o = query.getObjectEx(CompanyUserStatus.class,
				"companyid=? and userid=?", new Object[] { companyId, userId });
		if (o == null) {// 没有记录过状态，创建状态
			o = new CompanyUserStatus();
			o.setCompanyId(companyId);
			o.setUserId(userId);
			if (status == CompanyUserStatus.USERSTATUS_DONE) {
				o.setDoneStatus(CompanyUserStatus.OK_FLG);
			}
			else {
				o.setUserStatus(CompanyUserStatus.OK_FLG);
			}
			query.insertObject(o);
		}
		else {// 更新状态
			if (status == CompanyUserStatus.USERSTATUS_DONE) {
				o.setDoneStatus(CompanyUserStatus.OK_FLG);
			}
			else {
				o.setUserStatus(CompanyUserStatus.OK_FLG);
			}
			query.updateObject(o);
		}
	}

	public void updateHeadPath(long companyId, String headPath) {
		Query query = manager.createQuery();
		query.setTable(Company.class);
		query.addField("headpath", headPath);
		query.where("companyid=?").setParam(companyId);
		query.update();
	}

	public List<Company> getCompanyListByCreaterId(long createrId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(Company.class, "createrid=?",
				new Object[] { createrId }, "companyid desc", begin, size);
	}

	public List<Company> getCompanyListByUserId(long userId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(Company.class, "userid=?", new Object[] { userId },
				"companyid desc", begin, size);
	}

	public List<Company> getCompanyListByUserId(long userId) {
		Query query = manager.createQuery();
		return query.listEx(Company.class, "userid=?", new Object[] { userId },
				"companyid desc");
	}

	public void clearCompanyUser(long companyId, long userId) {
		this.setCompanyUser(companyId, 0);
	}

	public void createAuthCompany(AuthCompany authCompany) {
		authCompany.setMainStatus(AuthCompany.MAINSTATUS_UNCHECK);
		Query query = manager.createQuery();
		long sysId = query.insertObject(authCompany).longValue();
		authCompany.setSysId(sysId);
	}

	public void deleteAuthCompany(long sysId) {
		Query query = manager.createQuery();
		query.deleteById(AuthCompany.class, sysId);
	}

	public List<AuthCompany> getAuthCompanyList(String name, byte mainStatus,
			int begin, int size) {
		if (DataUtil.isEmpty(name)) {
			return this.getAuthCompanyList(mainStatus, begin, size);
		}
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select a.* from authcompany a,company c where a.companyid=c.companyid and name like ?");
		List<Object> olist = new ArrayList<Object>();
		olist.add("%" + name + "%");
		if (mainStatus >= AuthCompany.MAINSTATUS_CHECKFAIL) {
			sql.append("and a.mainstatus=?");
			olist.add(mainStatus);
		}
		sql.append(" order by a.sysid desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				AuthCompany.class, olist);
	}

	private List<AuthCompany> getAuthCompanyList(byte mainStatus, int begin,
			int size) {
		Query query = manager.createQuery();
		query.setTable(AuthCompany.class);
		if (mainStatus >= AuthCompany.MAINSTATUS_CHECKFAIL) {
			query.where("mainstatus=?").setParam(mainStatus);
		}
		query.orderByDesc("sysid");
		return query.list(begin, size, AuthCompany.class);
	}

	public void setCompanyUser(long companyId, long userId) {
		Query query = manager.createQuery();
		query.setTable(Company.class);
		query.addField("userid", userId);
		query.where("companyid=?").setParam(companyId);
		query.update();
	}

	public AuthCompany getAuthCompany(long sysId) {
		return manager.createQuery().getObjectById(AuthCompany.class, sysId);
	}

	public void updateAuthCompany(AuthCompany authCompany) {
		Query query = manager.createQuery();
		query.updateObject(authCompany);
	}

	public List<Company> getCompanyListByCdn(String name, byte companyStatus,
			byte freezeflg, int pcityId, int begin, int size) {
		StringBuilder sql = new StringBuilder("select * from company where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (!DataUtil.isEmpty(name)) {
			olist.add("%" + name + "%");
			sql.append(" and name like ?");
		}
		if (companyStatus > -2) {
			if (companyStatus == Company.COMPANYSTATUS_CHECKED) {
				sql.append(" and companystatus>=?");
			}
			else {
				sql.append(" and companystatus=?");
			}
			olist.add(companyStatus);
		}
		if (pcityId > 0) {
			sql.append(" and pcityid=?");
			olist.add(pcityId);
		}
		if (freezeflg >= 0) {
			sql.append(" and freezeflg=?");
			olist.add(freezeflg);
		}
		sql.append(" order by companyid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Company.class, olist);
	}

	public void updateCompanyStatus(long companyId, byte companyStatus) {
		Company company = this.getCompany(companyId);
		if (company == null) {
			return;
		}
		company.setCompanyStatus(companyStatus);
		Query query = manager.createQuery();
		query.updateObject(company);
		HkObj obj = this.getHkObj(companyId);
		if (companyStatus >= Company.COMPANYSTATUS_CHECKED) {
			obj.setCheckflg(HkObj.COMPANYSTATUS_CHECKED);
		}
		else {
			obj.setCheckflg(HkObj.COMPANYSTATUS_CHECKFAIL);
		}
		this.updateHkObj(obj);
	}

	public void checkAuthCompany(AuthCompany authCompany) {
		// authCompany.setEndTime(null);
		// authCompany.setProbationEndTime(null);
		this.updateAuthCompany(authCompany);
		Company company = this.getCompany(authCompany.getCompanyId());
		if (authCompany.getMainStatus() != AuthCompany.MAINSTATUS_CHECKED) {
			company.setUserId(0);
			this.updateCompany(company);
			CmpSmsPort cmpSmsPort = this.cmpSmsPortService
					.getCmpSmsPortByCompanyId(company.getCompanyId());
			if (cmpSmsPort != null) {
				cmpSmsPort.setCompanyId(0);
				this.cmpSmsPortService.updateCmpSmsPort(cmpSmsPort);
			}
		}
		else {
			company.setUserId(authCompany.getUserId());
			// // 如果正式认领后，修改足迹状态为开发中
			// if (authCompany.getCheckStatus() == AuthCompany.CHECKSTATUS_PAY)
			// {
			// company.setCompanyStatus(Company.COMPANYSTATUS_NORMAL);
			// }
			this.updateCompany(company);
			try {
				this.cmpSmsPortService.createAvailableCmpSmsPort(authCompany
						.getCompanyId());
			}
			catch (NoAvailableCmpSmsPortException e) {// 没有通道号码生成
				log.warn(e.getMessage());
			}
		}
	}

	public List<CompanyAward> getCompanyAwardList(byte status, int begin,
			int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyAward.class);
		if (status > -1) {
			query.where("awardstatus=?").setParam(status);
		}
		query.orderByDesc("companyid");
		return query.list(begin, size, CompanyAward.class);
	}

	public void updateCompanyAward(CompanyAward companyAward) {
		Query query = manager.createQuery();
		query.setTable(CompanyAward.class);
		query.addField("companyid", companyAward.getCompanyId());
		query.addField("createrid", companyAward.getCreaterId());
		query.addField("awardstatus", companyAward.getAwardStatus());
		query.addField("money", companyAward.getMoney());
		query.addField("awardhkb", companyAward.getAwardhkb());
		query.where("companyid=?").setParam(companyAward.getCompanyId());
		query.update();
	}

	public CompanyAward getCompanyAward(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(CompanyAward.class, companyId);
	}

	public List<CompanyMoney> getCompanyMoneyList(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		query.setTable(CompanyMoney.class);
		query.where("companyId=?").setParam(companyId);
		query.orderByDesc("sysid");
		return query.list(begin, size, CompanyMoney.class);
	}

	public CompanyMoney getFirstCompanyMoney(long companyId) {
		Query query = manager.createQuery();
		query.setTable(CompanyMoney.class);
		query.where("companyid=? and firstflg=?").setParam(companyId).setParam(
				CompanyMoney.FIRSTFLG_Y);
		return query.getObject(CompanyMoney.class);
	}

	public void createCompanyMoney(CompanyMoney companyMoney) {
		Calendar c = Calendar.getInstance();
		c.setTime(companyMoney.getEndTime());
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		companyMoney.setEndTime(c.getTime());
		companyMoney.setCreateTime(new Date());
		Query query = manager.createQuery();
		CompanyMoney first = this.getFirstCompanyMoney(companyMoney
				.getCompanyId());
		// 设置是否是第一次充值,为了奖励创建者所使用的标示(奖励为第一次充值的费用的比例)
		if (first != null) {
			companyMoney.setFirstflg(CompanyMoney.FIRSTFLG_N);
		}
		else {
			companyMoney.setFirstflg(CompanyMoney.FIRSTFLG_Y);
		}
		query.addField("companyid", companyMoney.getCompanyId());
		query.addField("opuserid", companyMoney.getOpuserId());
		query.addField("money", companyMoney.getMoney());
		query.addField("createtime", companyMoney.getCreateTime());
		query.addField("endtime", companyMoney.getEndTime());
		query.addField("firstflg", companyMoney.getFirstflg());
		long id = query.insert(CompanyMoney.class).longValue();
		companyMoney.setSysId(id);
		Company company = this.getCompany(companyMoney.getCompanyId());
		company
				.setTotalMoney(company.getTotalMoney()
						+ companyMoney.getMoney());
		this.updateCompany(company);
	}

	public CompanyMoney getCompanyMoney(long sysId) {
		Query query = manager.createQuery();
		return query.getObjectById(CompanyMoney.class, sysId);
	}

	public CompanyReview getLastCompanyReview(long companyId, long userId) {
		Query query = manager.createQuery();
		return query.getObject(CompanyReview.class, "companyid=? and userid=?",
				new Object[] { companyId, userId }, "labaid desc");
	}

	public List<CompanyReview> getUserCompanyReviewList(long companyId,
			long userId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyReview.class, "companyid=? and userid=?",
				new Object[] { companyId, userId }, "labaid desc", begin, size);
	}

	public int countUserCmpReview(long userId) {
		Query query = manager.createQuery();
		return query.count(UserCmpReview.class, "userid=?",
				new Object[] { userId });
	}

	public int countCompanyByCreaterId(long createrId) {
		Query query = manager.createQuery();
		return query.count(Company.class, "createrid=?",
				new Object[] { createrId });
	}

	public void tempUpdateScore() {
		Query query = manager.createQuery();
		List<CompanyUserScore> slist = query.listEx(CompanyUserScore.class);
		for (CompanyUserScore o : slist) {
			int old_score = o.getScore();
			int new_score = 0;
			if (old_score < 0) {
				new_score = old_score + 3;
			}
			else if (old_score > 0 && old_score < 2) {
				new_score = old_score + 2;
			}
			if (new_score != 0) {
				CompanyUserScore companyUserScore = new CompanyUserScore();
				companyUserScore.setCompanyId(o.getCompanyId());
				companyUserScore.setUserId(o.getUserId());
				companyUserScore.setScore(new_score);
				this.createCompanyUserScore(companyUserScore);
			}
		}
		List<CompanyReview> list = query.listEx(CompanyReview.class);
		for (CompanyReview o : list) {
			int old_score = o.getScore();
			int new_score = 0;
			if (old_score < 0) {
				new_score = old_score + 3;
			}
			else if (old_score > 0 && old_score < 2) {
				new_score = old_score + 2;
			}
			if (new_score != 0) {
				// 更新用户评论中的打分
				query.setTable(CompanyReview.class);
				query.addField("score", new_score);
				query.where("userid=? and companyid=?").setParam(o.getUserId())
						.setParam(o.getCompanyId());
				query.update();
			}
		}
		list = query.listEx(CompanyReview.class);
		for (CompanyReview o : list) {
			if (o.getScore() != 0) {
				this
						.gradeCompany(o.getUserId(), o.getCompanyId(), o
								.getScore());
			}
		}
	}

	public List<Company> getCompanyListEx(int kindId, int cityId, String name,
			Set<Long> noIdSet, HkDataCompositor compositor, int begin, int size) {
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder("select * from company where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		if (cityId > 0) {
			sql.append(" and pcityid=?");
			olist.add(cityId);
		}
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (noIdSet != null && noIdSet.size() > 0) {
			StringBuilder t = new StringBuilder();
			for (Long l : noIdSet) {
				t.append(l).append(",");
			}
			t.deleteCharAt(t.length() - 1);
			sql.append(" and companyid not in (" + t.toString() + ")");
		}
		if (compositor == null) {
			sql.append(" order by companyid desc");
		}
		else {
			sql.append(" order by ").append(compositor.getField());
			sql.append(" ").append(compositor.getOrderType());
			sql.append(",companyid desc");
		}
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Company.class, olist);
	}

	public List<Company> getCompanyListByMapInfo(int cityId, long noCompanyId,
			int minMarkerX, int maxMarderX, int minMarkerY, int maxMarkerY,
			int begin, int size) {
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from company where markerx>=? and markerx<=? and markery>=? and markery<=? and companyid!=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(minMarkerX);
		olist.add(maxMarderX);
		olist.add(minMarkerY);
		olist.add(maxMarkerY);
		olist.add(noCompanyId);
		if (cityId > 0) {
			sql.append(" and pcityid=?");
			olist.add(cityId);
		}
		sql.append(" order by checkincount desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Company.class, olist);
	}

	public void createCmpWatch(long companyId, long userId) {
		Query query = manager.createQuery();
		query.addField("companyid", companyId);
		query.addField("userid", userId);
		query.addField("duty", CmpWatch.DUTY_N);
		query.insert(CmpWatch.class);
	}

	public void deleteCmpWatch(long companyId, long userId) {
		Query query = manager.createQuery();
		query.delete(CmpWatch.class, "companyid=? and userid=?", new Object[] {
				companyId, userId });
	}

	public CmpWatch getCmpWatch(long companyId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpWatch.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public List<CmpWatch> getCmpWatchList(long companyId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpWatch.class, "companyid=?",
				new Object[] { companyId }, "sysid desc", begin, size);
	}

	public CmpWatch getDutyCmpWatch(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpWatch.class, "companyid=? and duty=?",
				new Object[] { companyId, CmpWatch.DUTY_Y });
	}

	public void setCmpWatchDuty(long companyId, long userId) {
		Query query = manager.createQuery();
		query.addField("duty", CmpWatch.DUTY_N);
		query.update(CmpWatch.class, "companyid=? and duty=?", new Object[] {
				companyId, CmpWatch.DUTY_Y });
		query.addField("duty", CmpWatch.DUTY_Y);
		query.update(CmpWatch.class, "companyid=? and userid=?", new Object[] {
				companyId, userId });
	}

	public void chgFreezeflgCompany(long companyId, byte freezeflg) {
		Company o = this.getCompany(companyId);
		if (o != null) {
			o.setFreezeflg(freezeflg);
			this.updateCompany(o);
		}
	}

	public void updateCompanyReviewCheckflg(long labaId, byte checkflg) {
		Query query = manager.createQuery();
		query.addField("checkflg", checkflg);
		query.update(CompanyReview.class, "labaid=?", new Object[] { labaId });
	}

	public void createCmpRecruit(CmpRecruit cmpRecruit) {
		this.deleteCmpRecruit(cmpRecruit.getCompanyId());
		Query query = manager.createQuery();
		query.addField("companyid", cmpRecruit.getCompanyId());
		query.addField("title", cmpRecruit.getTitle());
		query.addField("content", cmpRecruit.getContent());
		query.insert(CmpRecruit.class);
	}

	public void deleteCmpRecruit(long companyId) {
		Query query = manager.createQuery();
		query.deleteById(CmpRecruit.class, companyId);
	}

	public CmpRecruit getCmpRecruit(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpRecruit.class, companyId);
	}

	public void updateLogo(long companyId, File file, File file2)
			throws IOException {
		String dbPath = ImageConfig.getCompanyHeadDbPath(companyId);
		String headPath = ImageConfig.getCompanyHeadUploadPath(dbPath);
		DataUtil.copyFile(file, headPath, "logo.jpg");
		if (file2 != null) {
			DataUtil.copyFile(file2, headPath, "logo2.jpg");
		}
		Query query = manager.createQuery();
		query.addField("logopath", dbPath);
		if (file2 != null) {
			query.addField("logo2path", dbPath);
		}
		query.update(Company.class, "companyid=?", new Object[] { companyId });
	}

	public CmpTemplate getCmpTemplate(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpTemplate.class, companyId);
	}

	public CompanyUserStatus getCompanyUserStatus(long companyId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CompanyUserStatus.class,
				"companyid=? and userid=?", new Object[] { companyId, userId });
	}

	public Company getLastCreateCompany(long createrId) {
		Query query = manager.createQuery();
		query.setTable(Company.class);
		query.where("createrid=?").setParam(createrId);
		query.orderByDesc("companyId");
		return query.getObject(Company.class);
	}

	public void buildCmpZoneInfo(int cityId) {
		if (cityId <= 0) {
			return;
		}
		Pcity pcity = ZoneUtil.getPcity(cityId);
		if (pcity == null) {
			return;
		}
		Query query = manager.createQuery();
		CmpZoneInfo o = query.getObjectEx(CmpZoneInfo.class, "pcityid=?",
				new Object[] { cityId });
		if (o == null) {
			o = new CmpZoneInfo();
			o.setPcityId(cityId);
			o.setCmpcount(1);
			query.addField("pcityid", o.getPcityId());
			query.addField("cmpcount", o.getCmpcount());
			query.insert(CmpZoneInfo.class);
		}
		else {
			o.setCmpcount(o.getCmpcount() + 1);
			query.updateObject(o);
		}
	}

	public void initCmpZoneInfo() {
		String sql = "select pcityid,count(pcityid) from company where pcityid>0 group by pcityid";
		Query query = manager.createQuery();
		List<Object[]> list = query.listdata("ds1", sql);
		for (Object[] objs : list) {
			int pcityId = Integer.valueOf(objs[0].toString());
			int count = Integer.valueOf(objs[1].toString());
			CmpZoneInfo o = query.getObjectEx(CmpZoneInfo.class, "pcityid=?",
					new Object[] { pcityId });
			if (o == null) {
				o = new CmpZoneInfo();
				o.setPcityId(pcityId);
				o.setCmpcount(count);
				query.insertObject(o);
			}
			else {
				o.setCmpcount(count);
				query.updateObject(o);
			}
		}
	}

	public List<CmpZoneInfo> getCmpZoneInfoList() {
		Query query = manager.createQuery();
		return query.listEx(CmpZoneInfo.class, "cmpcount desc");
	}

	public void addHkb(CmpHkbLog cmpHkbLog) {
		Query query = manager.createQuery();
		query.addField("hkb", "add", cmpHkbLog.getAddcount());
		query.update(Company.class, "companyid=?", new Object[] { cmpHkbLog
				.getCompanyId() });
		this.createCmpHkbLog(cmpHkbLog);
	}

	private void createCmpHkbLog(CmpHkbLog cmpHkbLog) {
		cmpHkbLog.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("companyid", cmpHkbLog.getCompanyId());
		query.addField("userId", cmpHkbLog.getUserId());
		query.addField("hktype", cmpHkbLog.getHkbtype());
		query.addField("addcount", cmpHkbLog.getAddcount());
		query.addField("createTime", cmpHkbLog.getCreateTime());
		query.insert(CmpHkbLog.class);
	}

	public void createCmpAdminHkbLog(CmpAdminHkbLog cmpAdminHkbLog) {
		cmpAdminHkbLog.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("sysid", cmpAdminHkbLog.getSysId());
		query.addField("companyid", cmpAdminHkbLog.getCompanyId());
		query.addField("money", cmpAdminHkbLog.getMoney());
		query.addField("addflg", cmpAdminHkbLog.getAddflg());
		query.addField("opuserid", cmpAdminHkbLog.getOpuserId());
		query.addField("createtime", cmpAdminHkbLog.getCreateTime());
		query.addField("addcount", cmpAdminHkbLog.getAddCount());
		query.addField("remark", cmpAdminHkbLog.getRemark());
		query.insert(CmpAdminHkbLog.class);
	}

	public List<Company> getCompanyListInId(List<Long> idList, String orderSql) {
		if (idList.size() == 0) {
			return new ArrayList<Company>();
		}
		StringBuilder sb = new StringBuilder(
				"select * from company where companyid in(");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		if (orderSql == null) {
			sb.append(" order by companyid desc");
		}
		else {
			sb.append(orderSql);
		}
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sb.toString(), Company.class);
	}

	public List<Company> getCompanyListNearBy(long companyId, int parentKindId,
			int cityId, double markerX, double markerY, int begin, int size) {
		double def_distance = 0.004724058073698245;
		double min_x = markerX - def_distance;
		double min_y = markerY - def_distance;
		double max_x = markerX + def_distance;
		double max_y = markerY + def_distance;
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from company where parentkindid=? and markerx>=? and markerx<=? and markery>=? and markery<=? and companyid!=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(parentKindId);
		olist.add(min_x);
		olist.add(max_x);
		olist.add(min_y);
		olist.add(max_y);
		olist.add(companyId);
		if (cityId > 0) {
			sql.append(" and pcityid=?");
			olist.add(cityId);
		}
		sql.append(" order by checkincount desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Company.class, olist);
	}

	public List<Company> getCompanyListByPcityId(int pcityId, int begin,
			int size) {
		Query query = manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(Company.class, "pcityid=?",
					new Object[] { pcityId }, "checkincount desc", begin, size);
		}
		return query.listEx(Company.class, "checkincount desc", begin, size);
	}

	public List<Company> getCompanyListByPcityIdAndKindId(int pcityId,
			long kindId, int begin, int size) {
		Query query = manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(Company.class, "pcityid=? and kindid=?",
					new Object[] { pcityId, kindId }, "checkincount desc",
					begin, size);
		}
		return query.listEx(Company.class, "kindid=?", new Object[] { kindId },
				"checkincount desc", begin, size);
	}

	public List<Company> getCompanyList(int parentKindId, int kindId,
			int cityId, int begin, int size) {
		return this.getCompanyList(parentKindId, kindId, cityId,
				"companyid desc", begin, size);
	}

	public List<HkObj> getHkObjListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<HkObj>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from hkobj where objid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") order by objid desc");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), HkObj.class);
	}

	public boolean addMoney(long companyId, int money) {
		Query query = manager.createQuery();
		query.addField("money", "add", money);
		query.update(Company.class, "companyid=?", new Object[] { companyId });
		return true;
	}

	public Map<Long, HkObj> getHkObjMapInId(List<Long> idList) {
		List<HkObj> list = this.getHkObjListInId(idList);
		Map<Long, HkObj> map = new HashMap<Long, HkObj>();
		for (HkObj o : list) {
			map.put(o.getObjId(), o);
		}
		return map;
	}

	public Map<Integer, Integer> getCompanyKindDataInfo() {
		String sql = "select kindid,count(kindid) acount from company group by kindid";
		Query query = manager.createQuery();
		List<Object[]> list = query.listdata("ds1", sql);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (Object[] o : list) {
			map.put(Integer.valueOf(o[0].toString()), Integer.valueOf(o[1]
					.toString()));
		}
		return map;
	}

	public Map<Integer, Integer> getCompanyKindDataInfo(int cityId) {
		Query query = manager.createQuery();
		String sql = null;
		List<Object[]> list = null;
		if (cityId > 0) {
			sql = "select kindid,count(kindid) acount from company where pcityid=? group by kindid";
			list = query.listdata("ds1", sql, cityId);
		}
		else {
			sql = "select kindid,count(kindid) acount from company group by kindid";
			list = query.listdata("ds1", sql);
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (Object[] o : list) {
			map.put(Integer.valueOf(o[0].toString()), Integer.valueOf(o[1]
					.toString()));
		}
		return map;
	}

	public List<Company> getCompanyListForCool(int kindId, int cityId,
			int begin, int size) {
		return this.getCompanyList(0, kindId, cityId, "membercount desc",
				begin, size);
	}

	public List<Company> getCompanyListForHot(int kindId, int cityId,
			int begin, int size) {
		return this.getCompanyList(0, kindId, cityId, "reviewcount desc",
				begin, size);
	}

	/**
	 * @param parentKindId
	 *            可为0
	 * @param kindId
	 *            可为0
	 * @param cityId
	 *            可为0
	 * @param orderSql
	 * @param begin
	 * @param size
	 * @return
	 */
	private List<Company> getCompanyList(int parentKindId, int kindId,
			int cityId, String orderSql, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from company where stopflg=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Company.STOPFLG_N);
		if (parentKindId > 0) {
			sql.append(" and parentkindid=?");
			olist.add(parentKindId);
		}
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		if (cityId > 0) {
			olist.add(cityId);
			sql.append(" and pcityid=?");
		}
		sql.append(" order by ").append(orderSql);
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				Company.class, olist);
	}

	public int countCompany(int parentKindId, int kindId, int cityId) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from company where stopflg=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(Company.STOPFLG_N);
		if (parentKindId > 0) {
			sql.append(" and parentkindid=?");
			olist.add(parentKindId);
		}
		if (kindId > 0) {
			sql.append(" and kindid=?");
			olist.add(kindId);
		}
		if (cityId > 0) {
			olist.add(cityId);
			sql.append(" and pcityid=?");
		}
		Query query = manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public List<CompanyUserScore> getCompanyUserScoreListForLikeIt(
			long companyId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyUserScore.class, "companyid=? and score>?",
				new Object[] { companyId, 1 }, "score desc", begin, size);
	}

	private void createUserCmpReview(long userId, long labaId, long companyId) {
		Query query = manager.createQuery();
		UserCmpReview o = query.getObjectEx(UserCmpReview.class,
				"userid=? and companyid=?", new Object[] { userId, companyId });
		if (o != null) {
			query.addField("labaid", labaId);
			query.update(UserCmpReview.class, "userid=? and companyid=?",
					new Object[] { userId, companyId });
		}
		else {
			query.addField("userid", userId);
			query.addField("labaid", labaId);
			query.addField("companyid", companyId);
			query.insert(UserCmpReview.class);
		}
	}

	private void deleteUserCmpReview(long userId, long companyId) {
		Query query = manager.createQuery();
		query.delete(UserCmpReview.class, "userid=? and companyid=?",
				new Object[] { userId, companyId });
	}

	public List<UserCmpReview> getUserCmpReviewList(long userId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(UserCmpReview.class, "userid=?",
				new Object[] { userId }, "labaid desc", begin, size);
	}

	public List<CompanyReview> getCompanyReviewListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CompanyReview>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from companyreview where labaid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CompanyReview.class);
	}

	public Map<Long, CompanyReview> getCompanyReviewMapInId(List<Long> idList) {
		List<CompanyReview> list = this.getCompanyReviewListInId(idList);
		Map<Long, CompanyReview> map = new HashMap<Long, CompanyReview>();
		for (CompanyReview o : list) {
			map.put(o.getLabaId(), o);
		}
		return map;
	}

	public List<CompanyReview> getCompanyReviewListByUserId(long userId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyReview.class, "userid=?",
				new Object[] { userId }, "labaid desc", begin, size);
	}

	public void updateMemberCount(long companyId, int memberCount) {
		Query query = manager.createQuery();
		query.addField("membercount", memberCount);
		query.updateById(Company.class, companyId);
	}

	public void updatePsearchType(long companyId, byte psearchType) {
		Query query = manager.createQuery();
		query.addField("psearchtype", psearchType);
		query.updateById(Company.class, companyId);
	}

	public void updateUid(long companyId, long uid) {
		Query query = manager.createQuery();
		query.addField("uid", uid);
		query.updateById(Company.class, companyId);
	}

	public List<CompanyUserStatus> getCompanyUserStatusListByUserIdAndUserStatus(
			long userId, byte status, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyUserStatus.class,
				"userid=? and userstatus=?", new Object[] { userId, status },
				"sysid desc", begin, size);
	}

	public boolean createCmdCmp(CmdCmp cmdCmp) {
		Query query = manager.createQuery();
		if (query.count(CmdCmp.class, "companyid=?", new Object[] { cmdCmp
				.getCompanyId() }) > 0) {
			return false;
		}
		cmdCmp.setOid(query.insertObject(cmdCmp).longValue());
		return true;
	}

	public void deleteCmdCmp(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmdCmp.class, oid);
	}

	public List<CmdCmp> getCmdCmpList(int pcityId, int begin, int size) {
		Query query = manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(CmdCmp.class, "pcityid=?",
					new Object[] { pcityId }, "oid desc", begin, size);
		}
		return query.listEx(CmdCmp.class, "oid desc", begin, size);
	}

	public int countCmdCmp(int pcityId) {
		Query query = manager.createQuery();
		if (pcityId > 0) {
			return query.count(CmdCmp.class, "pcityid=?",
					new Object[] { pcityId });
		}
		return query.count(CmdCmp.class);
	}

	public List<Company> getCompanyListForUserLike(int pcityId, int begin,
			int size) {
		Query query = manager.createQuery();
		String sql = "select * from company where pcityid=? and totalScore>0 and totalscore/totalvote>=2 order by companyid desc";
		return query.listBySql("ds1", sql, begin, size, Company.class, pcityId);
	}

	public CmdCmp getCmdCmp(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmdCmp.class, oid);
	}

	public void createSearchIndex() {
		Query query = this.manager.createQuery();
		List<Company> list = query.listEx(Company.class, "checkincount desc");
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(this.idxDir);
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(2500000));
			for (Company o : list) {
				Document doc = new Document();
				doc.add(new Field("companyid",
						String.valueOf(o.getCompanyId()), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("checkincount", String.valueOf(o
						.getCheckInCount()), Field.Store.NO,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("pcityid", String.valueOf(o.getPcityId()),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("companyname", o.getName(), Field.Store.NO,
						Field.Index.ANALYZED));
				iwriter.addDocument(doc);
			}
			iwriter.optimize();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Company> getCompanyListWithSearch(String key, int begin,
			int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<Company>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(this.idxDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30,
					"companyname", analyzer);
			org.apache.lucene.search.Query squery = parser.parse(_key);
			Sort sort = new Sort();
			sort.setSort(new SortField("checkincount", SortField.INT, true));
			int localSize = begin + size;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long companyId = Long.parseLong(hitDoc.get("companyid"));
				idList.add(companyId);
			}
			if (idList.size() > size) {
				idList = idList.subList(idList.size() - size, idList.size());
			}
			if (idList.size() > 0) {
				return this.getCompanyListInId(idList, null);
			}
			Query query = this.manager.createQuery();
			return query.listEx(Company.class, "name like ?",
					new Object[] { "%" + key + "%" }, "companyid desc", begin,
					size);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Company>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 只从lucene索引文件中查找
	 */
	public List<Long> getCompanyIdListWithSearch(int pcityId, String key,
			int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(this.idxDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			String[] qs = new String[] {
					"{" + String.valueOf((pcityId - 1)) + " TO "
							+ String.valueOf((pcityId + 1)) + "}", _key };
			BooleanClause.Occur[] flags = new BooleanClause.Occur[] {
					BooleanClause.Occur.MUST, BooleanClause.Occur.MUST };
			org.apache.lucene.search.Query squery = MultiFieldQueryParser
					.parse(Version.LUCENE_30, qs, new String[] { "pcityid",
							"companyname" }, flags, analyzer);
			Sort sort = new Sort();
			sort.setSort(new SortField("checkincount", SortField.INT, true));
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long companyId = Long.parseLong(hitDoc.get("companyid"));
				idList.add(companyId);
			}
			return idList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Long>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Long> getCompanyIdListWithSearchNotPcityId(int pcityId,
			String key, int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(this.idxDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			String[] qs = new String[] {
					"{" + String.valueOf((pcityId - 1)) + " TO "
							+ String.valueOf((pcityId + 1)) + "}", _key };
			BooleanClause.Occur[] flags = new BooleanClause.Occur[] {
					BooleanClause.Occur.MUST_NOT, BooleanClause.Occur.MUST };
			org.apache.lucene.search.Query squery = MultiFieldQueryParser
					.parse(Version.LUCENE_30, qs, new String[] { "pcityid",
							"companyname" }, flags, analyzer);
			Sort sort = new Sort();
			sort.setSort(new SortField("checkincount", SortField.INT, true));
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long companyId = Long.parseLong(hitDoc.get("companyid"));
				idList.add(companyId);
			}
			return idList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Long>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 只从lucene索引文件中查找
	 */
	public List<Long> getCompanyIdListWithSearch(String key, int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(this.idxDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30,
					"companyname", analyzer);
			org.apache.lucene.search.Query squery = parser.parse(_key);
			Sort sort = new Sort();
			sort.setSort(new SortField("checkincount", SortField.INT, true));
			int localSize = begin + size;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long companyId = Long.parseLong(hitDoc.get("companyid"));
				idList.add(companyId);
			}
			if (idList.size() > size) {
				idList = idList.subList(idList.size() - size, idList.size());
			}
			return idList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Long>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updateCmpPCityIdData() {
		Query query = this.manager.createQuery();
		List<Company> list = query.listEx(Company.class);
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		for (Company o : list) {
			int pcityId = o.getPcityId();
			if (pcityId > 0) {
				Pcity pcity = ZoneUtil.getPcityOld(pcityId);
				if (pcity != null) {
					String name = DataUtil.filterZoneName(pcity.getNameOld());
					City city = zoneService.getCityLike(name);
					if (city != null) {
						o.setPcityId(city.getCityId());
						this.updateCompany(o);
						cmpProductService.updateCmpProductPcityIdByCompanyId(o
								.getCompanyId(), city.getCityId());
					}
				}
			}
		}
		List<CmpZoneInfo> list2 = this.getCmpZoneInfoList();
		for (CmpZoneInfo o : list2) {
			Pcity pcity = ZoneUtil.getPcityOld(o.getPcityId());
			if (pcity != null) {
				String name = DataUtil.filterZoneName(pcity.getNameOld());
				City city = zoneService.getCityLike(name);
				if (city != null) {
					o.setPcityId(city.getCityId());
					query.updateObject(o);
				}
			}
		}
	}

	public List<Company> getCompanyListByUnionKindId(long kindId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Company.class, "unionkindid=?",
				new Object[] { kindId }, "companyid desc", begin, size);
	}

	public List<Company> getCompanyListByPcityIdAndNameLike(int pcityId,
			String name, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Company.class, "pcityid=? and name like ?",
				new Object[] { pcityId, "%" + name + "%" },
				"checkincount desc", begin, size);
	}

	public void createCmpTagSearchIndex() {
		Query query = this.manager.createQuery();
		List<CmpTagRef> list = query.listEx(CmpTagRef.class, "oid desc");
		List<Long> idList = new ArrayList<Long>();
		for (CmpTagRef o : list) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> map = this.getCompanyMapInId(idList);
		for (CmpTagRef o : list) {
			o.setCompany(map.get(o.getCompanyId()));
		}
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(this.cmpTagRefDir);
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(2500000));
			for (CmpTagRef o : list) {
				if (o.getCompany() != null) {
					Document doc = new Document();
					doc.add(new Field("companyid", String.valueOf(o
							.getCompanyId()), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					doc.add(new Field("checkincount", String.valueOf(o
							.getCompany().getCheckInCount()), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					doc.add(new Field("pcityid",
							String.valueOf(o.getPcityId()), Field.Store.NO,
							Field.Index.ANALYZED));
					doc.add(new Field("tagname", o.getName(), Field.Store.NO,
							Field.Index.ANALYZED));
					iwriter.addDocument(doc);
				}
			}
			iwriter.optimize();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Long> getCompanyIdListFromCmpTagRefWithSearchNotPcityId(
			int pcityId, String key, int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			File dir = new File(this.cmpTagRefDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			String[] qs = new String[] {
					"{" + String.valueOf((pcityId - 1)) + " TO "
							+ String.valueOf((pcityId + 1)) + "}", _key };
			BooleanClause.Occur[] flags = new BooleanClause.Occur[] {
					BooleanClause.Occur.MUST_NOT, BooleanClause.Occur.MUST };
			org.apache.lucene.search.Query squery = MultiFieldQueryParser
					.parse(Version.LUCENE_30, qs, new String[] { "pcityid",
							"tagname" }, flags, analyzer);
			Sort sort = new Sort();
			sort.setSort(new SortField("checkincount", SortField.INT, true));
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			TopFieldDocs tfd = isearcher.search(squery, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long companyId = Long.parseLong(hitDoc.get("companyid"));
				idList.add(companyId);
			}
			return idList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Long>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deleteCompanyUserStatus(long companyId, long userId) {
		Query query = this.manager.createQuery();
		query.delete(CompanyUserStatus.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public void updateCompanyUserStatus(CompanyUserStatus companyUserStatus) {
		Query query = this.manager.createQuery();
		query.updateObject(companyUserStatus);
	}

	public List<Company> getCompanyListByNoPcityId(int pcityId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Company.class, "pcityid!=?",
				new Object[] { pcityId }, "checkincount desc", begin, size);
	}

	public void addCheckInCount(long companyId, int add) {
		Query query = this.manager.createQuery();
		query.addField("checkincount", "add", add);
		query.updateById(Company.class, companyId);
	}

	public List<Company> getCompanyListAll() {
		Query query = this.manager.createQuery();
		return query.listEx(Company.class);
	}

	public void deleteCompany(long companyId) {
		Query query = this.manager.createQuery();
		query.delete(CompanyReview.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyReviewDel.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyUserScore.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyUserStatus.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyAward.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyBizCircle.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyFeed.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyMoney.class, "companyid=?",
				new Object[] { companyId });
		query.delete(Employee.class, "companyid=?", new Object[] { companyId });
		query.delete(BizCircleRef.class, "companyid=?",
				new Object[] { companyId });
		query.delete(BuildingTagRef.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyTagRef.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyBizCircle.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyPhoto.class, "companyid=?",
				new Object[] { companyId });
		query.delete(AuthCompany.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProduct.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductSort.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpComment.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpWatch.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpBulletin.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpLink.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpLink.class, "linkcompanyid=?",
				new Object[] { companyId });
		query.delete(CmpRecruit.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTemplate.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpInfo.class, "companyid=?", new Object[] { companyId });
		query
				.delete(CmpFollow.class, "companyid=?",
						new Object[] { companyId });
		query
				.delete(CmpModule.class, "companyid=?",
						new Object[] { companyId });
		query.delete(Coupon.class, "companyid=?", new Object[] { companyId });
		query.delete(UserCoupon.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpHkbLog.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmpAdminHkbLog.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpAdminHkbLog.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpChildKindRef.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserCmpReview.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductPhoto.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductReview.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductUserScore.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductUserStatus.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserCmpProductReview.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductTagRef.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpProductFav.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(OrderForm.class, "companyid=?",
						new Object[] { companyId });
		query
				.delete(CmpMember.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmpMemberGrade.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpMemberMoneyLog.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTable.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpTableSort.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTablePhoto.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpPersonTable.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpOrderTable.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTableDailyData.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTablePhotoSet.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserProduct.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpAct.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpActUser.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpActCost.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpActStepCost.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpActCmt.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmdCmp.class, "companyid=?", new Object[] { companyId });
		query.delete(CmdProduct.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpCheckInUser.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpCheckInUserLog.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpTip.class, "companyid=?", new Object[] { companyId });
		query.delete(UserCmpTip.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpTipDel.class, "companyid=?",
						new Object[] { companyId });
		query
				.delete(CmpFollow.class, "companyid=?",
						new Object[] { companyId });
		query.delete(HkObj.class, "objid=?", new Object[] { companyId });
		// 是否拿到外面删除
		query.delete(CompanyRefLaba.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CompanyRefLabaDel.class, "companyid=?",
				new Object[] { companyId });
		query.delete(Mayor.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpPhotoVote.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserCmpPoint.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserCmpEnjoy.class, "companyid=?",
				new Object[] { companyId });
		query.delete(UserEquipment.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpFrLink.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmpBbsKind.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpMyBbs.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpBbs.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpBbsReply.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpBbsDel.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmpBbsReplyDel.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpBbsContent.class, "companyid=?",
				new Object[] { companyId });
		query
				.delete(CmpBomber.class, "companyid=?",
						new Object[] { companyId });
		query.delete(CmpAdminUser.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpContact.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpJoinInApply.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpSellNet.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpMsg.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpArticle.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpArticleContent.class, "companyid=?",
				new Object[] { companyId });
		query.delete(CmpFile.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpNav.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpAd.class, "companyid=?", new Object[] { companyId });
		query.delete(CmpRefUser.class, "companyid=?",
				new Object[] { companyId });
		query.deleteById(Company.class, companyId);
	}

	public List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndDoneStatus(
			long companyId, byte doneStatus, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyUserStatus.class,
				"companyid=? and donestatus=?", new Object[] { companyId,
						doneStatus }, "sysid desc", begin, size);
	}

	public List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndUserStatus(
			long companyId, byte userStatus, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyUserStatus.class,
				"companyid=? and userstatus=?", new Object[] { companyId,
						userStatus }, "sysid desc", begin, size);
	}

	public void updateCompanyMap(long companyId, double markerX, double markerY) {
		Query query = manager.createQuery();
		query.addField("markerx", markerX);
		query.addField("markery", markerY);
		query.updateById(Company.class, companyId);
	}

	public void updateCompanyUserId(long companyId, long userId) {
		Query query = manager.createQuery();
		query.addField("userid", userId);
		query.updateById(Company.class, companyId);
	}

	public List<AuthCompany> getAuthCompanyListByMainStatus(byte mainStatus,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(AuthCompany.class, "mainstatus=?",
				new Object[] { mainStatus }, "sysid desc", begin, size);
	}

	public void createCmpOtherInfo(CmpOtherInfo cmpOtherInfo) {
		Query query = manager.createQuery();
		query.insertObject(cmpOtherInfo);
	}

	public CmpOtherInfo getCmpOtherInfo(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpOtherInfo.class, companyId);
	}

	public void updateCmpOtherInfo(CmpOtherInfo cmpOtherInfo) {
		Query query = manager.createQuery();
		query.updateObject(cmpOtherInfo);
	}

	public void updateWorkCountByCompanyId(long companyId, int workCount) {
		Query query = manager.createQuery();
		query.addField("workcount", workCount);
		query.updateById(Company.class, companyId);
	}

	public List<Company> getCompanyListByKindIdForNew(int kindId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(Company.class, "kindid=?", new Object[] { kindId },
				"companyid desc", begin, size);
	}

	public List<Company> getCompanyListByKindIdForWorkCount(int kindId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(Company.class, "kindid=?", new Object[] { kindId },
				"workcount desc", begin, size);
	}
}