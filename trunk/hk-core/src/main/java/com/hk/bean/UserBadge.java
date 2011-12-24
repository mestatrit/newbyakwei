package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ImageConfig;

@Table(name = "userbadge")
public class UserBadge {

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private long badgeId;

	@Column
	private String name;

	@Column
	private String intro;

	@Column
	private byte limitflg;

	@Column
	private Date createTime;

	/**
	 * 在那里获得的
	 */
	@Column
	private long companyId;

	/**
	 * 徽章路径
	 */
	@Column
	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public UserBadge() {
	}

	public UserBadge(Badge badge) {
		this.setBadgeId(badge.getBadgeId());
		this.setIntro(badge.getIntro());
		this.setName(badge.getName());
		this.setLimitflg(badge.getLimitflg());
		this.setPath(badge.getPath());
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBadgeId() {
		return badgeId;
	}

	public void setBadgeId(long badgeId) {
		this.badgeId = badgeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public byte getLimitflg() {
		return limitflg;
	}

	public void setLimitflg(byte limitflg) {
		this.limitflg = limitflg;
	}

	public String getPic300() {
		return ImageConfig.getBadge300PicUrl(this.path);
	}

	public String getPic57() {
		return ImageConfig.getBadge57PicUrl(this.path);
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public boolean isInvite() {
		if (this.limitflg == Badge.LIMITFLG_INVITE) {
			return true;
		}
		return false;
	}
}