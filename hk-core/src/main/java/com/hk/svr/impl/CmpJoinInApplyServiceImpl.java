package com.hk.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpJoinInApply;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpJoinInApplyService;

public class CmpJoinInApplyServiceImpl implements CmpJoinInApplyService {

	@Autowired
	private QueryManager manager;

	public void createCmpJoinInApply(CmpJoinInApply cmpJoinInApply) {
		if (cmpJoinInApply.getCreateTime() == null) {
			cmpJoinInApply.setCreateTime(new Date());
		}
		Query query = this.manager.createQuery();
		long oid = query.insertObject(cmpJoinInApply).longValue();
		cmpJoinInApply.setOid(oid);
	}

	public void deleteCmpJoinInApply(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpJoinInApply.class, oid);
	}

	public CmpJoinInApply getCmpJoinInApply(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpJoinInApply.class, oid);
	}

	public List<CmpJoinInApply> getCmpJoinInApplyListByCompanyId(
			long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpJoinInApply.class, "companyid=?",
				new Object[] { companyId }, "oid desc", begin, size);
	}

	public List<CmpJoinInApply> getCmpJoinInApplyListByCompanyIdAndReaded(
			long companyId, byte readed, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpJoinInApply.class, "companyid=? and readed=?",
				new Object[] { companyId, readed }, "oid desc", begin, size);
	}

	public void setCmpJoinInApplyReaded(long oid, byte readed) {
		Query query = this.manager.createQuery();
		query.addField("readed", readed);
		query.updateById(CmpJoinInApply.class, oid);
	}
}