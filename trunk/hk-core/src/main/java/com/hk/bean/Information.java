package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;

@Table(name = "information", id = "infoid")
public class Information {
	public static final byte USESTATUS_RUN = 0;

	public static final byte USESTATUS_STOP = 1;

	private long infoId;

	private long portId;

	private long userId;

	private String tag;

	private String name;

	private Date createTime;

	private byte useStatus;

	private Date beginTime;

	private Date endTime;

	private String intro;

	private User user;

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public static byte getUSESTATUS_RUN() {
		return USESTATUS_RUN;
	}

	public static byte getUSESTATUS_STOP() {
		return USESTATUS_STOP;
	}

	public boolean isRun() {
		if (useStatus == USESTATUS_RUN) {
			return true;
		}
		return false;
	}

	public byte getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(byte useStatus) {
		this.useStatus = useStatus;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getInfoId() {
		return infoId;
	}

	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int validate() {
		String name = DataUtil.toTextRow(getName());
		if (DataUtil.isEmpty(name)) {
			return Err.INFORMATION_NAME_ERROR;
		}
		if (name.length() > 16) {
			return Err.INFORMATION_NAME_LEN_TOOLONG;
		}
		if (getUserId() <= 0) {
			return Err.USERID_ERROR;
		}
		if (DataUtil.isEmpty(getTag())) {
			return Err.INFORMATION_TAG_ERROR;
		}
		if (DataUtil.toTextRow(getTag()).length() > 15) {
			return Err.INFORMATION_TAG_LEN_TOOLONG;
		}
		String intro = DataUtil.toText(getIntro());
		if (!DataUtil.isEmpty(intro)) {
			if (intro.length() > 300) {
				return Err.INFROMATION_INTRO_LEN_TOOLONG;
			}
		}
		return Err.SUCCESS;
	}
}