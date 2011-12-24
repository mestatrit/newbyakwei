package com.hk.svr.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.ActSysNum;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.ActSysNumService;
import com.hk.svr.user.exception.NoAvailableActSysNumException;

public class ActSysNumServiceImpl implements ActSysNumService {

	@Autowired
	private QueryManager manager;

	public void tmpcreateSysnum(ActSysNum actSysNum) {
		Query query = this.manager.createQuery();
		actSysNum.setSysId(query.insertObject(actSysNum).intValue());
	}

	DecimalFormat df = new DecimalFormat("000000");

	private ActSysNum createNewSysnum() {
		ActSysNum o = new ActSysNum();
		Query query = this.manager.createQuery();
		List<ActSysNum> list = query
				.listEx(ActSysNum.class, "sysid desc", 0, 1);
		if (list.size() > 0) {
			ActSysNum last = list.get(0);
			o.setSysnum(df.format(Integer.parseInt(last.getSysnum()) + 1));
		}
		else {
			o.setSysnum(df.format(1));
		}
		o.setOverTime(new Date());
		o.setSysstatus(ActSysNum.SYSSTATUS_FREE);
		tmpcreateSysnum(o);
		return o;
	}

	public ActSysNum getActSysNumBySysNum(String sysNum) {
		Query query = this.manager.createQuery();
		query.setTable(ActSysNum.class);
		query.where("sysnum=?").setParam(sysNum);
		return query.getObject(ActSysNum.class);
	}

	public synchronized ActSysNum createAvailableActSysNum()
			throws NoAvailableActSysNumException {
		Query query = this.manager.createQuery();
		// 获得随机系统暗号
		int count = query.count(ActSysNum.class, "sysstatus=?",
				new Object[] { ActSysNum.SYSSTATUS_FREE });// 计算可用暗号数量
		if (count == 0) {
			ActSysNum actSysNum = this.createNewSysnum();
			return actSysNum;
		}
		int begin = DataUtil.getRandomNumber(count);
		int size = 1;
		List<ActSysNum> list = query.listEx(ActSysNum.class, "sysstatus=?",
				new Object[] { ActSysNum.SYSSTATUS_FREE }, "sysid asc", begin,
				size);// 获取暗号
		if (list.size() == 0) {
			// 暗号用完，活动创建失败
			throw new NoAvailableActSysNumException("no sysnum");
		}
		return list.get(0);
	}

	public void updateActSysNumInfo(int sysId, long actId, byte sysStatus,
			byte usetype, Date overTime) {
		Query query = this.manager.createQuery();
		query.setTable(ActSysNum.class);
		query.addField("sysstatus", sysStatus);
		query.addField("overtime", overTime);
		query.addField("actid", actId);
		query.addField("usetype", usetype);
		query.where("sysid=?").setParam(sysId);
		query.update();
	}

	public ActSysNum getActSysNum(long sysId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(ActSysNum.class, sysId);
	}
}