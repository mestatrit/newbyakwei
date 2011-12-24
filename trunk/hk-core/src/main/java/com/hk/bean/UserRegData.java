package com.hk.bean;

public class UserRegData {

	private String email;

	private String mobile;

	private String password;

	private String nickName;

	private String zoneName;

	private byte sex;

	private long prouserId;

	private long inviteUserId;

	private String inviteCode;

	private String ip;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public long getProuserId() {
		return prouserId;
	}

	public void setProuserId(long prouserId) {
		this.prouserId = prouserId;
	}

	public long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
}