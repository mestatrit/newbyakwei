package com.hk.svr.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpGroup;
import com.hk.bean.CmpGroupQuestion;
import com.hk.bean.CmpGroupSmsPort;
import com.hk.bean.CmpGroupUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpGroupService;

public class CmpGroupServiceImpl implements CmpGroupService {
	@Autowired
	QueryManager manager;

	public boolean createCmpGroup(CmpGroup cmpGroup) {
		CmpGroup o = this.getCmpGroupByName(cmpGroup.getName());
		if (o != null) {
			return false;
		}
		cmpGroup.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("companyid", cmpGroup.getCompanyId());
		query.addField("name", cmpGroup.getName());
		query.addField("intro", cmpGroup.getIntro());
		query.addField("logopath", cmpGroup.getName());
		query.addField("validateflg", cmpGroup.getValidateflg());
		query.addField("createtime", cmpGroup.getCreateTime());
		long id = query.insert(CmpGroup.class).longValue();
		cmpGroup.setCmpgroupId(id);
		this.createCmpGroupSmsPort(id);// 生成俱乐部通道
		return true;
	}

	public CmpGroup getCmpGroupByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpGroup.class, "name=?",
				new Object[] { name });
	}

	public CmpGroup getCmpGroupByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpGroup.class, "companyid=?",
				new Object[] { companyId });
	}

	public void updateLogopath(long cmpgroupId, File file) throws Exception {
		// TODO Auto-generated method stub
	}

	public void createCmpGroupQuestion(CmpGroupQuestion cmpGroupQuestion) {
		Query query = manager.createQuery();
		query.addField("cmpgroupid", cmpGroupQuestion.getCmpgroupId());
		query.addField("quest", cmpGroupQuestion.getQuest());
		query.addField("answer", cmpGroupQuestion.getAnswer());
		long id = query.insert(CmpGroupQuestion.class).longValue();
		cmpGroupQuestion.setOid(id);
	}

	public boolean createCmpGroupUser(CmpGroupUser cmpGroupUser) {
		CmpGroupUser o = this.getCmpGroupUser(cmpGroupUser.getCmpgroupId(),
				cmpGroupUser.getUserId());
		if (o != null) {
			return false;
		}
		Query query = manager.createQuery();
		query.addField("userid", cmpGroupUser.getUserId());
		query.addField("cmpgroupid", cmpGroupUser.getCmpgroupId());
		query.addField("name", cmpGroupUser.getName());
		long oid = query.insert(CmpGroupUser.class).longValue();
		cmpGroupUser.setOid(oid);
		return true;
	}

	public void deleteCmpGroupQuestion(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpGroupQuestion.class, oid);
	}

	public void deleteCmpGroupUser(long cmpgroupId, long userId) {
		Query query = manager.createQuery();
		query.delete(CmpGroupUser.class, "cmpgroupid=? and userid=?",
				new Object[] { cmpgroupId, userId });
	}

	public CmpGroup getCmpGroup(long cmpgroupId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpGroup.class, cmpgroupId);
	}

	public CmpGroupQuestion getCmpGroupQuestion(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpGroupQuestion.class, oid);
	}

	public List<CmpGroupQuestion> getCmpGroupQuestionList(long cmpgroupId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpGroupQuestion.class, "cmpgroupid=?",
				new Object[] { cmpgroupId }, "oid desc", begin, size);
	}

	public CmpGroupQuestion getCmpGroupQuestionRandom(long cmpgroupId) {
		int count = this.countCmpGroupQuestionByCmpgroupId(cmpgroupId);
		int begin = DataUtil.getRandomNumber(count);
		List<CmpGroupQuestion> list = this.getCmpGroupQuestionList(cmpgroupId,
				begin, 1);
		if (list.size() > 0) {
			return list.iterator().next();
		}
		return null;
	}

	private int countCmpGroupQuestionByCmpgroupId(long cmpgroupId) {
		Query query = manager.createQuery();
		return query.count(CmpGroupQuestion.class, "cmpgroupid=?",
				new Object[] { cmpgroupId });
	}

	public CmpGroupSmsPort getCmpGroupSmsPort(long cmpgroupId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpGroupSmsPort.class, "cmpgroupid=?",
				new Object[] { cmpgroupId });
	}

	/**
	 * 获得最后一个通道的数据，查看通道号码，然后生成一个当前通道号码+1的数据，然后返回
	 * 
	 * @param cmpgroupId
	 * @return
	 */
	private synchronized CmpGroupSmsPort createCmpGroupSmsPort(long cmpgroupId) {
		Query query = manager.createQuery();
		List<CmpGroupSmsPort> list = query.listEx(CmpGroupSmsPort.class,
				"oid desc", 0, 1);
		CmpGroupSmsPort o = new CmpGroupSmsPort();
		o.setCmpgroupId(cmpgroupId);
		if (list.size() == 0) {
			o.setPort("101");
		}
		else {
			CmpGroupSmsPort oo = list.iterator().next();
			int port = Integer.parseInt(oo.getPort());
			int new_port = port + 1;
			o.setPort(new_port + "");
		}
		query.addField("cmpgroupid", o.getCmpgroupId());
		query.addField("port", o.getPort());
		long oid = query.insert(CmpGroupSmsPort.class).longValue();
		o.setOid(oid);
		return o;
	}

	public CmpGroupSmsPort getCmpGroupSmsPortByPort(String port) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpGroupSmsPort.class, "port=?",
				new Object[] { port });
	}

	public CmpGroupUser getCmpGroupUser(long cmpgroupId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpGroupUser.class,
				"cmpgroupid=? and userid=?",
				new Object[] { cmpgroupId, userId });
	}

	public boolean updateCmpGroup(CmpGroup cmpGroup) {
		CmpGroup o = this.getCmpGroupByName(cmpGroup.getName());
		if (o.getCmpgroupId() != cmpGroup.getCmpgroupId()) {// 如果名字相同但不是同一个对象，就有重名存在
			return false;
		}
		Query query = manager.createQuery();
		query.addField("companyid", cmpGroup.getCompanyId());
		query.addField("name", cmpGroup.getName());
		query.addField("validateflg", cmpGroup.getValidateflg());
		query.addField("intro", cmpGroup.getIntro());
		query.addField("logopath", cmpGroup.getLogopath());
		query.updateById(CmpGroup.class, cmpGroup.getCmpgroupId());
		return true;
	}
}