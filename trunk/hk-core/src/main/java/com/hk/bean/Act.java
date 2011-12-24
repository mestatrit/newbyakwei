package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 活动
 * 
 * @author akwei
 */
@Table(name = "act", id = "actid")
public class Act {

	public static final byte NEEDCHECK_N = 0;

	public static final byte NEEDCHECK_Y = 1;

	private long actId;

	private long userId;// 创建者

	private String name;// 活动名称

	private Date beginTime;// 活动报名时间

	private Date endTime;// 结束时间

	private String addr;// 地址信息

	private String intro;// 活动说明

	private byte needCheck;// 是否需要审核

	private Date createTime;// 创建时间

	private long portId;// 短信通道的id

	private long actSysNumId;// 暗号id

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isActNeedCheck() {
		if (this.needCheck == Act.NEEDCHECK_Y) {
			return true;
		}
		return false;
	}

	public byte getNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(byte needCheck) {
		this.needCheck = needCheck;
	}

	public int validate(boolean forEdit) {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.ACT_NAME_ERROR;
		}
		if (s.length() > 15) {
			return Err.ACT_NAME_LENGTH_TOO_LONG;
		}
		if (this.userId <= 0) {
			return Err.USERID_ERROR;
		}
		if (this.beginTime == null) {
			return Err.ACT_BEGINTIME_ERROR;
		}
		if (this.endTime == null) {
			return Err.ACT_ENDTIME_ERROR;
		}
		if (!forEdit) {
			if (this.beginTime.getTime() < System.currentTimeMillis()) {
				return Err.ACT_BEGINTIME_ERROR;
			}
			if (endTime.getTime() <= beginTime.getTime()) {
				return Err.ACT_ENDTIME_EARLYER_THAN_BEGINTIME_ERROR;
			}
			if (endTime.getTime() <= System.currentTimeMillis()) {
				return Err.ACT_ENDTIME_ERROR;
			}
		}
		s = DataUtil.toTextRow(this.addr);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.ACT_ADDR_LENGTH_TOO_LONG;
			}
		}
		s = DataUtil.toText(this.intro);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.ACT_INTRO_LENGTH_TOO_LONG;
			}
		}
		if (!DataUtil.isInElements(this.needCheck, new Object[] {
				Act.NEEDCHECK_N, Act.NEEDCHECK_Y })) {
			return Err.ACT_NEEDCHECK_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getActSysNumId() {
		return actSysNumId;
	}

	public void setActSysNumId(long actSysNumId) {
		this.actSysNumId = actSysNumId;
	}
}