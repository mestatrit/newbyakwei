package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.HkLog;

@Table(name = "scorelog", id = "logid")
public class ScoreLog implements HkLog {
	private long logId;

	private long userId;

	private int scoretype;// 积分获得方式(短信吹喇叭...)

	private int addcount;// 积分获得数量

	private long objId;

	private Date createTime;

	public static ScoreLog create(long userId, int scoretype, long objId,
			int addcount) {
		ScoreLog o = new ScoreLog();
		o.setUserId(userId);
		o.setScoretype(scoretype);
		o.setAddcount(addcount);
		o.setObjId(objId);
		return o;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public long getObjId() {
		return objId;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getScoretype() {
		return scoretype;
	}

	public void setScoretype(int scoretype) {
		this.scoretype = scoretype;
	}

	public int getAddcount() {
		return addcount;
	}

	public void setAddcount(int addcount) {
		this.addcount = addcount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}