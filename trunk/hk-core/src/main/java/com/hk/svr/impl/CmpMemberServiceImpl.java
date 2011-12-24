package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpMember;
import com.hk.bean.CmpMemberGrade;
import com.hk.bean.CmpMemberMoneyLog;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpMemberService;

public class CmpMemberServiceImpl implements CmpMemberService {

	@Autowired
	private QueryManager manager;

	public List<CmpMember> getCmpMemberList(long companyId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpMember.class, "companyid=?",
				new Object[] { companyId }, "memberid desc", begin, size);
	}

	public int createCmpMember(CmpMember cmpMember) {
		CmpMember o = null;
		if (cmpMember.getEmail() != null) {
			o = this.getCmpMemberByEmailAndCompanyId(cmpMember.getEmail(),
					cmpMember.getCompanyId());
			if (o != null) {
				return 2;
			}
		}
		else {
			o = this.getCmpMemberByMobileAndCompanyId(cmpMember.getMobile(),
					cmpMember.getCompanyId());
			if (o != null) {
				return 1;
			}
		}
		cmpMember.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("userid", cmpMember.getUserId());
		query.addField("companyid", cmpMember.getCompanyId());
		query.addField("money", cmpMember.getMoney());
		query.addField("mobile", cmpMember.getMobile());
		query.addField("email", cmpMember.getEmail());
		query.addField("createtime", cmpMember.getCreateTime());
		query.addField("gradeid", cmpMember.getGradeId());
		query.addField("name", cmpMember.getName());
		query.insert(CmpMember.class);
		return 0;
		// Company company = this.getCompany(cmpMember.getCompanyId());
		// company.setMemberCount(company.getMemberCount() + 1);
		// this.updateCompany(company);
	}

	public CmpMember getCmpMemberByMobileAndCompanyId(String mobile,
			long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpMember.class, "mobile=? and companyid=?",
				new Object[] { mobile, companyId });
	}

	public CmpMember getCmpMemberByEmailAndCompanyId(String email,
			long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpMember.class, "email=? and companyid=?",
				new Object[] { email, companyId });
	}

	public void deleteCmpMember(long memberId) {
		CmpMember cmpMember = this.getCmpMember(memberId);
		if (cmpMember == null) {
			return;
		}
		Query query = manager.createQuery();
		query.deleteById(CmpMember.class, memberId);
	}

	public CmpMember getCmpMember(long companyId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpMember.class, "companyid=? and userid=?",
				new Object[] { companyId, userId });
	}

	public boolean createCmpMemberGrade(CmpMemberGrade cmpMemberGrade) {
		Query query = manager.createQuery();
		CmpMemberGrade o = query.getObjectEx(CmpMemberGrade.class, "name=?",
				new Object[] { cmpMemberGrade.getName() });
		if (o != null) {
			return false;
		}
		query.addField("companyid", cmpMemberGrade.getCompanyId());
		query.addField("name", cmpMemberGrade.getName());
		query.addField("rebate", cmpMemberGrade.getRebate());
		long id = query.insert(CmpMemberGrade.class).longValue();
		cmpMemberGrade.setGradeId(id);
		return true;
	}

	public void deleteCmpMemberGrade(long gradeId) {
		Query query = manager.createQuery();
		query.deleteById(CmpMemberGrade.class, gradeId);
	}

	public CmpMemberGrade getCmpMemberGrade(long gradeId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpMemberGrade.class, gradeId);
	}

	public List<CmpMemberGrade> getCmpMemberGradeListByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpMemberGrade.class, "companyid=?",
				new Object[] { companyId }, "gradeid desc");
	}

	public List<CmpMember> getCmpMemberList(long companyId, String name,
			String mobile, String email, long gradeId, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmpmember where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (mobile != null) {
			sql.append(" and mobile=?");
			olist.add(mobile);
		}
		if (email != null) {
			sql.append(" and email=?");
			olist.add(email);
		}
		if (gradeId > 0) {
			sql.append(" and gradeid=?");
			olist.add(gradeId);
		}
		sql.append(" order by gradeid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpMember.class, olist);
	}

	public int countCmpMember(long companyId, String name, String mobile,
			String email, long gradeId) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmpmember where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (mobile != null) {
			sql.append(" and mobile=?");
			olist.add(mobile);
		}
		if (email != null) {
			sql.append(" and email=?");
			olist.add(email);
		}
		if (gradeId > 0) {
			sql.append(" and gradeid=?");
			olist.add(gradeId);
		}
		Query query = manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public int updateCmpMember(CmpMember cmpMember) {
		CmpMember o = null;
		if (cmpMember.getEmail() != null) {
			o = this.getCmpMemberByEmailAndCompanyId(cmpMember.getEmail(),
					cmpMember.getCompanyId());
			if (o != null && o.getMemberId() != cmpMember.getMemberId()) {
				return 2;
			}
		}
		else {
			o = this.getCmpMemberByMobileAndCompanyId(cmpMember.getMobile(),
					cmpMember.getCompanyId());
			if (o != null && o.getMemberId() != cmpMember.getMemberId()) {
				return 1;
			}
		}
		Query query = manager.createQuery();
		query.addField("userid", cmpMember.getUserId());
		query.addField("companyid", cmpMember.getCompanyId());
		query.addField("money", cmpMember.getMoney());
		query.addField("mobile", cmpMember.getMobile());
		query.addField("email", cmpMember.getEmail());
		query.addField("createtime", cmpMember.getCreateTime());
		query.addField("gradeid", cmpMember.getGradeId());
		query.addField("name", cmpMember.getName());
		query.updateById(CmpMember.class, cmpMember.getMemberId());
		return 0;
	}

	public boolean updateCmpMemberGrade(CmpMemberGrade cmpMemberGrade) {
		Query query = manager.createQuery();
		CmpMemberGrade o = query.getObjectEx(CmpMemberGrade.class, "name=?",
				new Object[] { cmpMemberGrade.getName() });
		if (o != null && o.getGradeId() != cmpMemberGrade.getGradeId()) {
			return false;
		}
		query.addField("companyid", cmpMemberGrade.getCompanyId());
		query.addField("name", cmpMemberGrade.getName());
		query.addField("rebate", cmpMemberGrade.getRebate());
		query.updateById(CmpMemberGrade.class, cmpMemberGrade.getGradeId());
		return true;
	}

	public CmpMember getCmpMember(long memberId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpMember.class, memberId);
	}

	private void createMoneyLog(CmpMemberMoneyLog log) {
		log.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("companyid", log.getCompanyId());
		query.addField("memberid", log.getMemberId());
		query.addField("addflg", log.getAddflg());
		query.addField("money", log.getMoney());
		query.addField("oldmoney", log.getOldMoney());
		query.addField("createtime", log.getCreateTime());
		long oid = query.insert(CmpMemberMoneyLog.class).longValue();
		log.setOid(oid);
	}

	public boolean addMoney(long memberId, byte addflg, double money) {
		Query query = manager.createQuery();
		CmpMember o = this.getCmpMember(memberId);
		if (o == null) {
			return false;
		}
		double newMoney = o.getMoney() + money;
		if (newMoney < 0) {
			return false;
		}
		query.addField("money", newMoney);
		query.updateById(CmpMember.class, memberId);
		CmpMemberMoneyLog log = new CmpMemberMoneyLog();
		log.setCompanyId(o.getCompanyId());
		log.setMemberId(log.getMemberId());
		log.setCreateTime(new Date());
		log.setMoney(newMoney);
		log.setOldMoney(o.getMoney());
		log.setAddflg(addflg);
		this.createMoneyLog(log);
		return true;
	}
}